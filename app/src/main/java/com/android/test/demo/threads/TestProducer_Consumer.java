package com.android.test.demo.threads;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 生产者&消费者模型
 * 其中Person是共享资源（当前时刻只能存在一个）
 * 存在一个资源时，生产者线程阻塞，唤醒消费者线程消费
 * 没有资源时，消费者线程阻塞，唤醒生产者线程生产，如此交替执行
 */
public class TestProducer_Consumer {

    private static final String TAG = "Threads";

    private static final int SIZE = 10;

    public static void test() {

        /**
         * 共享资源
         * 通过wait()和notify()实现的生产者/消费者
         */
        //Person p = new Person1();

        /**
         * 使用ReentrantLock(重入锁)实现的生产者/消费者模式
         */
        Person p = new Person2();

        /**
         * 使用BlockingQueue实现的生产者/消费者模式
         */
        //Person p = new Person3();

        /**
         * 生产者线程
         */
        Thread producer = new Thread(new Producer(p));
        producer.start();

        /**
         * 测试mLock.notifyAll()会唤醒同类的其他线程
         *
         * 线程3825和3824都是生产者线程
         * 01-11 00:31:23.349 I/Threads ( 4104): [3825]push() of while wait() !!!
         * 01-11 00:31:23.350 I/Threads ( 4104): [3824]push() of while wait() !!!
         */
        Thread producer2 = new Thread(new Producer(p));
        producer2.start();


        /**
         * 消费者线程
         */
        Thread consumer = new Thread(new Consumer(p));
        consumer.start();
    }


    /**
     * 生产者，生产50次数据
     */
    static class Producer implements Runnable {

        /**
         * 共享对象
         */
        private Person p = null;

        public Producer(Person p) {
            this.p = p;
        }

        @Override
        public void run() {
            /**
             * 生产对象
             */
            for (int i = 0; i < SIZE; i++) {
                /**
                 * 为了测试明显，设置奇偶差异
                 */
                if (i % 2 == 0) {
                    p.push("Tom", 20);
                } else {
                    p.push("Jarry", 30);
                }
            }

            logI(TAG, "Producer is end!!!");
        }
    }

    /**
     * 消费者
     */
    static class Consumer implements Runnable {

        private Person p;

        public Consumer(Person p) {
            this.p = p;
        }

        @Override
        public void run() {
            for (int i = 0; i < SIZE; i++) {
                p.pop();
            }

            logI(TAG, "Consumer is end!!!");
        }
    }


    /**
     * 资源操作类
     */
    static abstract class Person {

        /**
         *
         */
        protected String name;
        protected int age;

        /**
         * 表示共享资源对象是否为空，如果为true，表示需要生产，如果为false，则有数据了，不要生产
         */
        protected boolean isEmpty = true;

        /**
         * 生产数据
         * @param name
         * @param age
         */
        public abstract void push(String name, int age);

        /**
         * 消费数据
         */
        public abstract void pop();
    }


    /**
     * 通过wait()和notify()实现的生产者/消费者
     */
    static class Person1 extends Person {


        /**
         * mLock主要是用来配合wait()/notify()方法的调用：
         * 作用wait()/notify()对象的必须与外层synchronized同步的对象是同一个对象，这里涉及到锁的获取/释放，
         * 如果不是同一个对象会抛异常。
         *
         * wait():执行该方法的线程对象，释放同步锁，JVM会把该线程放到等待池中，等待其他线程唤醒该线程
         * notify():执行该方法的线程唤醒在等待池中等待的任意一个线程，把线程转到锁池中等待（注意锁池和等待池的区别）
         * notifyAll():执行该方法的线程唤醒在等待池中等待的所有线程，把线程转到锁池中等待。
         */
        private Object mLock = this/*new Object()*/; //这里也可以是创建的新对象，只要保证是同一个对象即可

        /**
         * 生产数据
         * @param name
         * @param age
         */
        @Override
        public void push(String name, int age) {
            synchronized (mLock) {
                try {
                    /**
                     * 进入到while语句内，说明isEmpty==false，那么表示有数据了，不能生产，必须要等待消费者消费
                     * 不能用if，因为可能有多个线程
                     */
                    while (!isEmpty) {

                        logI(TAG, "push() of while wait() !!!");

                        /**
                         * 导致当前线程等待，进入等待池中，只能被其他线程唤醒才能继续往下执行
                         *
                         * 注意：这里会同时去释放同步锁mLock（synchronized持有），以致另外的线程能进入pop()方法操作
                         */
                        mLock.wait();
                    }

                    //--开始生产
                    this.name = name;
                    try {
                        Thread.sleep(10); //模拟生产耗时
                    } catch (Exception e) {
                    }
                    this.age = age;
                    //--结束生产

                    isEmpty = false; //设置isEmpty为false,表示已经有数据了

                    /**
                     * 生产完毕，唤醒所有消费者线程
                     * （注意：notifyAll()也会唤醒其他正在等待的生产者线程，但是生产者线程通过while()条件判断即使被唤
                     * 醒也仍会进入wait状态）
                     */
                    //mLock.notify(); //notify()是唤醒任意一个线程（如果存在多个消费者线程时不能公平竞争）
                    mLock.notifyAll();

                } catch (Exception e) {
                    logI(TAG, "", e);
                }
            }
        }

        /**
         * 消费数据，说明参见push注释
         */
        @Override
        public void pop() {
            synchronized(mLock) {
                try {
                    while (isEmpty) {

                        logI(TAG, "pop() of while wait() !!!");

                        mLock.wait();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    logI(TAG, name + " -- " + age);

                    isEmpty = true;

                    //mLock.notify();
                    mLock.notifyAll();

                } catch (Exception e) {
                    logI(TAG, "", e);
                }

            }
        }
    }


    /**
     * 使用ReentrantLock(重入锁)实现的生产者/消费者模式
     */
    static class Person2 extends Person {

        /**
         * 重入锁: true--公平锁， false--非公平锁
         *
         * 公平锁指的是线程获取锁的顺序是按照加锁顺序来的，而非公平锁指的是抢锁机制，先lock的线程不一定先获得锁。
         */
        private Lock mLock = new ReentrantLock(false);

        /**
         * 仓库满的条件变量
         */
        private Condition mFullCondition = mLock.newCondition();

        /**
         * 仓库空的条件变量
         */
        private Condition mEmptyCondition = mLock.newCondition();

        @Override
        public void push(String name, int age) {
            mLock.lock();

            try {
                /**
                 * 进入到while语句内，说明isEmpty==false，那么表示有数据了，不能生产，必须要等待消费者消费
                 * 不能用if，因为可能有多个线程
                 */
                while (!isEmpty) {
                    /**
                     * 通过Condition对象来使线程wait，必须先执行lock.lock方法获得锁
                     */
                    mFullCondition.await(); //等价与Object.wait()方法
                }

                //--开始生产
                this.name = name;
                try {
                    Thread.sleep(10); //模拟生产耗时
                } catch (Exception e) {
                }
                this.age = age;
                //--结束生产

                isEmpty = false; //设置isEmpty为false,表示已经有数据了

                /**
                 * 生产完毕，唤醒所有消费者线程
                 * （注意：signalAll()也会唤醒其他正在等待的生产者线程，但是生产者线程通过while()条件判断即使被唤
                 * 醒也仍会进入wait状态）
                 *
                 * 1.一个condition对象的signal（signalAll）方法和该对象的await方法是一一对应的，
                 * 也就是一个condition对象的signal（signalAll）方法不能唤醒其他condition对象的await方法
                 *
                 * 2.ReentrantLock类可以唤醒指定条件的线程，而object.notify()唤醒是随机的
                 */
                //mEmptyCondition.signal(); //condition对象的signal()方法可以唤醒wait线程
                mEmptyCondition.signalAll();

            } catch (Exception e) {

            } finally {
                mLock.unlock();
            }
        }

        @Override
        public void pop() {
            mLock.lock();

            try {
                while (isEmpty) {

                    logI(TAG, "pop() of while await()!!!");

                    mEmptyCondition.await();
                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                logI(TAG, name + " -- " + age);

                isEmpty = true;

                //mFullCondition.signal();
                mFullCondition.signalAll();

            } catch (Exception e) {

            } finally {
                mLock.unlock();
            }
        }
    }

    /**
     * 使用BlockingQueue实现的生产者/消费者模式
     */
    static class Person3 extends Person {
        private final Object mValue = new Object();

        /**
         * SynchronousQueue必须是放和取交替完成的
         */
        private BlockingQueue<Object> mQueue = new SynchronousQueue<>();

        /**
         * 带缓冲区时使用这个缓存
         * @param name
         * @param age
         */
        //private BlockingQueue<Object> mQueue = new LinkedBlockingQueue<>(2);

        @Override
        public void push(String name, int age) {

            try {
                mQueue.put(mValue);
            } catch (Exception e) {
                logI(TAG, "", e);
            }


            //--开始生产
            this.name = name;
            try {
                Thread.sleep(10); //模拟生产耗时
            } catch (Exception e) {
            }
            this.age = age;
            //--结束生产

        }

        @Override
        public void pop() {

            try {
                mQueue.take();
            } catch (Exception e) {
                logI(TAG, "", e);
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            logI(TAG, name + " -- " + age);

        }
    }

    public static void logI(String tag, String message) {
        logI(tag, message, null);
    }

    /**
     * 日志增加线程信息
     * @param tag
     * @param message
     * @param t
     */
    public static void logI(String tag, String message, Throwable t) {
        Log.i(tag, "[" + Thread.currentThread().getId() + "]" + message, t);
    }

}

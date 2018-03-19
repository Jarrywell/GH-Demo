package com.android.test.demo.state;

import android.os.Message;

/**
 * des: demo 坦克攻击的状态机demo(http://ju.outofmemory.cn/entry/145718)
 * author: libingyan
 * Date: 18-3-15 16:28
 */
public class TankStateMachine extends StateMachine {
    private static final String TAG = "Tank";

    private final int BASE_CMD = 0;
    private final int CMD_ATTACK = BASE_CMD + 1; //攻击
    private final int CMD_SETUP_BASE = BASE_CMD + 2; //安装基座
    private final int CMD_REMOVE_BASE = BASE_CMD + 3; //卸载基座
    private final int CMD_SETUP_BARREL = BASE_CMD + 4; //安装炮筒
    private final int CMD_REMOVE_BARREL = BASE_CMD + 5; //卸载炮筒
    private final int CMD_SETUP_BIG_BARREL = BASE_CMD + 6; //安装大口径炮筒
    private final int CMD_REMOVE_BIG_BARREL = BASE_CMD + 7; //卸载大口径炮筒
    private final int CMD_ADD_MISSILE = BASE_CMD + 8; //装填导弹
    private final int CMD_LAUNCH_MISSILE = BASE_CMD + 9; //发射导弹
    private final int CMD_REMOVE_MISSILE = BASE_CMD + 10; //移除导弹
    private final int CMD_ADD_ROCKET = BASE_CMD + 11; //装填火箭
    private final int CMD_LAUNCH_ROCKET = BASE_CMD + 12; //发射火箭
    private final int CMD_REMOVE_ROCKET = BASE_CMD + 13; //移除火箭

    private final int CMD_BASE_SETUP_SUCCESSED = BASE_CMD + 14; //基座已经准备就绪(由其他State发送)
    private final int CMD_BARREL_SETUP_SUCCESSED = BASE_CMD + 15; //炮筒安装完毕
    private final int CMD_BIGBARREL_SETUP_SUCCESSED = BASE_CMD + 16; //大口径炮筒安装完毕


    private State mDefaultState = new DefaultState();
    private State mBaseSetupStartingState = new BaseSetupStartingState();
    private State mBaseSetupFinishedState = new BaseSetupFinishedState();
    private State mBarrelSetupStartingState = new BarrelSetupStartingState();
    private State mBarrelSetupFinishedState = new BarrelSetupFinishedState();
    private State mBigBarrelSetupStartingState = new BigBarrelSetupStartingState();
    private State mBigBarrelSetupFinishedState = new BigBarrelSetupFinishedState();
    private State mAddMissileSuccessedState = new AddMissileSuccessedState();
    private State mAddRocketSuccessedState = new AddRocketSuccessedState();


    public static TankStateMachine make() {
        TankStateMachine hsm = new TankStateMachine();
        hsm.start();

        return hsm;
    }

    private TankStateMachine() {
        super(TAG);
        setDbg(false);

        addState(mDefaultState);
            addState(mBaseSetupStartingState, mDefaultState);
            addState(mBaseSetupFinishedState, mDefaultState);
                addState(mBarrelSetupStartingState, mBaseSetupFinishedState);
                addState(mBarrelSetupFinishedState, mBaseSetupFinishedState);
                    addState(mAddMissileSuccessedState, mBarrelSetupFinishedState);
                addState(mBigBarrelSetupStartingState, mBaseSetupFinishedState);
                addState(mBigBarrelSetupFinishedState, mBaseSetupFinishedState);
                    addState(mAddRocketSuccessedState, mBigBarrelSetupFinishedState);

        setInitialState(mDefaultState);

    }

    /**
     * 安装基座
     */
    public void setupBase() {
        sendMessage(obtainMessage(CMD_SETUP_BASE));
    }

    /**
     * 卸载底座
      */
    public void removeBase() {
        sendMessage(obtainMessage(CMD_REMOVE_BASE));
    }

    /**
     * 安装普通口径炮筒
     */
    public void setupBarrel() {
        sendMessage(obtainMessage(CMD_SETUP_BARREL));
    }

    /**
     * 卸载普通炮筒
      */
    public void removeBarrel() {
        sendMessage(obtainMessage(CMD_REMOVE_BARREL));
    }


    /**
     * 安装大口径炮筒
     */
    public void setupBigBarrel() {
        sendMessage(obtainMessage(CMD_SETUP_BIG_BARREL));
    }

    /**
     * 卸载大口径炮筒
      */
    public void removeBigBarrel() {
        sendMessage(obtainMessage(CMD_SETUP_BIG_BARREL));
    }

    /**
     * 发射，如果已经装填了就发射，无论是装填的是什么；否则就不发射
      */
    public void launch() {
        sendMessage(obtainMessage(CMD_ATTACK));
    }


    /**
     * 装填导弹（普通口径炮筒才能装填）
     */
    public void addMissile() {
        sendMessage(obtainMessage(CMD_ADD_MISSILE));
    }

    /**
     * 发射导弹
      */
    public void launchMissile() {
        sendMessage(CMD_LAUNCH_MISSILE);
    }

    /**
     * 装填火箭（大口径炮筒才能装填）
      */
    public void addRocket() {
        sendMessage(obtainMessage(CMD_ADD_ROCKET));
    }

    /**
     * 发射火箭
      */
    public void launchRocket() {
        sendMessage(obtainMessage(CMD_LAUNCH_ROCKET));
    }



    /**
     * 只有一个履带的状态
     */
    class DefaultState extends State {

        @Override
        public void enter() {
            log("DefaultState.enter()");
        }

        @Override
        public void exit() {
            log("DefaultState.exit()");
        }

        @Override
        public boolean processMessage(Message msg) {
            log("DefaultState.processMessage: " + msg.what);
            switch (msg.what) {
                case CMD_SETUP_BASE: {
                    //直接切换到BaseSetupStartingState进行基座安装工作
                    transitionTo(mBaseSetupStartingState);

                    return HANDLED;
                }
                case CMD_SETUP_BARREL:
                case CMD_SETUP_BIG_BARREL:
                case CMD_ADD_MISSILE:
                case CMD_LAUNCH_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET: {
                    //想做上面几件事,都得先去装基座
                    transitionTo(mBaseSetupStartingState);

                    //然后再让新状态去处理这个Message
                    deferMessage(msg);

                    return HANDLED;
                }
                case CMD_ATTACK:
                case CMD_REMOVE_BASE:
                case CMD_REMOVE_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_REMOVE_MISSILE:
                case CMD_REMOVE_ROCKET: { //不处理

                    return HANDLED;
                }
                default: {
                    return NOT_HANDLED;
                }
            }
        }
    }

    /**
     * 基座安装中
     */
    class BaseSetupStartingState extends State {

        @Override
        public void enter() {
            log("BaseSetupStartingState.enter()");
            try {
                Thread.sleep(500); //模拟基座安装过程
            } catch (Exception e) {}

            //安装完毕后发送安装完成消息
            sendMessage(obtainMessage(CMD_BASE_SETUP_SUCCESSED));
        }

        @Override
        public void exit() {
            log("BaseSetupStartingState.exit()");
            //停下手头做的一切，同时拆除已经安装完成的部分
        }

        @Override
        public boolean processMessage(Message msg) {
            log("BaseSetupStartingState.processMessage: " + msg.what);
            switch (msg.what) {
                case CMD_BASE_SETUP_SUCCESSED: {
                    transitionTo(mBaseSetupFinishedState);

                    return HANDLED;
                }
                case CMD_REMOVE_BASE: {
                    //立刻做一些停止基座安装的操作，再进行已经完成部分的拆除工作

                    //最后切换到DefaultState
                    transitionTo(mDefaultState);

                    return HANDLED;
                }
                case CMD_ATTACK:
                case CMD_SETUP_BASE:
                case CMD_SETUP_BARREL:
                case CMD_SETUP_BIG_BARREL:
                case CMD_ADD_MISSILE:
                case CMD_LAUNCH_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET: {

                    deferMessage(msg);

                    return HANDLED;
                }
                case CMD_REMOVE_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_REMOVE_MISSILE:
                case CMD_REMOVE_ROCKET: {

                    return HANDLED;
                }
                default:
                    return NOT_HANDLED;
            }
        }
    }

    /**
     * 基座安装完毕
     */
    class BaseSetupFinishedState extends State {

        @Override
        public void enter() {
            log("BaseSetupFinishedState.enter()");
        }

        @Override
        public void exit() {
            log("BaseSetupFinishedState.exit()");
        }

        @Override
        public boolean processMessage(Message msg) {
            log("BaseSetupFinishedState.processMessage: " + msg.what);
            switch (msg.what) {
                case CMD_ATTACK:
                case CMD_ADD_MISSILE:
                case CMD_LAUNCH_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET: {
                    deferMessage(msg);
                    return HANDLED;
                }
                case CMD_REMOVE_BASE: {

                    transitionTo(mDefaultState);

                    return HANDLED;
                }
                case CMD_SETUP_BARREL: {

                    transitionTo(mBarrelSetupStartingState);

                    return HANDLED;
                }
                case CMD_SETUP_BIG_BARREL: {

                    transitionTo(mBigBarrelSetupStartingState);

                    return HANDLED;
                }
                case CMD_SETUP_BASE:
                case CMD_REMOVE_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_REMOVE_MISSILE:
                case CMD_REMOVE_ROCKET: {

                    return HANDLED;
                }
                default:
                    return NOT_HANDLED;
            }
        }
    }

    /**
     * 炮筒安装中(必须要在基座安装完毕才能进入该状态)
     */
    class BarrelSetupStartingState extends State {

        @Override
        public void enter() {
            log("BarrelSetupStartingState.enter()");
            try {
                Thread.sleep(500); //模拟炮筒安装过程
            } catch (Exception e) {}
            sendMessage(obtainMessage(CMD_BARREL_SETUP_SUCCESSED));
        }

        @Override
        public void exit() {
            log("BarrelSetupStartingState.exit()");
        }

        @Override
        public boolean processMessage(Message msg) {
            log("BarrelSetupStartingState.processMessage: " + msg.what);
            switch (msg.what) {
                case CMD_BARREL_SETUP_SUCCESSED: {

                    transitionTo(mBarrelSetupFinishedState);

                    return HANDLED;
                }
                case CMD_ATTACK:
                case CMD_SETUP_BARREL:
                case CMD_SETUP_BIG_BARREL:
                case CMD_ADD_MISSILE:
                case CMD_LAUNCH_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET: {

                    deferMessage(msg);

                    return HANDLED;
                }
                case CMD_REMOVE_BASE: {
                    deferMessage(msg);

                    transitionTo(mBaseSetupFinishedState); //先拆除炮筒，即回到基座安装完成状态
                    return HANDLED;
                }
                case CMD_SETUP_BASE:
                case CMD_REMOVE_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_REMOVE_MISSILE:
                case CMD_REMOVE_ROCKET: {

                    return HANDLED;
                }
                default:
                    return NOT_HANDLED;
            }
        }
    }

    /**
     * 炮筒安装完毕(必须要在基座安装完毕才能进入该状态)
     */
    class BarrelSetupFinishedState extends State {

        @Override
        public void enter() {
            log("BarrelSetupFinishedState.enter()");
        }

        @Override
        public void exit() {
            log("BarrelSetupFinishedState.exit()");
        }

        @Override
        public boolean processMessage(Message msg) {
            log("BarrelSetupFinishedState.processMessage: " + msg.what);
            switch (msg.what) {
                case CMD_ATTACK:
                case CMD_SETUP_BIG_BARREL:
                case CMD_LAUNCH_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET: {

                    deferMessage(msg);

                    return HANDLED;
                }
                case CMD_REMOVE_BASE: {
                    deferMessage(msg);

                    transitionTo(mBaseSetupFinishedState);

                    return HANDLED;
                }
                case CMD_REMOVE_BARREL: {

                    transitionTo(mBaseSetupFinishedState);

                    return HANDLED;
                }
                case CMD_ADD_MISSILE: {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {}
                    transitionTo(mAddMissileSuccessedState);
                    return HANDLED;
                }
                case CMD_SETUP_BASE:
                case CMD_SETUP_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_REMOVE_MISSILE:
                case CMD_REMOVE_ROCKET: {

                    return HANDLED;
                }
                default:
                    return NOT_HANDLED;
            }
        }
    }

    /**
     * 大口径炮筒安装中(必须要在基座安装完毕才能进入该状态)
     */
    class BigBarrelSetupStartingState extends State {

    }

    /**
     * 大口径炮筒安装完毕(必须要在基座安装完毕才能进入该状态)
     */
    class BigBarrelSetupFinishedState extends State {

    }

    /**
     * 成功装填导弹(必须要在炮筒安装完毕才能进入该状态)
     */
    class AddMissileSuccessedState extends State {

        @Override
        public void enter() {
            log("AddMissileSuccessedState.enter()");
        }

        @Override
        public void exit() {
            log("AddMissileSuccessedState.exit()");
        }

        @Override
        public boolean processMessage(Message msg) {
            log("AddMissileSuccessedState.processMessage: " + msg.what);

            switch (msg.what) {
                case CMD_ATTACK:
                case CMD_LAUNCH_MISSILE: {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {}

                    transitionTo(mBarrelSetupFinishedState);

                    return HANDLED;
                }
                case CMD_REMOVE_MISSILE: {
                    transitionTo(mBarrelSetupFinishedState);
                    return HANDLED;
                }
                case CMD_REMOVE_BASE:
                case CMD_SETUP_BASE:
                case CMD_SETUP_BARREL:
                case CMD_REMOVE_BARREL:
                case CMD_SETUP_BIG_BARREL:
                case CMD_REMOVE_BIG_BARREL:
                case CMD_ADD_MISSILE:
                case CMD_ADD_ROCKET:
                case CMD_LAUNCH_ROCKET:
                case CMD_REMOVE_ROCKET: {

                    return HANDLED;
                }
            }
            return true;
        }
    }


    /**
     * 成功装填火箭(必须要在大口径炮筒安装完毕才能进入该状态)
     */
    class AddRocketSuccessedState extends State {

    }

}

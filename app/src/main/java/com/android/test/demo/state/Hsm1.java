package com.android.test.demo.state;

import android.os.Message;
import android.util.Log;

/**
 * des:
 * author: libingyan
 * Date: 18-3-13 16:42
 */
public class Hsm1 extends StateMachine {
    private static final String TAG = "Hsm1";

    public static final int CMD_1 = 1;
    public static final int CMD_2 = 2;
    public static final int CMD_3 = 3;
    public static final int CMD_4 = 4;
    public static final int CMD_5 = 5;

    private P1 mP1 = new P1();
    private S1 mS1 = new S1();
    private S2 mS2 = new S2();
    private P2 mP2 = new P2();


    public static Hsm1 makeStateMachine() {
        Hsm1 sm = new Hsm1(TAG);
        sm.setDbg(false);
        sm.start();

        return sm;
    }

    private Hsm1(String name) {
        super(name);

        addState(mP1);
            addState(mS1, mP1);
            addState(mS2, mP1);
        addState(mP2);

        setInitialState(mS1);
    }

    private class P1 extends State {

        @Override
        public void enter() {
            log("P1.enter()");
        }

        @Override
        public boolean processMessage(Message msg) {
            boolean retVal;
            log("P1.processMessage what: " + msg.what);
            switch (msg.what) {
                case CMD_2: {
                    // CMD_2 will arrive in mS2 before CMD_3
                    sendMessage(obtainMessage(CMD_3));

                    deferMessage(msg); //CMD_2, CMD_2早于CMD_3

                    transitionTo(mS2);

                    retVal = HANDLED;
                    break;
                }
                default: {
                    retVal = NOT_HANDLED;
                    break;
                }
            }

            return retVal;
        }

        @Override
        public void exit() {
            log("P1.exit()");
        }
    }

    private class S1 extends State {

        @Override
        public void enter() {
            log("S1.enter()");
        }

        @Override
        public boolean processMessage(Message msg) {
            boolean retVal;
            log("S1.processMessage what: " + msg.what);
            if (msg.what == CMD_1) {
                // Transition to ourself to show that enter/exit is called
                transitionTo(mS1);

                retVal = HANDLED;
            } else {
                retVal = NOT_HANDLED;
            }
            return retVal;
        }

        @Override
        public void exit() {
            log("S1.exit()");
        }
    }

    private class S2 extends State {

        @Override
        public void enter() {
            log("S2.enter()");
        }

        @Override
        public boolean processMessage(Message msg) {
            boolean retVal;
            log("S2.processMessage what: " + msg.what);
            switch (msg.what) {
                case CMD_2: {
                    sendMessage(obtainMessage(CMD_4));
                    retVal = HANDLED;
                    break;
                }
                case CMD_3: {
                    deferMessage(msg);
                    transitionTo(mP2);

                    retVal = HANDLED;
                    break;
                }
                default: {
                    retVal = NOT_HANDLED;
                    break;
                }
            }
            return retVal;
        }

        @Override
        public void exit() {
            log("S2.exit()");
        }


    }

    private class P2 extends State {

        @Override
        public void enter() {
            log("P2.enter()");
            sendMessage(obtainMessage(CMD_5));
        }

        @Override
        public boolean processMessage(Message msg) {
            log("P2.processMessage what: " + msg.what);
            switch (msg.what) {
                case CMD_3: {

                    break;
                }
                case CMD_4: {

                    break;
                }
                case CMD_5: {
                    transitionToHaltingState();
                    break;
                }
            }

            return HANDLED;
        }

        @Override
        public void exit() {
            log("P2.exit()");
        }
    }

    @Override
    protected void onHalting() {
        log("sm.onHalting()");
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    protected void haltedProcessMessage(Message msg) {
        log("sm.haltedProcessMessage() what: " + msg.what);
    }

    @Override
    protected void onQuitting() {
        log("sm.onQuitting()");
    }

    public static void test() {
        Hsm1 sm = makeStateMachine();
        synchronized (sm) {
            sm.sendMessage(sm.obtainMessage(Hsm1.CMD_1));
            sm.sendMessage(sm.obtainMessage(Hsm1.CMD_2));
            try {
                sm.wait();
            } catch (Exception e) {
                Log.i(TAG, "exception!!!", e);
            }
            //transitionToHaltingState()后续消息直接转入haltedProcessMessage()
            sm.sendMessage(sm.obtainMessage(Hsm1.CMD_1));
            sm.sendMessage(sm.obtainMessage(Hsm1.CMD_2));
            //sm.quit(); //排队等待所有消息处理完毕后再退出
            sm.quitNow(); //立即退出
        }
    }
}

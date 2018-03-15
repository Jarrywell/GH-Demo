package com.android.test.demo.state;

import android.os.Message;

/**
 * des:
 * author: libingyan
 * Date: 18-3-13 16:42
 */
public class Hsm2 extends StateMachine {
    private static final String TAG = "Hsm2";

    private static final String ENTER = "enter";
    private static final String EXIT = "exit";
    private static final String ON_QUITTING = "ON_QUITTING";

    private static final int TEST_CMD_1 = 1;
    private static final int TEST_CMD_2 = 2;
    private static final int TEST_CMD_3 = 3;
    private static final int TEST_CMD_4 = 4;
    private static final int TEST_CMD_5 = 5;
    private static final int TEST_CMD_6 = 6;


    public static Hsm2 test() {
        Hsm2 sm = new Hsm2(TAG);
        sm.setDbg(true);
        sm.start();

        sm.sendMessage(TEST_CMD_1);

        return sm;
    }


    Hsm2(String name) {
        super(name);
        setDbg(false);

        // Setup state machine with 1 state
        addState(mS1);
        addState(mS2);
        addState(mS3);
        addState(mS4);

        // Set the initial state
        setInitialState(mS1);
    }

    class S1 extends State {
        @Override
        public void enter() {
            // Test transitions in enter on the initial state work
            addLogRec(ENTER);
            transitionTo(mS2);
            log("S1.enter");
        }
        @Override
        public void exit() {
            addLogRec(EXIT);
            log("S1.exit");
        }
    }

    class S2 extends State {
        @Override
        public void enter() {
            addLogRec(ENTER);
            log("S2.enter");
        }
        @Override
        public void exit() {
            // Test transition in exit work
            transitionTo(mS4);

            addLogRec(EXIT);

            log("S2.exit");
        }

        @Override
        public boolean processMessage(Message message) {
            // Start a transition to S3 but it will be
            // changed to a transition to S4 in exit
            transitionTo(mS3);
            log("S2.processMessage");
            return HANDLED;
        }
    }

    class S3 extends State {
        @Override
        public void enter() {
            addLogRec(ENTER);
            log("S3.enter");
        }
        @Override
        public void exit() {
            addLogRec(EXIT);
            log("S3.exit");
        }
    }

    class S4 extends State {
        @Override
        public void enter() {
            addLogRec(ENTER);
            // Test that we can do halting in an enter/exit
            transitionToHaltingState();
            log("S4.enter");
        }
        @Override
        public void exit() {
            addLogRec(EXIT);
            log("S4.exit");
        }
    }

    @Override
    protected void onHalting() {
        log("onHalting");
    }

    private S1 mS1 = new S1();
    private S2 mS2 = new S2();
    private S3 mS3 = new S3();
    private S4 mS4 = new S4();
}

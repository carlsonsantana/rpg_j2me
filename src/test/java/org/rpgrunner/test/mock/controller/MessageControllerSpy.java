package org.rpgrunner.test.mock.controller;

import org.rpgrunner.controller.MessageController;

public class MessageControllerSpy implements MessageController {
    private String lastMessage;
    private int pressedKey;
    private int releasedKey;
    private boolean finished;

    public void pressKey(final int key) {
        pressedKey = key;
    }

    public int getPressedKey() {
        return pressedKey;
    }

    public void releaseKey(final int key) {
        releasedKey = key;
    }

    public int getReleasedKey() {
        return releasedKey;
    }

    public void showMessage(final String message) {
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public boolean isFinished() {
        return finished;
    }

    public void finish() {
        finished = true;
    }
}

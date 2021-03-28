package org.rpgrunner.test.mock.event.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import org.rpgrunner.controller.GameController;
import org.rpgrunner.event.action.Action;
import org.rpgrunner.event.factory.ActionAbstractFactory;
import org.rpgrunner.test.mock.controller.GameControllerSpy;
import org.rpgrunner.test.mock.event.action.ActionSpy;

public class ActionAbstractFactorySpy extends ActionAbstractFactory {
    private final Vector actionsCreated;

    public ActionAbstractFactorySpy() {
        this(new GameControllerSpy());
    }

    public ActionAbstractFactorySpy(final GameController gameController) {
        super(gameController, null, null, null);
        actionsCreated = new Vector();
    }

    public Action create(final InputStream inputStream) throws IOException {
        Action action = new ActionSpy();
        actionsCreated.addElement(action);

        return action;
    }

    public ActionSpy[] getActionsCreated() {
        ActionSpy[] arrayActionsCreated = new ActionSpy[actionsCreated.size()];
        int index = 0;

        for (
            Enumeration enumeration = actionsCreated.elements();
            enumeration.hasMoreElements();
        ) {
            arrayActionsCreated[index++] = (
                (ActionSpy) enumeration.nextElement()
            );
        }

        return arrayActionsCreated;
    }
}

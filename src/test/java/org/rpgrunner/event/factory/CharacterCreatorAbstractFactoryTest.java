package org.rpgrunner.event.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.rpgrunner.controller.GameController;
import org.rpgrunner.event.action.Action;
import org.rpgrunner.test.helper.HelperActionAbstractFactory;

public class CharacterCreatorAbstractFactoryTest
    extends AbstractCharacterCreatorFactoryTest {
    private static final byte CHARACTER_CREATOR_FACTORY = (byte) 3;

    protected InputStream generateInputStream(final byte[] byteArray) {
        int length = byteArray.length;
        int newLength = length + 1;
        byte[] newByteArray = new byte[newLength];

        newByteArray[0] = CHARACTER_CREATOR_FACTORY;
        System.arraycopy(byteArray, 0, newByteArray, 1, length);

        return new ByteArrayInputStream(newByteArray);
    }

    protected Action createAction(
        final InputStream inputStream,
        final GameController gameController
    ) throws IOException {
        return HelperActionAbstractFactory.createAction(
            gameController,
            inputStream
        );
    }
}

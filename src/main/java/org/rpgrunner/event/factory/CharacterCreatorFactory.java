package org.rpgrunner.event.factory;

import java.io.IOException;
import java.io.InputStream;

import org.rpgrunner.GameController;
import org.rpgrunner.event.action.Action;
import org.rpgrunner.event.action.CharacterCreator;
import org.rpgrunner.helper.Loader;

public class CharacterCreatorFactory {
    private final GameController gameController;

    public CharacterCreatorFactory(final GameController currentGameController) {
        gameController = currentGameController;
    }

    public Action create(final InputStream inputStream) throws IOException {
        String fileBaseName = Loader.extractString(inputStream);
        int mapPositionX = inputStream.read();
        int mapPositionY = inputStream.read();
        CharacterCreator characterCreator = new CharacterCreator(
            gameController,
            fileBaseName,
            mapPositionX,
            mapPositionY
        );

        return characterCreator;
    }
}

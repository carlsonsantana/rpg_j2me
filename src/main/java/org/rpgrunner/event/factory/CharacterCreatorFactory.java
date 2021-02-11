package org.rpgrunner.event.factory;

import org.rpgrunner.GameController;
import org.rpgrunner.event.action.AbstractCharacterCreator;
import org.rpgrunner.event.action.CharacterCreator;

public class CharacterCreatorFactory extends AbstractCharacterCreatorFactory {
    public CharacterCreatorFactory(final GameController gameController) {
        super(gameController);
    }

    public AbstractCharacterCreator create(
        final GameController gameController,
        final String fileBaseName,
        final int mapPositionX,
        final int mapPositionY
    ) {
        CharacterCreator characterCreator = new CharacterCreator(
            gameController,
            fileBaseName,
            mapPositionX,
            mapPositionY
        );

        return characterCreator;
    }
}

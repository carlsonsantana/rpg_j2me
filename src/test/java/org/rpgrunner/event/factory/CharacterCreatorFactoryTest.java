package org.rpgrunner.event.factory;

import org.rpgrunner.character.CharacterElement;
import org.rpgrunner.test.mock.GameControllerSpy;

public class CharacterCreatorFactoryTest
        extends AbstractCharacterCreatorFactoryTest {
    public AbstractCharacterCreatorFactory createFactory(
        final GameControllerSpy gameController
    ) {
        return new CharacterCreatorFactory(gameController);
    }

    public CharacterElement getCharacterCreated(
        final GameControllerSpy gameController
    ) {
        return gameController.getLastCharacterElementAdded();
    }
}

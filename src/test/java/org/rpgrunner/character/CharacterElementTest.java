package org.rpgrunner.character;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.rpgrunner.character.movement.MovementCommand;
import org.rpgrunner.character.movement.RandomMovement;
import org.rpgrunner.test.helper.RandomGenerator;
import org.rpgrunner.test.mock.character.CharacterAnimationSpy;
import org.rpgrunner.test.mock.helper.CollisionDetectorSpy;

public class CharacterElementTest extends TestCase {
    private CharacterElement characterElement;
    private CollisionDetectorSpy collisionDetector;
    private GameCharacter character;
    private CharacterAnimationSpy characterAnimation;
    private MovementCommand movementCommand;

    public void setUp() {
        collisionDetector = new CollisionDetectorSpy();
        character = RandomGenerator.generateRandomCharacter();
        characterAnimation = new CharacterAnimationSpy();
        movementCommand = new RandomMovement(character);

        characterElement = new CharacterElement(
            collisionDetector,
            character,
            characterAnimation,
            movementCommand
        );
    }

    public void testReturnSameCharacter() {
        Assert.assertSame(character, characterElement.getCharacter());
    }

    public void testReturnSameCharacterAnimation() {
        Assert.assertSame(
            characterAnimation,
            characterElement.getCharacterAnimation()
        );
    }

    public void testReturnSameMovementCommand() {
        Assert.assertSame(
            movementCommand,
            characterElement.getMovementCommand()
        );
    }

    public void testOnMoveFalseCancelCharacterMovement() {
        testOnMove(false);
    }

    public void testOnMoveTrueContinueCharacterMovement() {
        testOnMove(true);
    }

    private void testOnMove(final boolean canMove) {
        int y = character.getMapPositionY();
        int movementDifference = canMove ? -1 : 0;
        int nextY = y + movementDifference;

        collisionDetector.setCanMove(canMove);
        character.moveUp();
        characterElement.onMove();

        Assert.assertEquals(canMove, character.isMoving());
        Assert.assertEquals(y, character.getMapPositionY());
        Assert.assertEquals(nextY, character.getMapNextPositionY());
        Assert.assertTrue(characterAnimation.isStartAnimationCalled());
    }

    public void testOnAnimationCompleteFinishCharacterMovement() {
        int y = character.getMapPositionY();
        int nextY = y - 1;

        collisionDetector.setCanMove(true);
        character.moveUp();
        characterElement.onAnimationComplete();

        Assert.assertFalse(character.isMoving());
        Assert.assertEquals(nextY, character.getMapPositionY());
        Assert.assertEquals(nextY, character.getMapNextPositionY());
    }
}

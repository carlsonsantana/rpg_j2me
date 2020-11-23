package org.rpgrunner.game.character;

import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.rpgrunner.game.Direction;
import org.rpgrunner.test.helper.RandomGenerator;
import org.rpgrunner.test.helper.TestAllDirections;

public class GameCharacterTest extends TestCase {
    private GameCharacter character;

    public void setUp() {
        character = new GameCharacter(null);
    }

    public void testReturnSameFileBaseName() {
        String randomFileBaseName = RandomGenerator.getRandomString();
        character = new GameCharacter(randomFileBaseName);
        Assert.assertEquals(randomFileBaseName, character.getFileBaseName());
    }

    public void testCharacterStartStopped() {
        Assert.assertFalse(character.isMoving());
    }

    public void testFinishMoveWhenCharacterIsStopped() {
        byte direction = character.getDirection();
        int initialPositionX = character.getMapPositionX();
        int initialPositionY = character.getMapPositionY();

        testChangePositionWhenFinishMoviment(
            character,
            direction,
            initialPositionX,
            initialPositionY
        );
    }

    public void testMoveWhenAnimationIsCompleted() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testChangePositionOnMoviment(
                    characterTest,
                    direction,
                    initialPositionX,
                    initialPositionY,
                    newPositionX,
                    newPositionY
                );
            }
        };
        testAllDirections.test();
    }

    private void testChangePositionOnMoviment(
        final GameCharacter characterTest,
        final byte direction,
        final int initialPositionX,
        final int initialPositionY,
        final int newPositionX,
        final int newPositionY
    ) {
        Assert.assertTrue(characterTest.isMoving());
        Assert.assertEquals(direction, characterTest.getDirection());
        Assert.assertEquals(initialPositionX, characterTest.getMapPositionX());
        Assert.assertEquals(initialPositionY, characterTest.getMapPositionY());
        Assert.assertEquals(newPositionX, characterTest.getMapNextPositionX());
        Assert.assertEquals(newPositionY, characterTest.getMapNextPositionY());

        testChangePositionWhenFinishMoviment(
            characterTest,
            direction,
            newPositionX,
            newPositionY
        );
    }

    private void testChangePositionWhenFinishMoviment(
        final GameCharacter characterTest,
        final byte direction,
        final int finalPositionX,
        final int finalPositionY
    ) {
        characterTest.finishMove();

        testCharacterStoppedPosition(
            characterTest,
            direction,
            finalPositionX,
            finalPositionY
        );
    }

    private void testCharacterStoppedPosition(
        final GameCharacter characterTest,
        final byte direction,
        final int finalPositionX,
        final int finalPositionY
    ) {
        Assert.assertFalse(characterTest.isMoving());
        Assert.assertEquals(direction, characterTest.getDirection());
        Assert.assertEquals(finalPositionX, characterTest.getMapPositionX());
        Assert.assertEquals(finalPositionY, characterTest.getMapPositionY());
        Assert.assertEquals(
            finalPositionX,
            characterTest.getMapNextPositionX()
        );
        Assert.assertEquals(
            finalPositionY,
            characterTest.getMapNextPositionY()
        );
    }

    public void testOnlyExecuteNewMoveToUpWhenMoveIsFinished() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testNewMoveToUpDirection(
                    characterTest,
                    direction,
                    newPositionX,
                    newPositionY
                );
            }

            private void testNewMoveToUpDirection(
                final GameCharacter characterTest,
                final byte direction,
                final int finalPositionX,
                final int finalPositionY
            ) {
                characterTest.moveUp();
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    finalPositionX,
                    finalPositionY
                );
            }
        };
        testAllDirections.test();
    }

    public void testOnlyExecuteNewMoveToRightWhenMoveIsFinished() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testNewMoveToRightDirection(
                    characterTest,
                    direction,
                    newPositionX,
                    newPositionY
                );
            }

            private void testNewMoveToRightDirection(
                final GameCharacter characterTest,
                final byte direction,
                final int finalPositionX,
                final int finalPositionY
            ) {
                characterTest.moveRight();
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    finalPositionX,
                    finalPositionY
                );
            }
        };
        testAllDirections.test();
    }

    public void testOnlyExecuteNewMoveToDownWhenMoveIsFinished() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testNewMoveToDownDirection(
                    characterTest,
                    direction,
                    newPositionX,
                    newPositionY
                );
            }

            private void testNewMoveToDownDirection(
                final GameCharacter characterTest,
                final byte direction,
                final int finalPositionX,
                final int finalPositionY
            ) {
                characterTest.moveDown();
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    finalPositionX,
                    finalPositionY
                );
            }
        };
        testAllDirections.test();
    }

    public void testOnlyExecuteNewMoveToLeftWhenMoveIsFinished() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testNewMoveToLeftDirection(
                    characterTest,
                    direction,
                    newPositionX,
                    newPositionY
                );
            }

            private void testNewMoveToLeftDirection(
                final GameCharacter characterTest,
                final byte direction,
                final int finalPositionX,
                final int finalPositionY
            ) {
                characterTest.moveLeft();
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    finalPositionX,
                    finalPositionY
                );
            }
        };
        testAllDirections.test();
    }

    public void testMoveMoreThanOneTime() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    newPositionX,
                    newPositionY
                );

                testRandomMoviment(direction);
            }

            private void testRandomMoviment(final byte direction) {
                Random random = new Random();
                int randomDirection = random.nextInt(
                    Direction.NUMBER_DIRECTIONS + 1
                );
                if (randomDirection == 1) {
                    testMoveToUp();
                } else if (randomDirection == 2) {
                    testMoveToRight();
                } else if (randomDirection == 3) {
                    testMoveToDown();
                } else if (randomDirection == 4) {
                    testMoveToLeft();
                }
            }
        };
        for (int i = 0, numberTests = 100; i < numberTests; i++) {
            testAllDirections.test();
        }
    }

    public void testCancelMove() {
        TestAllDirections testAllDirections = new TestAllDirections(character) {
            public void testMoviment(
                final GameCharacter characterTest,
                final byte direction,
                final int initialPositionX,
                final int initialPositionY,
                final int newPositionX,
                final int newPositionY
            ) {
                characterTest.cancelMove();
                testCharacterStoppedPosition(
                    characterTest,
                    direction,
                    initialPositionX,
                    initialPositionY
                );
                testChangePositionWhenFinishMoviment(
                    characterTest,
                    direction,
                    initialPositionX,
                    initialPositionY
                );
            }
        };
        testAllDirections.test();
    }
}

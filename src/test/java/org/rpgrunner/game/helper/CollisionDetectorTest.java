package org.rpgrunner.game.helper;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.rpgrunner.test.mock.MapSpy;
import org.rpgrunner.test.mock.CharacterSpy;
import org.rpgrunner.game.character.GameCharacter;
import org.rpgrunner.test.helper.TestCollisionAllDirections;
import org.rpgrunner.test.helper.RandomGenerator;

public class CollisionDetectorTest extends TestCase {
    private CollisionDetector collisionDetector;

    public void setUp() {
        collisionDetector = new CollisionDetector();
    }

    public void testReturnSameMap() {
        MapSpy map = new MapSpy();
        collisionDetector.setMap(map);

        Assert.assertEquals(map, collisionDetector.getMap());
    }

    public void testReturnSameEmptyCharacters() {
        CharacterSpy[] characters = new CharacterSpy[0];
        collisionDetector.setCharacters(characters);
        GameCharacter[] sameCharacters = collisionDetector.getCharacters();

        Assert.assertEquals(characters, sameCharacters);
        Assert.assertEquals(0, sameCharacters.length);
    }

    public void testReturnSameCharacters() {
        CharacterSpy[] characters = RandomGenerator.generateRandomCharacters();
        CharacterSpy[] copyCharacters = cloneArrayCharacters(characters);
        int numberCharacters = characters.length;

        collisionDetector.setCharacters(characters);
        GameCharacter[] sameCharacters = collisionDetector.getCharacters();

        Assert.assertEquals(characters, sameCharacters);
        Assert.assertEquals(numberCharacters, sameCharacters.length);
        for (int i = 0; i < numberCharacters; i++) {
            Assert.assertEquals(sameCharacters[i], copyCharacters[i]);
        }
    }

    private CharacterSpy[] cloneArrayCharacters(
        final CharacterSpy[] characters
    ) {
        int numberCharacters = characters.length;
        CharacterSpy[] copyCharacters = new CharacterSpy[numberCharacters];
        for (int i = 0; i < numberCharacters; i++) {
            CharacterSpy character = characters[i];
            copyCharacters[i] = character;
        }
        return copyCharacters;
    }

    public void testCantMoveWhenExistsAMapCollision() {
        MapSpy map = new MapSpy();
        CharacterSpy[] characters = RandomGenerator.generateRandomCharacters();
        collisionDetector.setMap(map);
        collisionDetector.setCharacters(characters);

        map.setCanMoveTo(false);
        CharacterSpy character = RandomGenerator.getRandomCharacter(characters);

        Assert.assertFalse(collisionDetector.canMove(character));
    }

    public void testCantMoveUpWhenExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                0,
                -1
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveUp();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertFalse(
                        collisionDetector.canMove(characterTest)
                    );
                }
            };
            test.test();
        }
    }

    public void testCantMoveRightWhenExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                1,
                0
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveRight();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertFalse(
                        collisionDetector.canMove(characterTest)
                    );
                }
            };
            test.test();
        }
    }

    public void testCantMoveDownWhenExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                0,
                1
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveDown();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertFalse(
                        collisionDetector.canMove(characterTest)
                    );
                }
            };
            test.test();
        }
    }

    public void testCantMoveLeftWhenExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                -1,
                0
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveLeft();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertFalse(
                        collisionDetector.canMove(characterTest)
                    );
                }
            };
            test.test();
        }
    }

    public void testCanMoveUpWhenNotExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                0,
                -2
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveUp();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertTrue(collisionDetector.canMove(characterTest));
                }
            };
            test.test();
        }
    }

    public void testCanMoveRightWhenNotExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                2,
                0
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveRight();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertTrue(collisionDetector.canMove(characterTest));
                }
            };
            test.test();
        }
    }

    public void testCanMoveDownWhenNotExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                0,
                2
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveDown();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertTrue(collisionDetector.canMove(characterTest));
                }
            };
            test.test();
        }
    }

    public void testCanMoveLeftWhenNotExistsACharacterCollision() {
        for (int i = 0; i < 100; i++) {
            TestCollisionAllDirections test = new TestCollisionAllDirections(
                collisionDetector,
                -2,
                0
            ) {
                public void moveCharacter(final GameCharacter characterTest) {
                    characterTest.moveLeft();
                }

                public void testOperation(final GameCharacter characterTest) {
                    Assert.assertTrue(collisionDetector.canMove(characterTest));
                }
            };
            test.test();
        }
    }
}

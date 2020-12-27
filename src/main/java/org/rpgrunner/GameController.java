package org.rpgrunner;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import org.rpgrunner.character.CharacterAnimation;
import org.rpgrunner.character.CharacterElement;
import org.rpgrunner.character.GameCharacter;
import org.rpgrunner.helper.CollisionDetector;
import org.rpgrunner.helper.Camera;
import org.rpgrunner.j2me.CharacterAnimationImpl;
import org.rpgrunner.j2me.MapRender;
import org.rpgrunner.map.Map;
import org.rpgrunner.map.MapLoader;
import org.rpgrunner.command.Command;
import org.rpgrunner.command.RandomCommand;
import org.rpgrunner.command.PlayerCommand;
import org.rpgrunner.j2me.command.PlayerCommandImpl;

public class GameController {
    private final Graphics graphics;
    private final LayerManager layerManager;
    private final Camera camera;
    private final CollisionDetector collisionDetector;
    private Map map;
    private CharacterElement playerCharacterElement;
    private CharacterElement[] characterElements;
    private int gameAction;

    public GameController(
        final Graphics midletGraphics,
        final int screenWidth,
        final int screenHeight
    ) {
        graphics = midletGraphics;
        layerManager = new LayerManager();
        camera = new Camera(screenWidth, screenHeight);

        collisionDetector = new CollisionDetector();

        setMap(MapLoader.loadMap("map"));
        playerCharacterElement = generatePlayerCharacterElement("character");
        setCharacters();
    }

    public void setMap(final Map newMap) {
        map = newMap;
        MapRender mapRender = new MapRender(map);
        clearMapLayerManager();
        TiledLayer[] tiledLayers = mapRender.getTiledLayers();
        for (int i = tiledLayers.length - 1; i >= 0; i--) {
            layerManager.append(tiledLayers[i]);
        }

        collisionDetector.setMap(map);
    }

    private void clearMapLayerManager() {
        int size = layerManager.getSize();
        for (int i = 1; (i < size) && (i <= 2); i++) {
            Layer layer = layerManager.getLayerAt(size - i);
            if (layer instanceof TiledLayer) {
                layerManager.remove(layer);
            }
        }
    }

    private void setCharacters() {
        CharacterElement characterElement = generateNPCCharacterElement(
            "character"
        );

        characterElements = new CharacterElement[2];
        characterElements[0] = playerCharacterElement;
        characterElements[1] = characterElement;
        for (int i = 0; i < characterElements.length; i++) {
            CharacterAnimation characterAnimation = (
                characterElements[i].getCharacterAnimation()
            );
            layerManager.insert((Sprite) characterAnimation.getSprite(), 0);
        }

        collisionDetector.setCharacters(new GameCharacter[] {
            playerCharacterElement.getCharacter(),
            characterElement.getCharacter()
        });
    }

    private CharacterElement generateNPCCharacterElement(
        final String baseName
    ) {
        GameCharacter character = new GameCharacter(baseName);
        RandomCommand command = new RandomCommand(character);

        return generateCharacterElement(character, command);
    }

    private CharacterElement generatePlayerCharacterElement(
        final String baseName
    ) {
        GameCharacter character = new GameCharacter(baseName);
        PlayerCommand command = new PlayerCommandImpl(character);

        return generateCharacterElement(character, command);
    }

    private CharacterElement generateCharacterElement(
        final GameCharacter character,
        final Command command
    ) {
        CharacterAnimation characterAnimation = new CharacterAnimationImpl(
            character
        );
        CharacterElement characterElement = new CharacterElement(
            collisionDetector,
            character,
            characterAnimation,
            command
        );
        character.setCharacterElement(characterElement);
        characterAnimation.setCharacterElement(characterElement);

        return characterElement;
    }

    public void setGameAction(final int newGameAction) {
        gameAction = newGameAction;
    }

    public void preRender() {
        GameCharacter playerCharacter = playerCharacterElement.getCharacter();
        if (gameAction == GameCanvas.UP) {
            playerCharacter.moveUp();
        } else if (gameAction == GameCanvas.RIGHT) {
            playerCharacter.moveRight();
        } else if (gameAction == GameCanvas.DOWN) {
            playerCharacter.moveDown();
        } else if (gameAction == GameCanvas.LEFT) {
            playerCharacter.moveLeft();
        }

        moveNPCs();
    }

    private void moveNPCs() {
        for (int i = 0; i < characterElements.length; i++) {
            CharacterElement characterElement = characterElements[i];

            if (characterElement != playerCharacterElement) {
                GameCharacter character = characterElement.getCharacter();
                Random random = new Random();
                int direction = random.nextInt(Direction.NUMBER_DIRECTIONS);
                if (direction == 0) {
                    character.moveUp();
                } else if (direction == 1) {
                    character.moveRight();
                } else if (direction == 2) {
                    character.moveDown();
                } else {
                    character.moveLeft();
                }
            }
        }
    }

    public void render() {
        preRenderCharacters();
        centerCamera();
        layerManager.paint(graphics, 0, 0);
    }

    private void preRenderCharacters() {
        for (int i = 0; i < characterElements.length; i++) {
            CharacterAnimation characterAnimation = (
                characterElements[i].getCharacterAnimation()
            );

            characterAnimation.doAnimation();
        }
    }

    private void centerCamera() {
        camera.centerCamera(
            map,
            playerCharacterElement.getCharacterAnimation()
        );
        layerManager.setViewWindow(
            camera.getX(),
            camera.getY(),
            camera.getScreenWidth(),
            camera.getScreenHeight()
        );
    }
}

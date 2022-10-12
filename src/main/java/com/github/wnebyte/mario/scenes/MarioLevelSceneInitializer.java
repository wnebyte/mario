package com.github.wnebyte.mario.scenes;

import org.joml.Vector3f;
import com.github.wnebyte.sproink.components.TextRenderer;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.core.SceneInitializer;
import com.github.wnebyte.sproink.core.Transform;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;
import com.github.wnebyte.mario.components.GameCamera;
import com.github.wnebyte.mario.components.ScoringSystem;
import com.github.wnebyte.mario.components.TimeSystem;

public class MarioLevelSceneInitializer implements SceneInitializer {

    private static final String ASSETS = Context.getAssetsDir();

    @Override
    public void init(Scene scene) {
        // add scoring system
        GameObject scoringSystemGo = Scene.createGameObject("ScoringSystem");
        scoringSystemGo.addComponent(new ScoringSystem());
        scoringSystemGo.setNoSerialize();
        scene.addGameObjectToScene(scoringSystemGo);

        // add time system
        GameObject timeSystemGo = Scene.createGameObject("TimeSystem");
        timeSystemGo.addComponent(new TimeSystem(400));
        timeSystemGo.setNoSerialize();
        scene.addGameObjectToScene(timeSystemGo);

        // add game camera
        GameObject gameCameraGo = Scene.createGameObject("GameCamera");
        gameCameraGo.addComponent(new GameCamera(scene.getCamera()));
        gameCameraGo.setNoSerialize();
        scene.addGameObjectToScene(gameCameraGo);

        // add text
        GameObject go = Scene.createGameObject("Score_Label");
        go.setNoSerialize();
        Transform transform = go.getComponent(Transform.class);
        float scale = 0.0025f;
        transform.position.x = 0.45f;
        transform.position.y = 2.75f;
        transform.scale.x = scale;
        transform.scale.y = scale;
        TextRenderer tr = new TextRenderer();
        tr.setText("MARIO");
        tr.setColor(new Vector3f(1, 1, 1));
        go.addComponent(tr);
        scene.addGameObjectToScene(go);

        go = Scene.createGameObject("Score_Value");
        go.setNoSerialize();
        transform = go.getComponent(Transform.class);
        transform.position.x = 0.45f;
        transform.position.y = 2.65f;
        transform.scale.x = scale;
        transform.scale.y = scale;
        tr = new TextRenderer();
        tr.setText("000000");
        tr.setColor(new Vector3f(1f, 1f, 1f));
        go.addComponent(tr);
        scene.addGameObjectToScene(go);

        go = Scene.createGameObject("Time_Value");
        go.setNoSerialize();
        transform = go.getComponent(Transform.class);
        transform.position.x = 6 - 0.8f;
        transform.position.y = 2.65f;
        transform.scale.x = scale;
        transform.scale.y = scale;
        tr = new TextRenderer();
        tr.setText("000");
        tr.setColor(new Vector3f(1f, 1f, 1f));
        go.addComponent(tr);
        scene.addGameObjectToScene(go);
    }

    @Override
    public void loadResources(Scene scene) {
        // add spritesheets
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/decorationsAndBlocks.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/spritesheet.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/spritesheet.png"),
                        16, 16, 26, 0));
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/turtle.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/turtle.png"),
                        16, 24, 4, 0));
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/bigSpritesheet.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/bigSpritesheet.png"),
                        16, 32, 42, 0));
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/pipes.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/pipes.png"),
                        32, 32, 4, 0));
        Assets.addSpritesheet(ASSETS + "/images/spritesheets/items.png", () ->
                new Spritesheet(Assets.getTexture(ASSETS + "/images/spritesheets/items.png"),
                        16, 16, 43, 0));
        // add sounds
        Assets.addSound(ASSETS + "/sounds/main-theme-overworld.ogg", true);
        Assets.addSound(ASSETS + "/sounds/flagpole.ogg", false);
        Assets.addSound(ASSETS + "/sounds/break_block.ogg", false);
        Assets.addSound(ASSETS + "/sounds/bump.ogg", false);
        Assets.addSound(ASSETS + "/sounds/coin.ogg", false);
        Assets.addSound(ASSETS + "/sounds/gameover.ogg", false);
        Assets.addSound(ASSETS + "/sounds/jump-small.ogg", false);
        Assets.addSound(ASSETS + "/sounds/mario_die.ogg", false);
        Assets.addSound(ASSETS + "/sounds/pipe.ogg", false);
        Assets.addSound(ASSETS + "/sounds/powerup.ogg", false);
        Assets.addSound(ASSETS + "/sounds/powerup_appears.ogg", false);
        Assets.addSound(ASSETS + "/sounds/stage_clear.ogg", false);
        Assets.addSound(ASSETS + "/sounds/stomp.ogg", false);
        Assets.addSound(ASSETS + "/sounds/kick.ogg", false);
        Assets.addSound(ASSETS + "/sounds/invincible.ogg", false);
    }

    @Override
    public void imGui() {
        // do nothing
    }
}

package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.physics2d.components.CircleCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.components.AnimationState;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.GoombaAI;

public class GoombaGenerator implements Prefab {

    private static final String SPRITESHEET_PATH =
            "C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/spritesheet.png";

    public GoombaGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 16, 43, 0));
    }

    public GameObject generate(Sprite sprite) {
        Spritesheet sprites = Assets.getSpritesheet(SPRITESHEET_PATH);
        GameObject goomba = Scene.createGameObject(sprite, "Goomba", 0.25f, 0.25f);

        AnimationState walk = new AnimationState();
        walk.setTitle("Walk");
        float defaultFrameTime = 0.23f;
        walk.addFrame(sprites.getSprite(14), defaultFrameTime);
        walk.addFrame(sprites.getSprite(15), defaultFrameTime);
        walk.setLoops(true);

        AnimationState squashed = new AnimationState();
        squashed.setTitle("Squashed");
        squashed.addFrame(sprites.getSprite(16), 0.1f);
        squashed.setLoops(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(walk);
        stateMachine.addState(squashed);
        stateMachine.setDefaultState(walk.getTitle());
        stateMachine.addStateTrigger(walk.getTitle(), squashed.getTitle(), "squashMe");
        goomba.addComponent(stateMachine);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setMass(0.1f);
        rb.setFixedRotation(true);
        goomba.addComponent(rb);

        CircleCollider cc = new CircleCollider();
        cc.setRadius(0.12f);
        goomba.addComponent(cc);

        goomba.addComponent(new GoombaAI());

        return goomba;
    }
}

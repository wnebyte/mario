package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.mario.Context;
import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.physics2d.components.CircleCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.TurtleAI;

public class TurtleGenerator implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/turtle.png";

    public TurtleGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 24, 4, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        Spritesheet sprites = Assets.getSpritesheet(SPRITESHEET_PATH);
        GameObject turtle = Scene.createGameObject(sprite, "Turtle", 0.25f, 0.35f);

        AnimationState walk = new AnimationState();
        walk.setTitle("Walk");
        float defaultFrameTime = 0.23f;
        walk.addFrame(sprites.getSprite(0), defaultFrameTime);
        walk.addFrame(sprites.getSprite(1), defaultFrameTime);
        walk.setLoops(true);

        AnimationState squashed = new AnimationState();
        squashed.setTitle("TurtleShellSpin");
        squashed.addFrame(sprites.getSprite(2), 0.1f);
        squashed.setLoops(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(walk);
        stateMachine.addState(squashed);
        stateMachine.setDefaultState(walk.getTitle());
        stateMachine.addStateTrigger(walk.getTitle(), squashed.getTitle(), "squashMe");
        turtle.addComponent(stateMachine);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setMass(0.1f);
        rb.setFixedRotation(true);
        turtle.addComponent(rb);
        CircleCollider cc = new CircleCollider();
        cc.setRadius(0.12f);
        cc.setOffset(new Vector2f(0, -0.5f));
        turtle.addComponent(cc);
        turtle.addComponent(new TurtleAI());

        return turtle;
    }
}

package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.mario.Context;
import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.physics2d.components.Box2DCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.components.Ground;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.QuestionBlock;

public class QuestionBlockGenerator implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/items.png";

    public QuestionBlockGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 16, 43, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        Spritesheet items = Assets.getSpritesheet(SPRITESHEET_PATH);
        GameObject questionBlock = Scene.createGameObject(sprite, "Question_Block", 0.25f, 0.25f);

        AnimationState flicker = new AnimationState();
        flicker.setTitle("Flicker");
        float defaultFrameTime = 0.23f;
        flicker.addFrame(items.getSprite(0), 0.57f);
        flicker.addFrame(items.getSprite(1), defaultFrameTime);
        flicker.addFrame(items.getSprite(2), defaultFrameTime);
        flicker.setLoops(true);

        AnimationState inactive = new AnimationState();
        inactive.setTitle("Inactive");
        inactive.addFrame(items.getSprite(3), 0.1f);
        inactive.setLoops(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(flicker);
        stateMachine.addState(inactive);
        stateMachine.setDefaultState(flicker.getTitle());
        stateMachine.addStateTrigger(flicker.getTitle(), inactive.getTitle(), "setInactive");
        questionBlock.addComponent(stateMachine);
        questionBlock.addComponent(new QuestionBlock());

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        questionBlock.addComponent(rb);
        Box2DCollider boxCollider = new Box2DCollider();
        boxCollider.setHalfSize(new Vector2f(0.25f, 0.25f));
        questionBlock.addComponent(boxCollider);
        questionBlock.addComponent(new Ground());

        return questionBlock;
    }
}

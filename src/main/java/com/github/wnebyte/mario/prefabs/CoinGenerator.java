package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.components.StateMachine;
import com.github.wnebyte.sproink.physics2d.components.CircleCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.components.AnimationState;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;
import com.github.wnebyte.mario.components.Coin;

public class CoinGenerator implements Prefab {

    public CoinGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(Context.getAssetsDir() + "/images/spritesheets/items.png");
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 16, 43, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        GameObject coin = Scene.createGameObject(sprite, "Coin", 0.25f, 0.25f);
        Spritesheet items = Assets.getSpritesheet(Context.getAssetsDir() + "/images/spritesheets/items.png");

        AnimationState coinFlip = new AnimationState();
        coinFlip.setTitle("CoinFlip");
        float defaultFrameTime = 0.23f;
        coinFlip.addFrame(items.getSprite(7), 0.57f);
        coinFlip.addFrame(items.getSprite(8), defaultFrameTime);
        coinFlip.addFrame(items.getSprite(9), defaultFrameTime);
        coinFlip.setLoops(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(coinFlip);
        stateMachine.setDefaultState(coinFlip.getTitle());
        coin.addComponent(stateMachine);
        coin.addComponent(new Coin());

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.12f);
        coin.addComponent(circleCollider);
        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        coin.addComponent(rb);

        return coin;
    }
}

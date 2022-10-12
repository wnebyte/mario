package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.physics2d.components.CircleCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.Flower;
import com.github.wnebyte.mario.Context;

public class FlowerGenerator implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/items.png";

    public static final int SPRITE_INDEX = 20;

    public FlowerGenerator() {
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
        GameObject flower = Scene.createGameObject(sprite, "Flower", 0.25f, 0.25f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        flower.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.14f);
        flower.addComponent(circleCollider);
        flower.addComponent(new Flower());

        return flower;
    }
}

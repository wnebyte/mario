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
import com.github.wnebyte.mario.components.MushroomAI;
import com.github.wnebyte.mario.Context;

public class MushroomPrefab implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/items.png";

    public static final int SPRITE_INDEX = 10;

    public MushroomPrefab() {
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
        GameObject mushroom = Scene.createGameObject(sprite, "Mushroom", 0.25f, 0.25f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        mushroom.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.14f);
        mushroom.addComponent(circleCollider);
        mushroom.addComponent(new MushroomAI());

        return mushroom;
    }
}

package com.github.wnebyte.mario.prefabs;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.physics2d.components.Box2DCollider;
import com.github.wnebyte.sproink.physics2d.components.Ground;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class BlockGenerator implements Prefab {

    public static final String SPRITESHEET_PATH =
            Context.getAssetsDir() + "/images/spritesheets/decorationsAndBlocks.png";

    public static final int[] SPRITE_INDICES = null;

    public BlockGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 16, 81, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        GameObject go = Scene.createGameObject(sprite, "Block", 0.25f, 0.25f);
        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        go.addComponent(rb);
        Box2DCollider bc = new Box2DCollider();
        bc.setHalfSize(new Vector2f(0.25f, 0.25f));
        go.addComponent(bc);
        go.addComponent(new Ground());
        /*
        if (i == 12) {
            go.addComponent(new BreakableBlock())
        }
         */
        return go;
    }
}

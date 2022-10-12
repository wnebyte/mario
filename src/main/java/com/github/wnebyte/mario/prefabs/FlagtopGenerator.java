package com.github.wnebyte.mario.prefabs;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.components.Box2DCollider;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.Flagpole;

public class FlagtopGenerator implements Prefab {

    public FlagtopGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture =
                Assets.getTexture("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/items.png");
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 24, 4, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        Spritesheet items =
                Assets.getSpritesheet("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/items.png");
        GameObject flagtop = Scene.createGameObject(sprite, "Flag_Top", 0.25f, 0.25f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        flagtop.addComponent(rb);

        Box2DCollider bc = new Box2DCollider();
        bc.setHalfSize(new Vector2f(0.1f, 0.25f));
        bc.setOffset(new Vector2f(-0.075f, 0.0f));
        flagtop.addComponent(bc);
        flagtop.addComponent(new Flagpole(true));

        return flagtop;
    }
}

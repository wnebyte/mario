package com.github.wnebyte.mario.prefabs;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.physics2d.components.Box2DCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.Flagpole;

public class FlagpoleGenerator implements Prefab {

    public FlagpoleGenerator() {
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
        GameObject flagpole = Scene.createGameObject(sprite, "Flag_Pole", 0.25f, 0.25f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        flagpole.addComponent(rb);

        Box2DCollider bc = new Box2DCollider();
        bc.setHalfSize(new Vector2f(0.1f, 0.25f));
        bc.setOffset(new Vector2f(-0.075f, 0.0f));
        flagpole.addComponent(bc);
        flagpole.addComponent(new Flagpole(false));

        return flagpole;
    }
}

package com.github.wnebyte.mario.prefabs;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.physics2d.components.CircleCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.Fireball;

public class FireballGenerator {

    public FireballGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture =
                Assets.getTexture("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/items.png");
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 24, 4, 0));
    }

    public static GameObject generate(Vector2f pos) {
        Spritesheet items =
                Assets.getSpritesheet("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/items.png");
        GameObject fireball = Scene.createGameObject(items.getSprite(32), "Fireball", 0.18f, 0.18f);
        fireball.transform.position.set(pos);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        fireball.addComponent(rb);

        CircleCollider cc = new CircleCollider();
        cc.setRadius(0.08f);
        fireball.addComponent(cc);
        fireball.addComponent(new Fireball());

        return fireball;
    }
}

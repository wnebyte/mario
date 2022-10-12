package com.github.wnebyte.mario.prefabs;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;
import com.github.wnebyte.sproink.physics2d.components.Box2DCollider;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.components.Ground;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.Pipe;
import com.github.wnebyte.mario.enums.Direction;

public class PipeLeftGenerator implements Prefab {

    public PipeLeftGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture =
                Assets.getTexture("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/pipes.png");
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 24, 4, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        Spritesheet pipes =
                Assets.getSpritesheet("C:/Users/Ralle/dev/java/EngineTest/assets/images/spritesheets/pipes.png");
        GameObject pipe = Scene.createGameObject(sprite, "Pipe_Left", 0.5f, 0.5f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        pipe.addComponent(rb);

        Box2DCollider bc = new Box2DCollider();
        bc.setHalfSize(new Vector2f(0.5f, 0.5f));
        pipe.addComponent(bc);
        pipe.addComponent(new Pipe(Direction.LEFT));
        pipe.addComponent(new Ground());

        return pipe;
    }
}

package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.core.Sound;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class Flower extends Component {

    private transient RigidBody2D rb;

    @Override
    public void start() {
        rb = gameObject.getComponent(RigidBody2D.class);
        Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/powerup_appears.ogg");
        sound.play();
        rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject go, Contact contact, Vector2f contactNormal) {
        PlayerController pc = go.getComponent(PlayerController.class);
        if (pc != null) {
            pc.powerup();
            gameObject.destroy();
        }
    }
}

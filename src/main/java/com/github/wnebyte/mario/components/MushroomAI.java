package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.core.Sound;
import com.github.wnebyte.sproink.physics2d.components.Ground;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class MushroomAI extends Component {

    private transient boolean goingRight = true;

    private transient RigidBody2D rb;

    private transient Vector2f velocity = new Vector2f(1.0f, 0.0f);

    private transient float terminalVelocity = 0.8f;

    private transient boolean hitPlayer = false;

    @Override
    public void start() {
        rb = gameObject.getComponent(RigidBody2D.class);
        Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/powerup_appears.ogg");
        sound.play();
    }

    @Override
    public void update(float dt) {
        if (goingRight && Math.abs(rb.getVelocity().x) < terminalVelocity) {
            rb.addVelocity(velocity);
        } else if (!goingRight && Math.abs(rb.getVelocity().x) < terminalVelocity) {
            rb.addVelocity(new Vector2f(-velocity.x, velocity.y));
        }
    }

    @Override
    public void preSolve(GameObject go, Contact contact, Vector2f contactNormal) {
        PlayerController pc = go.getComponent(PlayerController.class);
        if (pc != null) {
            // mushroom collided with player
            contact.setEnabled(false);
            if (!hitPlayer) {
                pc.powerup();
                gameObject.destroy();
                hitPlayer = true;
            }
        } else if (go.getComponent(Ground.class) == null) {
            // mushroom collided with a non-ground object
            contact.setEnabled(false);
            return;
        }

        if (Math.abs(contactNormal.y) < 0.1f) {
            // more horizontal than vertical
            goingRight = (contactNormal.x < 0);
        }
    }
}

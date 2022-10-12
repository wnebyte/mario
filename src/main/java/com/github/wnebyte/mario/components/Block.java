package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.core.GameObject;

public abstract class Block extends Component {

    private transient boolean bopGoingUp = true;

    private transient boolean doBopAnimation = false;

    private transient boolean active = true;

    private transient Vector2f bopStartPos;

    private transient Vector2f bopTopPos;

    public float bopSpeed = 0.4f;

    @Override
    public void start() {
        bopStartPos = new Vector2f(gameObject.transform.position);
        bopTopPos = new Vector2f(bopStartPos).add(0.0f, 0.02f);
    }

    @Override
    public void update(float dt) {
        if (doBopAnimation) {
            if (bopGoingUp) {
                if (gameObject.transform.position.y < bopTopPos.y) {
                    gameObject.transform.position.y += bopSpeed * dt;
                } else {
                    bopGoingUp = false;
                }
            } else {
                if (gameObject.transform.position.y > bopStartPos.y) {
                    gameObject.transform.position.y -= bopSpeed * dt;
                } else {
                    gameObject.transform.position.y = bopStartPos.y;
                    bopGoingUp = true;
                    doBopAnimation = false;
                }
            }
        }
    }

    @Override
    public void beginCollision(GameObject go, Contact contact, Vector2f contactNormal) {
        PlayerController pc = go.getComponent(PlayerController.class);
        if (active && pc != null && contactNormal.y < -0.8f) {
            doBopAnimation = true;
            // Todo: move play sound to subclass
            /*
            Sound sound = Assets.getSound("/sounds/bump.ogg");
            if (sound != null) {
                sound.play();
            }
             */
            playerHit(pc);
        }
    }

    public void setInactive() {
        active = false;
    }

    abstract void playerHit(PlayerController playerController);
}

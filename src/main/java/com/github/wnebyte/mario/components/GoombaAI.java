package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.core.*;
import com.github.wnebyte.sproink.components.StateMachine;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;
import com.github.wnebyte.mario.util.Constants;

public class GoombaAI extends Component {

    private transient boolean goingRight = false;

    private transient RigidBody2D rb;

    private transient float walkSpeed = 0.6f;

    private transient Vector2f velocity = new Vector2f();

    private transient Vector2f acceleration = new Vector2f();

    private transient Vector2f terminalVelocity = new Vector2f();

    private transient boolean isDead = false;

    private transient float timeToKill = 0.5f;

    private transient StateMachine stateMachine;

    private transient boolean onGround = false;

    @Override
    public void start() {
        rb = gameObject.getComponent(RigidBody2D.class);
        stateMachine = gameObject.getComponent(StateMachine.class);
        acceleration.y = Window.getScene().getPhysics().getGravity().y * Constants.GRAVITY_COEFFICIENT;
    }

    @Override
    public void update(float dt) {
        Camera camera = Window.getScene().getCamera();
        if (gameObject.transform.position.x >
                camera.getPosition().x + camera.getProjectionSize().x * camera.getZoom()) {
            // if the camera is to the right of us
            return;
        }

        if (isDead) {
            timeToKill -= dt;
            if (timeToKill <= 0) {
                gameObject.destroy();
            }
            rb.setVelocity(new Vector2f());
            return;
        }

        if (goingRight) {
            velocity.x = walkSpeed;
        } else {
            velocity.x = -walkSpeed;
        }

        if (onGround()) {
            acceleration.y = 0;
            velocity.y = 0;
        } else {
            acceleration.y = Window.getScene().getPhysics().getGravity().y * Constants.GRAVITY_COEFFICIENT;
        }

        velocity.y += acceleration.y * dt;
        velocity.y = Math.max(Math.min(velocity.y, terminalVelocity.y), -terminalVelocity.y);
        rb.setVelocity(velocity);
    }

    @Override
    public void preSolve(GameObject go, Contact contact, Vector2f contactNormal) {
        if (isDead) {
            return;
        }

        PlayerController pc = go.getComponent(PlayerController.class);
        if (pc != null) {
            if (pc.isAlive() && !pc.isHurtInvincible() && contactNormal.y > 0.58f) { // player jumped on top of the goomba's head
               pc.enemyBounce();
               stomp();
            } else if (pc.isAlive() && !pc.isInvincible() && !pc.isHurtInvincible()) {
               pc.die();
               if (pc.isAlive()) {
                   contact.setEnabled(false);
               }
            } else if (pc.isAlive() && pc.isInvincible()) {
                contact.setEnabled(false);
            }
        } else if (Math.abs(contactNormal.y) < 0.1f) { // hit something on the side
            goingRight = (contactNormal.x < 0);
        }

        Fireball fireball = go.getComponent(Fireball.class);
        if (fireball != null) {
            stomp();
            fireball.expire();
        }
    }

    public void stomp() {
        stomp(true);
    }

    public void stomp(boolean playSound) {
        isDead = true;
        velocity.zero();
        rb.setVelocity(new Vector2f());
        rb.setAngularVelocity(0.0f);
        rb.setGravityScale(0.0f);
        stateMachine.trigger("squashMe");
        rb.setIsSensor();
        if (playSound) {
            Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/bump.ogg");
            sound.play();
        }
    }

    public boolean onGround() {
        float innerWidth = 0.25f * 0.7f;
        float yVal = -0.14f;
        onGround = Window.getScene().getPhysics().onGround(gameObject, innerWidth, yVal);
        return onGround;
    }
}

package com.github.wnebyte.mario.components;

import com.github.wnebyte.mario.Context;
import com.github.wnebyte.mario.util.Constants;
import com.github.wnebyte.sproink.core.*;
import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.components.StateMachine;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.util.Assets;

public class TurtleAI extends Component {

    private transient RigidBody2D rb;

    private transient float walkSpeed = 0.6f;

    private transient Vector2f velocity = new Vector2f();

    private transient Vector2f acceleration = new Vector2f();

    private transient Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);

    private transient boolean goingRight = false;

    private transient boolean onGround = false;

    private transient boolean isDead = false;

    private transient boolean isMoving = false;

    private transient StateMachine stateMachine;

    private float movingDebounce = 0.32f;

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        rb = gameObject.getComponent(RigidBody2D.class);
        acceleration.y = Window.getScene().getPhysics().getGravity().y * Constants.GRAVITY_COEFFICIENT;
    }

    @Override
    public void update(float dt) {
        movingDebounce -= dt;
        Camera camera = Window.getScene().getCamera();
        if (gameObject.transform.position.x >
                camera.getPosition().x + camera.getProjectionSize().x * camera.getZoom()) {
            return;
        }

        if (!isDead || isMoving) {
            if (goingRight) {
                gameObject.transform.scale.x = -0.25f;
                velocity.x = walkSpeed;
                acceleration.x = 0;
            } else {
                gameObject.transform.scale.x = 0.25f;
                velocity.x = -walkSpeed;
                acceleration.x = 0;
            }
            acceleration.x = 0;
        } else {
            velocity.x = 0;
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

        // turtle is to the left of the camera
        if (gameObject.transform.position.x < camera.getPosition().x - 0.5f
               // || gameObject.transform.position.y < 0.0f
        ) {
            gameObject.destroy();
        }
    }

    @Override
    public void preSolve(GameObject go, Contact contact, Vector2f contactNormal) {
        GoombaAI goomba = go.getComponent(GoombaAI.class);
        if (isDead && isMoving && goomba != null) {
            goomba.stomp();
            contact.setEnabled(false);
            Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/kick.ogg");
            sound.play();
        }

        PlayerController playerController = go.getComponent(PlayerController.class);
        if (playerController != null) {
            if (!isDead && !playerController.isDead() &&
                    !playerController.isHurtInvincible() &&
                    contactNormal.y > 0.58f) {
                playerController.enemyBounce();
                stomp();
                walkSpeed *= 3.0f;
            } else if (movingDebounce < 0 && !playerController.isDead() &&
                    !playerController.isHurtInvincible() &&
                    (isMoving || !isDead) && contactNormal.y < 0.58f) {
                playerController.die();
                if (!playerController.isDead()) {
                    contact.setEnabled(false);
                }
            } else if (!playerController.isDead() && !playerController.isHurtInvincible()) {
                if (isDead && contactNormal.y > 0.58f) {
                    playerController.enemyBounce();
                    isMoving = !isMoving;
                    goingRight = (contactNormal.x < 0);
                } else if (isDead && !isMoving) {
                    isMoving = true;
                    goingRight = (contactNormal.x < 0);
                    movingDebounce = 0.32f;
                }
            } else if (!playerController.isDead() && playerController.isHurtInvincible()) {
                contact.setEnabled(false);
            }
        } else if (Math.abs(contactNormal.y) < 0.1f && !go.isDead()) {
            goingRight = (contactNormal.x < 0);
            if (isMoving && isDead) {
                Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/bump.ogg");
                sound.play();
            }
        }

        if (go.getComponent(Fireball.class) != null) {
            if (!isDead) {
                walkSpeed *= 3.0f;
                stomp();
            } else {
                isMoving = !isMoving;
                goingRight = (contactNormal.x < 0);
            }
            go.getComponent(Fireball.class).expire();
            contact.setEnabled(false);
        }
    }

    public void stomp() {
        isDead = true;
        isMoving = false;
        velocity.zero();
        rb.setVelocity(velocity);
        rb.setAngularVelocity(0.0f);
        rb.setGravityScale(0.0f);
        stateMachine.trigger("squashMe");
        Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/bump.ogg");
        sound.play();
    }

    public boolean onGround() {
        float innerWidth = 0.25f * 0.7f;
        float yVal = -0.2f;
        onGround = Window.getScene().getPhysics().onGround(gameObject, innerWidth, yVal);
        return onGround;
    }
}

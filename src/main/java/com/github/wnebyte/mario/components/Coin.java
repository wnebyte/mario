package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import org.jbox2d.dynamics.contacts.Contact;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.core.Sound;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class Coin extends Component {

    private Vector2f topY;

    private float coinSpeed = 1.4f;

    private transient boolean playAnimation = false;

    private transient ScoringSystem scoringSystem;

    @Override
    public void start() {
        topY = new Vector2f(gameObject.transform.position.y).add(0.0f, 0.5f);
        GameObject go = Window.getScene().getGameObject(ScoringSystem.class);
        if (go != null) {
            scoringSystem = go.getComponent(ScoringSystem.class);
        }
    }

    @Override
    public void update(float dt) {
        if (playAnimation) {
            if (gameObject.transform.position.y < topY.y) {
                gameObject.transform.position.y += dt * coinSpeed;
                gameObject.transform.scale.x -= (0.5f * dt) % -1.0f;
            } else {
                if (scoringSystem != null) {
                    scoringSystem.incrementScore();
                }
                gameObject.destroy();
            }
        }
    }

    @Override
    public void preSolve(GameObject go, Contact contact, Vector2f contactNormal) {
        if (go.getComponent(PlayerController.class) != null) {
            Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/coin.ogg");
            sound.play();
            playAnimation = true;
            contact.setEnabled(false);
        }
    }
}

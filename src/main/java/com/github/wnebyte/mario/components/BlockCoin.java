package com.github.wnebyte.mario.components;

import org.joml.Vector2f;
import com.github.wnebyte.sproink.core.Sound;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class BlockCoin extends Component {

    private Vector2f topY;

    private float coinSpeed = 1.4f;

    @Override
    public void start() {
        topY = new Vector2f(gameObject.transform.position.y).add(0, 0.5f);
        Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/coin.ogg");
        sound.play();
    }

    @Override
    public void update(float dt) {
        if (gameObject.transform.position.y < topY.y) {
            gameObject.transform.position.y += dt * coinSpeed;
            gameObject.transform.scale.x -= (0.5f * dt) % -1.0f;
        } else {
            gameObject.destroy();
        }
    }
}

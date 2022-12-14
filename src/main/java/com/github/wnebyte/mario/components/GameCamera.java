package com.github.wnebyte.mario.components;

import org.joml.Vector4f;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.Camera;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;

public class GameCamera extends Component {

    private static final Vector4f SKY_COLOR
            = new Vector4f(92.0f / 255.0f, 148.0f / 255.0f, 252.0f / 255.0f, 1.0f);

    private static final Vector4f UNDERGROUND_COLOR
            = new Vector4f(0, 0, 0, 1);

    private transient Camera camera;

    private transient GameObject player;

    private transient float highestX = Float.MIN_VALUE;

    private transient float lowestX = Float.MAX_VALUE;

    private transient float underGroundYLevel = 0.0f;

    private transient float cameraBuffer = 1.5f;

    private transient float playerBuffer = 0.25f;

    public GameCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void start() {
        player = Window.getScene().getGameObject(PlayerController.class);
        camera.setClearColor(SKY_COLOR);
        underGroundYLevel = camera.getPosition().y - camera.getProjectionSize().y - cameraBuffer;
    }

    @Override
    public void update(float dt) {
        if (player == null) {
            return;
        }

        PlayerController pc = player.getComponent(PlayerController.class);
        if (player != null && !pc.hasWon()) {
            camera.getPosition().x = Math.max(player.transform.position.x - 2.5f, highestX);
            highestX = Math.max(highestX, camera.getPosition().x);

            if (player.transform.position.y < -playerBuffer) {
                camera.getPosition().y = underGroundYLevel;
                camera.setClearColor(UNDERGROUND_COLOR);
            } else if (player.transform.position.y >= 0) {
                camera.getPosition().y = 0.0f;
                camera.setClearColor(SKY_COLOR);
            }
        }
    }
}

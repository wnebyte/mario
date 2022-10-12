package com.github.wnebyte.mario.components;

import com.github.wnebyte.mario.prefabs.Prefabs;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.components.StateMachine;

public class QuestionBlock extends Block {

    public enum BlockType {
        COIN,
        POWERUP,
        INVINCIBILITY
    }

    public BlockType blockType = BlockType.COIN;

    @Override
    public void playerHit(PlayerController playerController) {
        switch (blockType) {
            case COIN:
                doCoin(playerController);
                break;
            case POWERUP:
                doPowerup(playerController);
                break;
            case INVINCIBILITY:
                doInvincibility(playerController);
                break;
        }

        StateMachine stateMachine = gameObject.getComponent(StateMachine.class);
        if (stateMachine != null) {
            stateMachine.trigger("setInactive");
            setInactive();
        }
    }

    private void doCoin(PlayerController playerController) {
        GameObject coin = Prefabs.generateBlockCoin();
        coin.transform.position.set(gameObject.transform.position);
        coin.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(coin);
    }

    private void doPowerup(PlayerController playerController) {
        if (playerController.isSmall()) {
            spawnMushroom();
        } else if (playerController.isBig()) {
            spawnFlower();
        }
    }

    private void spawnMushroom() {
        GameObject mushroom = Prefabs.generateMushroom();
        mushroom.transform.position.set(gameObject.transform.position);
        mushroom.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(mushroom);
    }

    private void spawnFlower() {
        GameObject flower = Prefabs.generateFlower();
        flower.transform.position.set(gameObject.transform.position);
        flower.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(flower);
    }

    private void doInvincibility(PlayerController playerController) {
        // Todo: impl
    }
}

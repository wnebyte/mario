package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.components.AnimationState;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;
import com.github.wnebyte.mario.components.BlockCoin;

public class BlockCoinGenerator implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/items.png";

    public static final int SPRITE_INDEX = 7;

    public BlockCoinGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture texture = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(texture.getPath(), () ->
                new Spritesheet(texture, 16, 16, 43, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        Spritesheet items = Assets.getSpritesheet(SPRITESHEET_PATH);
        GameObject coin = Scene.createGameObject(sprite, "Block_Coin", 0.25f, 0.25f);

        AnimationState coinFlip = new AnimationState();
        coinFlip.setTitle("CoinFlip");
        float defaultFrameTime = 0.23f;
        coinFlip.addFrame(items.getSprite(7), 0.57f);
        coinFlip.addFrame(items.getSprite(8), defaultFrameTime);
        coinFlip.addFrame(items.getSprite(9), defaultFrameTime);
        coinFlip.setLoops(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(coinFlip);
        stateMachine.setDefaultState(coinFlip.getTitle());
        coin.addComponent(stateMachine);
        coin.addComponent(new BlockCoin());

        return coin;
    }
}

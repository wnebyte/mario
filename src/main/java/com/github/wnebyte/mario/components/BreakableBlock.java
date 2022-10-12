package com.github.wnebyte.mario.components;

import com.github.wnebyte.sproink.core.Sound;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.Context;

public class BreakableBlock extends Block {

    @Override
    void playerHit(PlayerController playerController) {
        if (!playerController.isSmall()) {
            Sound sound = Assets.getSound(Context.getAssetsDir() + "/sounds/break_block.ogg");
            sound.play();
            gameObject.destroy();
        }
    }
}

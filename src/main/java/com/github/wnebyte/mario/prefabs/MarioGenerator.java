package com.github.wnebyte.mario.prefabs;

import com.github.wnebyte.mario.Context;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.components.*;
import com.github.wnebyte.sproink.renderer.Texture;
import com.github.wnebyte.sproink.physics2d.enums.BodyType;
import com.github.wnebyte.sproink.physics2d.components.RigidBody2D;
import com.github.wnebyte.sproink.physics2d.components.PillboxCollider;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.mario.components.PlayerController;

public class MarioGenerator implements Prefab {

    public static final String SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/spritesheet.png";

    public static final String BIG_SPRITESHEET_PATH = Context.getAssetsDir() + "/images/spritesheets/bigSpritesheet.png";

    public MarioGenerator() {
        loadResources();
    }

    private void loadResources() {
        Texture tex1 = Assets.getTexture(SPRITESHEET_PATH);
        Assets.addSpritesheet(tex1.getPath(), () ->
                new Spritesheet(tex1, 16, 16, 26, 0));
        Texture tex2 = Assets.getTexture(BIG_SPRITESHEET_PATH);
        Assets.addSpritesheet(tex2.getPath(), () ->
                new Spritesheet(tex2, 16, 32, 42, 0));
    }

    @Override
    public GameObject generate(Sprite sprite) {
        GameObject mario = Scene.createGameObject(sprite, "Mario", 0.25f, 0.25f);
        Spritesheet playerSprites = Assets.getSpritesheet(SPRITESHEET_PATH);
        Spritesheet bigPlayerSprites = Assets.getSpritesheet(BIG_SPRITESHEET_PATH);

        // Little mario animations
        AnimationState run = new AnimationState();
        run.setTitle("Run");
        float defaultFrameTime = 0.2f;
        run.addFrame(playerSprites.getSprite(0), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(3), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        run.setLoops(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.setTitle("Direction");
        switchDirection.addFrame(playerSprites.getSprite(4), 0.1f);
        switchDirection.setLoops(false);

        AnimationState idle = new AnimationState();
        idle.setTitle("Idle");
        idle.addFrame(playerSprites.getSprite(0), 0.1f);
        idle.setLoops(false);

        AnimationState jump = new AnimationState();
        jump.setTitle("Jump");
        jump.addFrame(playerSprites.getSprite(5), 0.1f);
        jump.setLoops(false);

        // Big mario animations
        AnimationState bigRun = new AnimationState();
        bigRun.setTitle("BigRun");
        bigRun.addFrame(bigPlayerSprites.getSprite(0), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(1), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(2), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(3), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(2), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(1), defaultFrameTime);
        bigRun.setLoops(true);

        AnimationState bigSwitchDirection = new AnimationState();
        bigSwitchDirection.setTitle("Big Switch Direction");
        bigSwitchDirection.addFrame(bigPlayerSprites.getSprite(4), 0.1f);
        bigSwitchDirection.setLoops(false);

        AnimationState bigIdle = new AnimationState();
        bigIdle.setTitle("BigIdle");
        bigIdle.addFrame(bigPlayerSprites.getSprite(0), 0.1f);
        bigIdle.setLoops(false);

        AnimationState bigJump = new AnimationState();
        bigJump.setTitle("BigJump");
        bigJump.addFrame(bigPlayerSprites.getSprite(5), 0.1f);
        bigJump.setLoops(false);

        // Fire mario animations
        int fireOffset = 21;
        AnimationState fireRun = new AnimationState();
        fireRun.setTitle("FireRun");
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset), defaultFrameTime);
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset + 1), defaultFrameTime);
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset + 2), defaultFrameTime);
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset + 3), defaultFrameTime);
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset + 2), defaultFrameTime);
        fireRun.addFrame(bigPlayerSprites.getSprite(fireOffset + 1), defaultFrameTime);
        fireRun.setLoops(true);

        AnimationState fireSwitchDirection = new AnimationState();
        fireSwitchDirection.setTitle("Fire Switch Direction");
        fireSwitchDirection.addFrame(bigPlayerSprites.getSprite(fireOffset + 4), 0.1f);
        fireSwitchDirection.setLoops(false);

        AnimationState fireIdle = new AnimationState();
        fireIdle.setTitle("FireIdle");
        fireIdle.addFrame(bigPlayerSprites.getSprite(fireOffset), 0.1f);
        fireIdle.setLoops(false);

        AnimationState fireJump = new AnimationState();
        fireJump.setTitle("FireJump");
        fireJump.addFrame(bigPlayerSprites.getSprite(fireOffset + 5), 0.1f);
        fireJump.setLoops(false);

        AnimationState die = new AnimationState();
        die.setTitle("Die");
        die.addFrame(playerSprites.getSprite(6), 0.1f);
        die.setLoops(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(run);
        stateMachine.addState(idle);
        stateMachine.addState(switchDirection);
        stateMachine.addState(jump);
        stateMachine.addState(die);

        stateMachine.addState(bigRun);
        stateMachine.addState(bigIdle);
        stateMachine.addState(bigSwitchDirection);
        stateMachine.addState(bigJump);

        stateMachine.addState(fireRun);
        stateMachine.addState(fireIdle);
        stateMachine.addState(fireSwitchDirection);
        stateMachine.addState(fireJump);

        stateMachine.setDefaultState(idle.getTitle());
        stateMachine.addStateTrigger(run.getTitle(), switchDirection.getTitle(), "switchDirection");
        stateMachine.addStateTrigger(run.getTitle(), idle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(run.getTitle(), jump.getTitle(), "jump");
        stateMachine.addStateTrigger(switchDirection.getTitle(), idle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(switchDirection.getTitle(), run.getTitle(), "startRunning");
        stateMachine.addStateTrigger(switchDirection.getTitle(), jump.getTitle(), "jump");
        stateMachine.addStateTrigger(idle.getTitle(), run.getTitle(), "startRunning");
        stateMachine.addStateTrigger(idle.getTitle(), jump.getTitle(), "jump");
        stateMachine.addStateTrigger(jump.getTitle(), idle.getTitle(), "stopJumping");

        stateMachine.addStateTrigger(bigRun.getTitle(), bigSwitchDirection.getTitle(), "switchDirection");
        stateMachine.addStateTrigger(bigRun.getTitle(), bigIdle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(bigRun.getTitle(), bigJump.getTitle(), "jump");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), bigIdle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), bigRun.getTitle(), "startRunning");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), bigJump.getTitle(), "jump");
        stateMachine.addStateTrigger(bigIdle.getTitle(), bigRun.getTitle(), "startRunning");
        stateMachine.addStateTrigger(bigIdle.getTitle(), bigJump.getTitle(), "jump");
        stateMachine.addStateTrigger(bigJump.getTitle(), bigIdle.getTitle(), "stopJumping");

        stateMachine.addStateTrigger(fireRun.getTitle(), fireSwitchDirection.getTitle(), "switchDirection");
        stateMachine.addStateTrigger(fireRun.getTitle(), fireIdle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(fireRun.getTitle(), fireJump.getTitle(), "jump");
        stateMachine.addStateTrigger(fireSwitchDirection.getTitle(), fireIdle.getTitle(), "stopRunning");
        stateMachine.addStateTrigger(fireSwitchDirection.getTitle(), fireRun.getTitle(), "startRunning");
        stateMachine.addStateTrigger(fireSwitchDirection.getTitle(), fireJump.getTitle(), "jump");
        stateMachine.addStateTrigger(fireIdle.getTitle(), fireRun.getTitle(), "startRunning");
        stateMachine.addStateTrigger(fireIdle.getTitle(), fireJump.getTitle(), "jump");
        stateMachine.addStateTrigger(fireJump.getTitle(), fireIdle.getTitle(), "stopJumping");

        stateMachine.addStateTrigger(run.getTitle(), bigRun.getTitle(), "powerup");
        stateMachine.addStateTrigger(idle.getTitle(), bigIdle.getTitle(), "powerup");
        stateMachine.addStateTrigger(switchDirection.getTitle(), bigSwitchDirection.getTitle(), "powerup");
        stateMachine.addStateTrigger(jump.getTitle(), bigJump.getTitle(), "powerup");
        stateMachine.addStateTrigger(bigRun.getTitle(), fireRun.getTitle(), "powerup");
        stateMachine.addStateTrigger(bigIdle.getTitle(), fireIdle.getTitle(), "powerup");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), fireSwitchDirection.getTitle(), "powerup");
        stateMachine.addStateTrigger(bigJump.getTitle(), fireJump.getTitle(), "powerup");

        stateMachine.addStateTrigger(bigRun.getTitle(), run.getTitle(), "damage");
        stateMachine.addStateTrigger(bigIdle.getTitle(), idle.getTitle(), "damage");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), switchDirection.getTitle(), "damage");
        stateMachine.addStateTrigger(bigJump.getTitle(), jump.getTitle(), "damage");
        stateMachine.addStateTrigger(fireRun.getTitle(), bigRun.getTitle(), "damage");
        stateMachine.addStateTrigger(fireIdle.getTitle(), bigIdle.getTitle(), "damage");
        stateMachine.addStateTrigger(fireSwitchDirection.getTitle(), bigSwitchDirection.getTitle(), "damage");
        stateMachine.addStateTrigger(fireJump.getTitle(), bigJump.getTitle(), "damage");

        stateMachine.addStateTrigger(run.getTitle(), die.getTitle(), "die");
        stateMachine.addStateTrigger(switchDirection.getTitle(), die.getTitle(), "die");
        stateMachine.addStateTrigger(idle.getTitle(), die.getTitle(), "die");
        stateMachine.addStateTrigger(jump.getTitle(), die.getTitle(), "die");
        stateMachine.addStateTrigger(bigRun.getTitle(), run.getTitle(), "die");
        stateMachine.addStateTrigger(bigSwitchDirection.getTitle(), switchDirection.getTitle(), "die");
        stateMachine.addStateTrigger(bigIdle.getTitle(), idle.getTitle(), "die");
        stateMachine.addStateTrigger(bigJump.getTitle(), jump.getTitle(), "die");
        stateMachine.addStateTrigger(fireRun.getTitle(), bigRun.getTitle(), "die");
        stateMachine.addStateTrigger(fireSwitchDirection.getTitle(), bigSwitchDirection.getTitle(), "die");
        stateMachine.addStateTrigger(fireIdle.getTitle(), bigIdle.getTitle(), "die");
        stateMachine.addStateTrigger(fireJump.getTitle(), bigJump.getTitle(), "die");
        mario.addComponent(stateMachine);

        PillboxCollider pbc = new PillboxCollider();
        pbc.setWidth(0.21f);
        pbc.setHeight(0.25f);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        rb.setMass(25.0f);

        mario.addComponent(pbc);
        mario.addComponent(rb);
        mario.addComponent(new PlayerController());
        mario.transform.zIndex = 10;

        return mario;
    }
}

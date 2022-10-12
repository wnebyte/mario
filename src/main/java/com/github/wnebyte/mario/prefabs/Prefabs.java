package com.github.wnebyte.mario.prefabs;

import java.util.Map;
import java.util.HashMap;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Prefab;
import com.github.wnebyte.sproink.core.SpriteGenerator;
import com.github.wnebyte.sproink.util.Assets;
import com.github.wnebyte.sproink.components.Sprite;
import com.github.wnebyte.sproink.components.Spritesheet;

public class Prefabs {

    private static final Map<Class<? extends Prefab>, Prefab> prefabs = new HashMap<>();

    public static <T extends Prefab> Prefab getPrefab(Class<T> cls) {
        if (prefabs.containsKey(cls)) {
            return prefabs.get(cls);
        } else {
            try {
                Prefab prefab = cls.newInstance();
                prefabs.put(prefab.getClass(), prefab);
                return prefab;
            } catch (Exception e) {
                e.printStackTrace();
                return new SpriteGenerator();
            }
        }
    }

    public static GameObject generateBlockCoin() {
        Prefab prefab = getPrefab(BlockCoinGenerator.class);
        Spritesheet spritesheet = Assets.getSpritesheet(BlockCoinGenerator.SPRITESHEET_PATH);
        Sprite sprite = spritesheet.getSprite(BlockCoinGenerator.SPRITE_INDEX);
        return prefab.generate(sprite);
    }

    public static GameObject generateMushroom() {
        Prefab prefab = getPrefab(MushroomPrefab.class);
        Spritesheet spritesheet = Assets.getSpritesheet(MushroomPrefab.SPRITESHEET_PATH);
        Sprite sprite = spritesheet.getSprite(MushroomPrefab.SPRITE_INDEX);
        return prefab.generate(sprite);
    }

    public static GameObject generateFlower() {
        Prefab prefab = getPrefab(FlowerGenerator.class);
        Spritesheet spritesheet = Assets.getSpritesheet(FlowerGenerator.SPRITESHEET_PATH);
        Sprite sprite = spritesheet.getSprite(FlowerGenerator.SPRITE_INDEX);
        return prefab.generate(sprite);
    }
}

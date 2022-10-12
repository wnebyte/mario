package com.github.wnebyte.mario;

import java.util.List;
import java.util.ArrayList;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.SceneInitializer;
import com.github.wnebyte.util.Pair;

public class ScenePlayer {

    private final List<Pair<String, SceneInitializer>> scenes;

    private int index;

    public ScenePlayer() {
        this.scenes = new ArrayList<>();
        this.index = 0;
    }

    public void addScene(String path, SceneInitializer init) {
        Pair<String, SceneInitializer> scene = new Pair<>(path, init);
        scenes.add(scene);
    }

    public boolean playNextScene() {
        if (index < scenes.size() - 1) {
            Pair<String, SceneInitializer> scene = scenes.get(++index);
            play(scene);
            return true;
        }
        return false;
    }

    public boolean playLastScene() {
        if (index >= 1) {
            Pair<String, SceneInitializer> scene = scenes.get(--index);
            play(scene);
            return true;
        }
        return false;
    }

    public boolean playScene() {
        if (index >= 0 && index <= scenes.size() - 1) {
            Pair<String, SceneInitializer> scene = scenes.get(index);
            play(scene);
            return true;
        }
        return false;
    }

    private void play(Pair<String, SceneInitializer> scene) {
        Window.setScene(scene.getFirst(), scene.getSecond());
        Window.setRuntimePlaying(true);
    }
}

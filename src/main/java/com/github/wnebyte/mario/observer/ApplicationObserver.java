package com.github.wnebyte.mario.observer;

import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.Scene;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.observer.Observer;
import com.github.wnebyte.sproink.observer.event.Event;
import com.github.wnebyte.sproink.observer.event.GameEngineStartPlayEvent;
import com.github.wnebyte.sproink.observer.event.GameEngineStopPlayEvent;
import com.github.wnebyte.mario.scenes.MarioLevelSceneInitializer;
import com.github.wnebyte.sproink.observer.event.WindowBeginLoopEvent;

public class ApplicationObserver implements Observer {

    @Override
    public void notify(GameObject gameObject, Event event) {
        if (event instanceof GameEngineStartPlayEvent) {
            handleGameEngineStartPlayEvent();
        } else if (event instanceof GameEngineStopPlayEvent) {
            handleGameEngineStopPlayEvent();
        } else if (event instanceof WindowBeginLoopEvent) {

        }
    }

    private void handleGameEngineStartPlayEvent() {
        Scene scene = Window.getScene();
        Window.setRuntimePlaying(true);
        Window.setScene(scene.getPath(), new MarioLevelSceneInitializer());
    }

    private void handleGameEngineStopPlayEvent() {
        Scene scene = Window.getScene();
        Window.setRuntimePlaying(false);
        Window.setScene(scene.getPath(), new MarioLevelSceneInitializer());
    }
}

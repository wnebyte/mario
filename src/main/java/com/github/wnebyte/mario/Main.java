package com.github.wnebyte.mario;

import java.util.List;
import com.github.wnebyte.sproink.Application;
import com.github.wnebyte.sproink.Configuration;
import com.github.wnebyte.sproink.observer.Observer;
import com.github.wnebyte.sproink.ui.FontConfig;
import com.github.wnebyte.sproink.ui.ImGuiWindow;
import com.github.wnebyte.sproink.ui.GameViewWindow;
import com.github.wnebyte.mario.scenes.MarioLevelSceneInitializer;
import com.github.wnebyte.mario.observer.ApplicationObserver;
import com.github.wnebyte.util.FsLogger;

public class Main extends Application {

    private static final String ASSETS = Context.getAssetsDir();

    public static void main(String[] args) {
        launch(new Main());
    }

    @Override
    public void configure(final Configuration conf) {
        conf.setTitle("Mario");
        conf.setAssetsDir(ASSETS);
        conf.setEnableDocking(true);
        conf.setIniFileName("imgui.ini");
        conf.setScene(ASSETS + "/scenes/world1-level1.json");
        conf.setSceneInitializer(new MarioLevelSceneInitializer());
        conf.setLogger(new FsLogger("logs"));
    }

    @Override
    public void addFonts(final List<FontConfig> fonts) {
        fonts.add(new FontConfig(ASSETS + "/fonts/segoeui.ttf", 18));
    }

    @Override
    public void addWindows(final List<ImGuiWindow> windows) {
        windows.add(new GameViewWindow());
    }

    @Override
    public void addObservers(final List<Observer> observers) {
        observers.add(new ApplicationObserver());
    }
}
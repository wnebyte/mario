package com.github.wnebyte.mario.components;

import java.util.Arrays;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.components.TextRenderer;

public class TimeSystem extends Component {

    private transient float time;

    private transient float maxTime;

    private transient float debounceTime = 1.0f;

    private transient float debounce = debounceTime;

    private transient TextRenderer tr;

    public TimeSystem(float maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public void start() {
        time = 0f;
        GameObject go = Window.getScene().getGameObject("Time_Value");
        if (go != null) {
            tr = go.getComponent(TextRenderer.class);
        }
    }

    @Override
    public void update(float dt) {
        time += dt;
        debounce -= dt;
        if (time >= maxTime) {
            // game over
        } else if (debounce <= 0) {
            String s = String.valueOf((int)time);
            if (s.length() < 3) {
                s = pad(3 - s.length()) + s;
            }
            tr.setText(s);
            debounce = debounceTime;
        }
    }

    public float getTime() {
        return time;
    }

    private String pad(int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, '0');
        return new String(arr);
    }
}

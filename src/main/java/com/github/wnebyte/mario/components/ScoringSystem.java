package com.github.wnebyte.mario.components;

import java.util.Arrays;
import com.github.wnebyte.sproink.core.Window;
import com.github.wnebyte.sproink.core.GameObject;
import com.github.wnebyte.sproink.core.Component;
import com.github.wnebyte.sproink.components.TextRenderer;

public class ScoringSystem extends Component {

    private final int COIN_VALUE = 5;

    private transient int score;

    private transient TextRenderer tr;

    @Override
    public void start() {
        this.score = 0;
        GameObject go = Window.getScene().getGameObject("Score_Value");
        if (go != null) {
            this.tr = go.getComponent(TextRenderer.class);
        }
    }

    public void incrementScore() {
        score += COIN_VALUE;
        updateText();
    }

    public int getScore() {
        return score;
    }

    private void updateText() {
        if (tr != null) {
            String s = String.valueOf(score);
            if (s.length() < 6) {
                s = pad(6 - s.length()) + s;
            }
            tr.setText(s);
        }
    }

    private String pad(int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, '0');
        return new String(arr);
    }
}

package cz.cvut.fit.miadp.mvcgame.interpreter;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Map;

public class SoundExpression implements IExpression{
    MediaPlayer mediaPlayer;
    String name;

    public SoundExpression(MediaPlayer mediaPlayer, String name){
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.setStartTime(Duration.ZERO);
        this.name = name;
    }

    @Override
    public IExpression interpret(Map<String, IExpression> context) {
        this.mediaPlayer.stop();
        this.mediaPlayer.play();
        return this;
    }

    @Override
    public String interpret() {
        return "sound:" +
                this.name;
    }
}

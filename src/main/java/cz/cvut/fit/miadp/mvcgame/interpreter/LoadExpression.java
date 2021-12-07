package cz.cvut.fit.miadp.mvcgame.interpreter;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Map;

public class LoadExpression implements IExpression {
    String pathVar;
    String nameVar;

    public LoadExpression(String pathVar, String nameVar){
        this.pathVar = pathVar;
        this.nameVar = nameVar;
    }

    @Override
    public IExpression interpret(Map<String, IExpression> context) {
        String path = context.get(pathVar).interpret();
        String name = context.get(nameVar).interpret();

        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return new SoundExpression(mediaPlayer, name);
    }

    @Override
    public String interpret() {
        return "load " +
                pathVar +
                " as " +
                nameVar;
    }
}

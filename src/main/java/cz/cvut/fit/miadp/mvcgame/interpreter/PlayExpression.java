package cz.cvut.fit.miadp.mvcgame.interpreter;

import java.util.Map;

public class PlayExpression implements IExpression{
    String soundName;

    public PlayExpression(String soundName){
        this.soundName = soundName;
    }

    @Override
    public IExpression interpret(Map<String, IExpression> context) {
        IExpression soundToPlay =  context.get(soundName);
        soundToPlay.interpret(context);
        return soundToPlay;
    }

    @Override
    public String interpret() {
        return "play " +
                this.soundName;
    }
}

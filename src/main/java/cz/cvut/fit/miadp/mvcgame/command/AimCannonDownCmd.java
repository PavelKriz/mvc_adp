package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class AimCannonDownCmd extends AbstractGameCommand{

    public AimCannonDownCmd( IGameModel subject ){
        this.subject = subject;
    }


    @Override
    protected void execute() {
        this.subject.aimCannonDown();
    }
}

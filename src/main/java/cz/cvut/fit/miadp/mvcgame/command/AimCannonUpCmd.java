package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class AimCannonUpCmd extends AbstractGameCommand{

    public AimCannonUpCmd( IGameModel subject ){
        this.subject = subject;
    }


    @Override
    protected void execute() {
        this.subject.aimCannonUp();
    }
}

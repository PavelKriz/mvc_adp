package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class CannonPowerUpCmd extends AbstractGameCommand{

    public CannonPowerUpCmd( IGameModel subject ){
        this.subject = subject;
    }

    @Override
    protected void execute() {
        this.subject.cannonPowerUp();
    }
}

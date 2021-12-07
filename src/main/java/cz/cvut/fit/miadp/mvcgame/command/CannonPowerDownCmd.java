package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class CannonPowerDownCmd extends AbstractGameCommand{

    public CannonPowerDownCmd( IGameModel subject ){
        this.subject = subject;
    }

    @Override
    protected void execute() {
        this.subject.cannonPowerDown();
    }
}

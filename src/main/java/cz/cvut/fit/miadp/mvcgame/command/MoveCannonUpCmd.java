package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class MoveCannonUpCmd extends AbstractGameCommand{

    public MoveCannonUpCmd( IGameModel subject ){
        this.subject = subject;
    }

    @Override
    protected void execute( ) {
        this.subject.moveCannonUp( );
    }
    
}

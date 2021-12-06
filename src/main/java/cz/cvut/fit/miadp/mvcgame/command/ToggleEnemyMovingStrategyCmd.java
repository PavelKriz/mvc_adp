package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class ToggleEnemyMovingStrategyCmd extends AbstractGameCommand{

    public ToggleEnemyMovingStrategyCmd(IGameModel subject ){
        this.subject = subject;
    }

    @Override
    protected void execute() {
        this.subject.toggleEnemyMovingStrategy();
    }
}

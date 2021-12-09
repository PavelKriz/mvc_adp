package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public class ToggleMovingStrategyCmd extends AbstractGameCommand {

    public ToggleMovingStrategyCmd(IGameModel subject) {
        this.subject = subject;
    }


    @Override
    protected void execute() {
        this.subject.toggleMovingStrategy();
    }
}

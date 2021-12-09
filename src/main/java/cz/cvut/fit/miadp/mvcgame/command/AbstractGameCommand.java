package cz.cvut.fit.miadp.mvcgame.command;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

public abstract class AbstractGameCommand {

    IGameModel subject;
    Object memento;

    protected abstract void execute();

    public void doExecute() {
        this.memento = this.subject.createMemento();
        this.execute();
    }

    public void unExecute() {
        this.subject.setMemento(this.memento);
    }


}

package cz.cvut.fit.miadp.mvcgame.memento;

import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

    private IGameModel model;
    private final List<Object> mementos = new ArrayList<Object>();

    private static class SingletonHolder {
        private static final Caretaker INSTANCE = new Caretaker();
    }

    public static Caretaker getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setModel(IGameModel model) {
        this.model = model;
    }

    public Object createMemento() {
        if (this.model != null) {
            Object memento = this.model.createMemento();
            this.mementos.add(memento);
            return memento;
        } else {
            return null;
        }
    }

    public void setMemento() {
        if (this.model != null) {
            if (this.mementos.size() > 0) {
                this.model.setMemento(this.mementos.get(this.mementos.size() - 1));
            }
        } else {

        }
    }


}

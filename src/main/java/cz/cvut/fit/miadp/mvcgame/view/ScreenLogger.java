package cz.cvut.fit.miadp.mvcgame.view;

import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.IEventSubscriber;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitable;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ScreenLogger implements IEventSubscriber, IVisitable {
    List<String> toLog;

    public ScreenLogger() {
        toLog = new ArrayList<String>();
    }

    protected void reset() {
        toLog.clear();
    }

    public List<String> getLog() {
        return toLog;
    }

    @Override
    public void invoice(String message) {
        if (message.equals("!clear")) {
            this.reset();
            return;
        }
        toLog.add(message);
    }

    @Override
    public void acceptVisitor(IVisitor visitor) {
        visitor.visitScreenLogger(this);
    }
}

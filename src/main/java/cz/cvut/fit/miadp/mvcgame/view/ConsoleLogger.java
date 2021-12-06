package cz.cvut.fit.miadp.mvcgame.view;

import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.IEventSubscriber;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitable;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ConsoleLogger implements IEventSubscriber, IVisitable {

    String data;
    int cnt = 0;
    int printCountLimiter = 240;

    public ConsoleLogger(){
        this.data = "";
    }

    protected void reset(){
        data = "";
    }

    public String getLog(){
        return data;
    }


    @Override
    public void invoice(String message) {
        if(message.equals("!clear")){
            this.reset();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(data);
        stringBuilder.append(" |:| ");
        stringBuilder.append(message);
        data = stringBuilder.toString();
    }

    public void log(){
        ++cnt;
        if(cnt < printCountLimiter){
            return;
        }
        cnt = 0;
        System.out.println(data);
    }

    @Override
    public void acceptVisitor(IVisitor visitor) {
        System.out.println(data);
    }
}

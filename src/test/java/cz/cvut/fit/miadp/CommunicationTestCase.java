package cz.cvut.fit.miadp;


import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.view.ConsoleLogger;
import cz.cvut.fit.miadp.mvcgame.view.ScreenLogger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CommunicationTestCase {

    @Test
    public void testCommunication(){
        EventBus eventBus = new EventBus();
        ConsoleLogger consoleLogger = new ConsoleLogger();
        ScreenLogger screenLogger = new ScreenLogger();
        List<String> refList = new ArrayList<>();

        eventBus.subscribe(EventBus.ETopic.SCREEN_LOGGING, screenLogger);
        eventBus.subscribe(EventBus.ETopic.SCREEN_LOGGING, consoleLogger);

        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "MESSAGE1");
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "MESSAGE2");
        refList.add("MESSAGE1");
        refList.add("MESSAGE2");


        Assert.assertEquals(consoleLogger.getLog(), " |:| MESSAGE1 |:| MESSAGE2");
        Assert.assertEquals(screenLogger.getLog(), refList);

        eventBus.unsubscribe(EventBus.ETopic.SCREEN_LOGGING, consoleLogger);
        eventBus.unsubscribe(EventBus.ETopic.SCREEN_LOGGING, screenLogger);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "MESSAGE3");

        Assert.assertEquals(consoleLogger.getLog(), " |:| MESSAGE1 |:| MESSAGE2");
        Assert.assertEquals(screenLogger.getLog(), refList);

        consoleLogger.invoice("!clear");
        screenLogger.invoice("!clear");

        refList.clear();
        Assert.assertEquals(consoleLogger.getLog(), "");
        Assert.assertEquals(screenLogger.getLog(), refList);

        eventBus.subscribe(EventBus.ETopic.SCREEN_LOGGING, consoleLogger);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "MESSAGE4");

        Assert.assertEquals(consoleLogger.getLog(), " |:| MESSAGE4");
        Assert.assertEquals(screenLogger.getLog(), refList);

        eventBus.subscribe(EventBus.ETopic.SCREEN_LOGGING, screenLogger);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "MESSAGE5");
        refList.add("MESSAGE5");

        eventBus.unsubscribe(EventBus.ETopic.SCREEN_LOGGING, screenLogger);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "!clear");

        Assert.assertEquals(consoleLogger.getLog(), "");
        Assert.assertEquals(screenLogger.getLog(), refList);

    }
}

package cz.cvut.fit.miadp.mvcgame.publisher_subscriber;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    public enum ETopic {
        SCREEN_LOGGING
    }

    private final List<IEventSubscriber> screenLoggingSubscribers;

    public EventBus() {
        this.screenLoggingSubscribers = new ArrayList<IEventSubscriber>();
    }

    public void subscribe(ETopic topic, IEventSubscriber eventSubscriber) {
        switch (topic) {
            case SCREEN_LOGGING:
                screenLoggingSubscribers.add(eventSubscriber);
                break;
        }
    }

    public void unsubscribe(ETopic topic, IEventSubscriber eventSubscriber) {
        switch (topic) {
            case SCREEN_LOGGING:
                screenLoggingSubscribers.remove(eventSubscriber);
                break;
        }
    }

    public void publish(ETopic topic, String text) {
        switch (topic) {
            case SCREEN_LOGGING:
                for (IEventSubscriber subscriber : screenLoggingSubscribers) {
                    subscriber.invoice(text);
                }
                break;
        }
    }
}


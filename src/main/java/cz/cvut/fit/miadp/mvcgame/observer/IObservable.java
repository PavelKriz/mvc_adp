package cz.cvut.fit.miadp.mvcgame.observer;

public interface IObservable {

    void registerObserver(IObserver obs);

    void unregisterObserver(IObserver obs);

    void notifyObservers();

}

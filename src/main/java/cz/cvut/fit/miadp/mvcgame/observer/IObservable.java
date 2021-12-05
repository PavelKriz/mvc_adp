package cz.cvut.fit.miadp.mvcgame.observer;

public interface IObservable {

    public void registerObserver( IObserver obs );
    public void unregisterObserver( IObserver obs );

    public void notifyObservers( );
    
}

package cz.cvut.fit.miadp.mvcgame.model;

import java.util.List;

import cz.cvut.fit.miadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.miadp.mvcgame.observer.IObservable;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.strategy.IMovingStrategy;

public interface IGameModel extends IObservable {
    public void moveCannonUp();
    public void moveCannonDown();
    public void cannonShoot();
    public void aimCannonUp();
    public void aimCannonDown();
    public void cannonPowerUp();
    public void cannonPowerDown();
    public void toggleMovingStrategy();
    public void toggleShootingMode();
    public void toggleEnemyMovingStrategy();

    public void update(EventBus eventBus);
    public List<GameObject> getGameObjects();
    public IMovingStrategy getMovingStrategy();
    public Object createMemento();
    public void setMemento(Object memento);
	public Position getCannonPosition();

    public void registerCommand( AbstractGameCommand cmd );
    public void undoLastCommand( );
}

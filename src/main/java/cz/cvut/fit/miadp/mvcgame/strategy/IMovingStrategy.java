package cz.cvut.fit.miadp.mvcgame.strategy;

import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;

public interface IMovingStrategy {

    public void updatePosition( AbsMissile missile );
    
}

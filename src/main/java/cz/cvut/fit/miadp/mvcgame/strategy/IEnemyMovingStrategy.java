package cz.cvut.fit.miadp.mvcgame.strategy;

import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;

public interface IEnemyMovingStrategy {

    void updatePosition(AbsEnemy enemy);
}

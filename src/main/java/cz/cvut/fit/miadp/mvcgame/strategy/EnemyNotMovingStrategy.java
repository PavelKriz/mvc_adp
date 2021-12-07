package cz.cvut.fit.miadp.mvcgame.strategy;

import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;

public class EnemyNotMovingStrategy implements IEnemyMovingStrategy {
    @Override
    public void updatePosition(AbsEnemy enemy) {}

    @Override
    public String toString() {
        return "Enemy not moving strategy";
    }
}

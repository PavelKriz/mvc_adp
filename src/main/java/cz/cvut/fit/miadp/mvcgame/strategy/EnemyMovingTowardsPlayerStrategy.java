package cz.cvut.fit.miadp.mvcgame.strategy;

import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.model.Vector;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;

public class EnemyMovingTowardsPlayerStrategy implements IEnemyMovingStrategy{

    @Override
    public void updatePosition(AbsEnemy enemy) {
        Vector v = new Vector( -MvcGameConfig.ENEMY_MOVING_SPEED, 0 );
        enemy.move( v );
    }

    @Override
    public String toString() {
        return "Enemy are moving towards player strategy";
    }
}

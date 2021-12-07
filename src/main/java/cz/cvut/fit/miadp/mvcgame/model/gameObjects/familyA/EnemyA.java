package cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA;

import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.Vector;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

public class EnemyA extends AbsEnemy {

    //deciding which image is going to use for d
    public enum EnemyType{
        ENEMY_1,
        ENEMY_2
    }
    EnemyType enemyType;

    @Override
    public int scoreGain() {
        switch (enemyType) {
            case ENEMY_1:
                return 1;
            case ENEMY_2:
                return 2;
        }
        return 0;
    }

    public EnemyA(IGameObjectsFactory goFact, EnemyType enemyType, Position initialPosition) {
        super(goFact, initialPosition);
        this.enemyType = enemyType;
    }

    @Override
    public void acceptVisitor(IVisitor visitor) {
        switch (enemyType) {
            case ENEMY_1:
                visitor.visitEnemy1(this);
                break;
            case ENEMY_2:
                visitor.visitEnemy2(this);
                break;
        }
    }

    @Override
    public void move(Vector v) {
        super.move(v);
    }
}

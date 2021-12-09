package cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA;

import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.miadp.mvcgame.strategy.IMovingStrategy;

public class MissileA extends AbsMissile {

    private final IMovingStrategy movingStrategy;

    public MissileA(Position initialPosition, double initAngle, int initVelocity, IMovingStrategy movingStrategy) {
        super(initialPosition, initAngle, initVelocity);
        this.position = initialPosition;
        this.movingStrategy = movingStrategy;
    }

    @Override
    public void move() {
        this.movingStrategy.updatePosition(this);
    }

}

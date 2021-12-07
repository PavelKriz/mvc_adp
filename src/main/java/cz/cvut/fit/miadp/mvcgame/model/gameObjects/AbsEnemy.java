package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.model.Position;

public abstract class AbsEnemy extends GameObject implements IHittable {

    IGameObjectsFactory goFact;
    AbsCollision collision;

    public abstract int scoreGain();

    @Override
    public void die() {
        collision = goFact.createCollision(this);
    }

    public AbsCollision getCollision(){
        return collision;
    }

    private final int hitBoxWidth = 30;
    private final int hitBoxHeight = 30;

    protected AbsEnemy(IGameObjectsFactory goFact, Position initialPosition) {
        this.collision = null;
        this.goFact = goFact;
        this.position = initialPosition;
    }

    @Override
    public boolean hit(AbsMissile missile) {
        Position pos = missile.getPosition();
        if(Math.abs(this.position.getY() - pos.getY()) < hitBoxHeight){
            if(Math.abs(this.position.getX() - pos.getX()) < hitBoxWidth){
                return true;
            }
        }
        return false;
    }
}

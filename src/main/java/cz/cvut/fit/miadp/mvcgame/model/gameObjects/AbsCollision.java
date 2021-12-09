package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.Vector;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

public abstract class AbsCollision extends LifetimeLimitedGameObject {

    private final int lifeTime = 1000 * 5; //in milliseconds

    protected AbsCollision(Position position) {
        super(position);
    }

    @Override
    public void move(Vector v) {
    }

    @Override
    public void acceptVisitor(IVisitor visitor) {
        visitor.visitCollision(this);
    }

    public boolean shouldExtinct() {
        return getAge() > lifeTime;
    }
}

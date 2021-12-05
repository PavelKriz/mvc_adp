package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

public abstract class AbsMissile extends LifetimeLimitedGameObject {

    private double initAngle;
    private int initVelocity;

    protected AbsMissile( Position initialPosition, double initAngle, int initVelocity ){
        super( initialPosition );
        this.initAngle = initAngle;
        this.initVelocity = initVelocity;
    }

    public abstract void move( );

    @Override
    public void acceptVisitor( IVisitor visitor ){
        visitor.visitMissile( this );
    }

    public int getInitVelocity( ){
        return this.initVelocity;
    }

    public double getInitAngle( ){
        return this.initAngle;
    }
    
}

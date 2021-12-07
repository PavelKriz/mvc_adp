package cz.cvut.fit.miadp.mvcgame.strategy;

import cz.cvut.fit.miadp.mvcgame.model.Vector;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;

public class SimpleMovingStrategy implements IMovingStrategy {

    @Override
    public void updatePosition( AbsMissile missile ) {
        int initVelocity = missile.getInitVelocity( );
        double initAngle = missile.getInitAngle( );
        long time = missile.getAge( ) / 100 ;

        int dX = ( int ) ( initVelocity * time * Math.cos( initAngle ) );
        int dY = ( int ) ( initVelocity * time * Math.sin( initAngle ) );

        missile.move( new Vector( dX, dY ) );
        
    }

    @Override
    public String toString() {
        return "Simple moving strategy";
    }
}

package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

import java.util.List;

import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.IEventPublisher;
import cz.cvut.fit.miadp.mvcgame.state.DoubleShootingMode;
import cz.cvut.fit.miadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.miadp.mvcgame.state.SingleShootingMode;
import cz.cvut.fit.miadp.mvcgame.visitor.IVisitor;

public abstract class AbsCannon extends GameObject implements IEventPublisher {

    protected IShootingMode shootingMode;
    protected static IShootingMode SINGLE_SHOOTING_MODE = new SingleShootingMode( );
    protected static IShootingMode DOUBLE_SHOOTING_MODE = new DoubleShootingMode( );

    public abstract void moveUp( );
    public abstract void moveDown( );
    public abstract void aimUp( );
    public abstract void aimDown( );
    public abstract void powerUp( );
    public abstract void powerDown( );

    public abstract List<AbsMissile> shoot( );
    public abstract void primitiveShoot( );

    @Override
    public void acceptVisitor( IVisitor visitor ){
        visitor.visitCannon( this );
    }

    public void toggleShootingMode( ){
        if ( this.shootingMode instanceof SingleShootingMode ){
            this.shootingMode = DOUBLE_SHOOTING_MODE;
        }
        else if ( this.shootingMode instanceof DoubleShootingMode ){
            this.shootingMode = SINGLE_SHOOTING_MODE;
        }
        else {
            //Another state
        }
    }

    public abstract Object getCannonState();
    public abstract void setCannonState(Object state);
}

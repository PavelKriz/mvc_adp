package cz.cvut.fit.miadp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import cz.cvut.fit.miadp.mvcgame.abstractFactory.GameObjectsFactoryA;
import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.model.GameModel;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;
import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.miadp.mvcgame.strategy.SimpleMovingStrategy;
import junit.framework.Assert;

public class EducativeTestCaseMock {

    @Test
    public void createMissile( ){
        IGameModel model = mock( GameModel.class );
        when( model.getCannonPosition( ) ).thenReturn( new Position( 500, 600 ) );
        when( model.getMovingStrategy( ) ).thenReturn( new SimpleMovingStrategy( ) );
        IGameObjectsFactory goFact = new GameObjectsFactoryA( model );
        AbsMissile missile = goFact.createMissile( 0 , 10000000 );
        Assert.assertEquals( missile.getPosition( ).getX( ), 500 );
        Assert.assertEquals( missile.getPosition( ).getY( ), 600 );
    }
    
}

package cz.cvut.fit.miadp;

import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import org.junit.Test;

import cz.cvut.fit.miadp.mvcgame.command.MoveCannonUpCmd;
import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.model.GameModel;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;
import junit.framework.Assert;

public class EducativeTestCase {

    @Test
    public void undoLastCommand( ){
        IGameModel model = new GameModel( false );
        int positionBeforeUndoX = model.getCannonPosition( ).getX( );
        int positionBeforeUndoY = model.getCannonPosition( ).getY( );
        model.registerCommand( new MoveCannonUpCmd( model ) );
        EventBus eventBus = new EventBus();
        model.update( eventBus );
        int positionAfterExecutionX = model.getCannonPosition( ).getX( );
        int positionAfterExecutionY = model.getCannonPosition( ).getY( );
        model.undoLastCommand( );
        int positionAfterUndoX = model.getCannonPosition( ).getX( );
        int positionAfterUndoY = model.getCannonPosition( ).getY( );

        Assert.assertEquals(positionBeforeUndoX, positionAfterExecutionX);
        Assert.assertEquals(positionAfterUndoX, positionAfterExecutionX);
        Assert.assertEquals(positionBeforeUndoY, positionAfterExecutionY + MvcGameConfig.MOVE_STEP );
        Assert.assertEquals(positionBeforeUndoX, positionAfterUndoX);
        Assert.assertEquals(positionBeforeUndoY, positionAfterUndoY);
        

    }
    
}

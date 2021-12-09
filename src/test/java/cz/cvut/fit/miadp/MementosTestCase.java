package cz.cvut.fit.miadp;

import cz.cvut.fit.miadp.mvcgame.command.MoveCannonDownCmd;
import cz.cvut.fit.miadp.mvcgame.command.MoveCannonUpCmd;
import cz.cvut.fit.miadp.mvcgame.model.GameModel;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class MementosTestCase {
    @Test
    public void testingMementos(){
        EventBus eventBus = new EventBus();
        IGameModel gameModel = new GameModel( false );
        Object m1 = gameModel.createMemento();
        gameModel.registerCommand(new MoveCannonDownCmd( gameModel ));
        gameModel.update( eventBus );
        Object m2 = gameModel.createMemento();

        Assert.assertThat(m1, not(equalTo(m2)));

        Object m3 = gameModel.createMemento();

        Assert.assertThat(m2, equalTo(m3));

        gameModel.registerCommand(new MoveCannonUpCmd( gameModel ));
        gameModel.update( eventBus );
        m2 = gameModel.createMemento();

        Assert.assertThat(m1, equalTo(m2));
        Assert.assertThat(m2, not(equalTo(m3)));
    }
}

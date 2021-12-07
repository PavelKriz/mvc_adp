package cz.cvut.fit.miadp.mvcgame;

import java.util.List;

import cz.cvut.fit.miadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.controller.GameController;
import cz.cvut.fit.miadp.mvcgame.memento.Caretaker;
import cz.cvut.fit.miadp.mvcgame.model.GameModel;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;
import cz.cvut.fit.miadp.mvcgame.proxy.GameModelProxy;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.view.ConsoleLogger;
import cz.cvut.fit.miadp.mvcgame.view.GameView;

public class MvcGame
{
    private IGameModel model;
    private GameView view;
    private GameController controller;
    private ConsoleLogger consoleLogger;
    private EventBus eventBus;

    public void init( ) {
        this.model = new GameModelProxy( new GameModel( ) );
        this.eventBus = new EventBus();
        this.consoleLogger = new ConsoleLogger();
        eventBus.subscribe(EventBus.ETopic.SCREEN_LOGGING, this.consoleLogger);
        this.view = new GameView( model, eventBus );
        controller = view.getController( );

        Caretaker.getInstance( ).setModel( model );
    }

    public void processPressedKeys(List<String> pressedKeysCodes)
    {
        this.controller.processPressedKeys(pressedKeysCodes);
    }

    public void update()
    {
        this.model.update( this.eventBus );
    }

    public void render(IGameGraphics gr)
    {
        this.view.setGraphicContext( gr );
        this.consoleLogger.log();
    }

    public String getWindowTitle()
    {
        return "The NI-ADP MvcGame";
    }

    public int getWindowWidth()
    {
        return MvcGameConfig.MAX_X;
    }

    public int getWindowHeight()
    {
        return  MvcGameConfig.MAX_Y;
    }
}
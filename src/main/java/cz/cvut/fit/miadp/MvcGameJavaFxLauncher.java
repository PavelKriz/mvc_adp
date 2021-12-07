package cz.cvut.fit.miadp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

import cz.cvut.fit.miadp.mvcgame.MvcGame;
import cz.cvut.fit.miadp.mvcgame.bridge.GameGraphics;
import cz.cvut.fit.miadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.miadp.mvcgame.bridge.JavaFxGraphics;

public class MvcGameJavaFxLauncher extends Application {

    private static final MvcGame theMvcGame = new MvcGame();

    @Override
    public void init() {
        theMvcGame.init();
    }

    @Override
    public void start(Stage stage) {
        String winTitle = theMvcGame.getWindowTitle();
        int winWidth = theMvcGame.getWindowWidth();
        int winHeight = theMvcGame.getWindowHeight();

        stage.setTitle( winTitle );

        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
            
        Canvas canvas = new Canvas( winWidth, winHeight );
        root.getChildren().add( canvas );
            
        GraphicsContext gc = canvas.getGraphicsContext2D();
        IGameGraphics gr = new GameGraphics( new JavaFxGraphics( gc ) );

        ArrayList<String> pressedKeysCodes = new ArrayList<String>();
 
        theScene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
 
                    // only add once... prevent duplicates
                    if ( !pressedKeysCodes.contains(code) )
                        pressedKeysCodes.add( code );
                }
            }
        );
 
        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    pressedKeysCodes.remove( code );
                }
            }
        );

        // the game-loop
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                theMvcGame.processPressedKeys(pressedKeysCodes);
                pressedKeysCodes.clear();
                theMvcGame.update();
                theMvcGame.render(gr);
            }
        }.start();
            
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
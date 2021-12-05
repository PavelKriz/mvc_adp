package cz.cvut.fit.miadp.mvcgame.visitor;

import cz.cvut.fit.miadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCollision;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.miadp.mvcgame.view.ScreenLogger;

import java.util.List;

public class GameRenderer implements IVisitor {

    private IGameGraphics gr;

    public void setGraphicContext( IGameGraphics gr ) {
        this.gr = gr;
    }

    @Override
    public void visitCannon(AbsCannon cannon) {
        this.gr.drawImage( "images/cannon.png", cannon.getPosition( ) );
    }

    @Override
    public void visitMissile(AbsMissile missile) {
        this.gr.drawImage( "images/missile.png", missile.getPosition( ) );
        
    }

    @Override
    public void visitEnemy1(AbsEnemy enemy) {
        this.gr.drawImage( "images/enemy1.png", enemy.getPosition());
    }

    @Override
    public void visitEnemy2(AbsEnemy enemy) {
        this.gr.drawImage( "images/enemy2.png", enemy.getPosition());
    }

    @Override
    public void visitCollision(AbsCollision collision) {
        this.gr.drawImage( "images/collision.png", collision.getPosition());
    }

    @Override
    public void visitScreenLogger(ScreenLogger screenLogger) {
        List<String> toLog = screenLogger.getLog();
        for(int i = 0, y = 20; i < toLog.size(); ++i, y += 15){
            this.gr.drawText(toLog.get(i), new Position(10,y));
        }
    }
}

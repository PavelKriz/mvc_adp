package cz.cvut.fit.miadp.mvcgame.visitor;

import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCollision;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.miadp.mvcgame.view.ScreenLogger;

public interface IVisitor {
    void visitCannon( AbsCannon cannon );
    void visitMissile( AbsMissile missile );
    void visitEnemy1(AbsEnemy enemy);
    void visitEnemy2(AbsEnemy enemy);
    void visitCollision(AbsCollision collision);
    void visitScreenLogger(ScreenLogger screenLogger);
    //TODO: enemies, collisions...
    
}

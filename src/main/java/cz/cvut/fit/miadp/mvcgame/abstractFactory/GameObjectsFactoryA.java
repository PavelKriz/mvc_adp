package cz.cvut.fit.miadp.mvcgame.abstractFactory;

import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;
import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCollision;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsEnemy;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA.CannonA;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA.CollisionA;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA.EnemyA;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA.MissileA;

import java.util.Random;

public class GameObjectsFactoryA implements IGameObjectsFactory {

    private Random random;
    private IGameModel model;

    public GameObjectsFactoryA( IGameModel model ){
        this.random = new Random();
        this.model = model;
    }


    @Override
    public CannonA createCannon( ) {
        return new CannonA( new Position( MvcGameConfig.CANNON_POS_X, MvcGameConfig.CANNON_POS_Y ), this );

    }

    @Override
    public EnemyA createEnemy( ){
        int randX = random.nextInt( MvcGameConfig.MAX_X - MvcGameConfig.MIN_X_ENEMY) + MvcGameConfig.MIN_X_ENEMY;
        int randY = random.nextInt( MvcGameConfig.MAX_Y);
        boolean enemy1 = random.nextBoolean();
        if(enemy1) {
            return new EnemyA(this, EnemyA.EnemyType.ENEMY_1, new Position(randX, randY));
        } else {
            return new EnemyA(this, EnemyA.EnemyType.ENEMY_2, new Position(randX, randY));
        }
    }

    @Override
    public AbsCollision createCollision(AbsEnemy enemy) {
        return new CollisionA(enemy.getPosition());
    }

    @Override
    public MissileA createMissile( double initAngle, int initVelocity ) {
        return new MissileA( 
            new Position( 
                model.getCannonPosition( ).getX(), 
                model.getCannonPosition().getY( ) 
            ), 
            initAngle,
            initVelocity, 
            this.model.getMovingStrategy( )
        );

    }
    
}

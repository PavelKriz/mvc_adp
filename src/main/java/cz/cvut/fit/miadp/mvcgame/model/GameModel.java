package cz.cvut.fit.miadp.mvcgame.model;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import cz.cvut.fit.miadp.mvcgame.abstractFactory.GameObjectsFactoryA;
import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.*;
import cz.cvut.fit.miadp.mvcgame.observer.IObserver;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.miadp.mvcgame.strategy.RealisticMovingStrategy;
import cz.cvut.fit.miadp.mvcgame.strategy.SimpleMovingStrategy;

public class GameModel implements IGameModel {

    private Random random;
    private AbsCannon cannon;
    private List<AbsMissile> missiles;
    private List<AbsEnemy> enemies;
    private List<AbsCollision> collisions;
    private List<IObserver> observers;
    private IGameObjectsFactory goFact;
    private int score;
    private IMovingStrategy movingStrategy;

    private Queue<AbstractGameCommand> unexecuteCmds = new LinkedBlockingQueue<AbstractGameCommand>( );
    private Stack<AbstractGameCommand> executedCmds = new Stack<AbstractGameCommand>();

    public GameModel( ){
        this.random = new Random();
        this.goFact = new GameObjectsFactoryA( this );
        this.cannon = this.goFact.createCannon( );
        this.missiles = new ArrayList<AbsMissile>();
        this.enemies = new ArrayList<AbsEnemy>();
        this.collisions = new ArrayList<AbsCollision>();
        this.observers = new ArrayList<IObserver>();
        this.score = 0;
        this.movingStrategy = new SimpleMovingStrategy( );
    }

    public Position getCannonPosition( ){
        return this.cannon.getPosition( );
    }

    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers( );
    }

    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers( );
    }

    public void aimCannonUp( ){
        this.cannon.aimUp( );
        this.notifyObservers( );
    }

    public void aimCannonDown( ){
        this.cannon.aimDown( );
        this.notifyObservers( );
    }

    public void cannonPowerUp( ){
        this.cannon.powerUp( );
        this.notifyObservers( );
    }

    public void cannonPowerDown( ){
        this.cannon.powerDown( );
        this.notifyObservers( );
    }

    public void update(EventBus eventBus) {
        this.executeCmds( );
        this.moveMissiles( );

        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "!clear");
        this.cannon.publish(eventBus);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "SCORE: >" + this.score + "<");

        if(enemies.size() < 10){
            int min = 0;
            int max = 120;
            int randInt = random.nextInt(max - min) + min;
            if(randInt < 5){
                enemies.add( goFact.createEnemy());
            }
        }
        List<AbsCollision> collisionsToRemove = new ArrayList<AbsCollision>();
        for(AbsCollision collision : collisions){
            if(collision.shouldExtinct()){
                collisionsToRemove.add(collision);
            }
        }
        collisions.removeAll(collisionsToRemove);
    }

    private void executeCmds( ) {
        while( !this.unexecuteCmds.isEmpty( ) ){
            AbstractGameCommand cmd = this.unexecuteCmds.poll( );
            cmd.doExecute( );
            this.executedCmds.push( cmd );
        }
    }

    private void moveMissiles( ){
        for( AbsMissile missile : this.missiles ){
            missile.move( );
        }
        this.destroyMissiles();
        this.notifyObservers( );
    }

    private void destroyMissiles( ){
        List<AbsMissile> toRemoveMissiles = new ArrayList<AbsMissile>();
        List<AbsEnemy> toRemoveEnemies = new ArrayList<AbsEnemy>();
        for( AbsMissile missile : this.missiles ){
            //geting out of bounds
            if ( missile.getPosition( ).getX( ) > MvcGameConfig.MAX_X ){
                toRemoveMissiles.add( missile );
            }
            //destroying enemy
            for(AbsEnemy enemy : enemies){
                if(enemy.hit(missile)){
                    toRemoveEnemies.add(enemy);
                    enemy.die();
                    this.score += enemy.scoreGain();
                    if(enemy.getCollision() != null) {
                        collisions.add(enemy.getCollision());
                    }
                    toRemoveMissiles.add(missile);
                }
            }
        }

        this.enemies.removeAll(toRemoveEnemies);
        this.missiles.removeAll( toRemoveMissiles );
    }

    @Override
    public void registerObserver(IObserver obs) {
        if( !this.observers.contains( obs ) ){
            this.observers.add( obs );
        }
    }

    @Override
    public void unregisterObserver(IObserver obs) {
        if( this.observers.contains( obs ) ){
            this.observers.remove( obs );
        }
        
    }

    @Override
    public void notifyObservers() {
        for( IObserver obs: this.observers){
            obs.update( );
        }
        
    }

    public void cannonShoot( ){
        this.missiles.addAll( cannon.shoot( ) );
        this.notifyObservers( );
    }

    public List<AbsMissile> getMissiles( ) {
        return this.missiles;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> go = new ArrayList<GameObject>();
        go.addAll( this.missiles );
        go.add( this.cannon );
        go.addAll( this.collisions);
        go.addAll( this.enemies );
        return go;
    }

    public IMovingStrategy getMovingStrategy( ){
        return this.movingStrategy;
    }

    public void toggleMovingStrategy( ){
        if ( this.movingStrategy instanceof SimpleMovingStrategy ){
            this.movingStrategy = new RealisticMovingStrategy( );
        }
        else if ( this.movingStrategy instanceof RealisticMovingStrategy ){
            this.movingStrategy = new SimpleMovingStrategy( );
        }
        else {
            //Another strategy
        }
    }

    public void toggleShootingMode( ){
        this.cannon.toggleShootingMode( );
    }

    private class Memento {
        private int score;
        Object cannonState;
        IMovingStrategy movingStrategy;
        //TODO GameModel state snapshot
    }

    public Object createMemento( ) {
        Memento m = new Memento( );
        m.score = this.score;
        m.cannonState = this.cannon.getCannonState();
        m.movingStrategy = this.movingStrategy;
        return m;
    }

    public void setMemento( Object memento ) {
        Memento m = (Memento)memento;
        this.score = m.score;
        this.cannon.setCannonState(m.cannonState);
        this.movingStrategy = m.movingStrategy;
    }

    @Override
    public void registerCommand(AbstractGameCommand cmd) {
        this.unexecuteCmds.add( cmd );
    }

    @Override
    public void undoLastCommand( ) {
        if( !this.executedCmds.isEmpty( ) ){
            AbstractGameCommand cmd = this.executedCmds.pop( );
            cmd.unExecute( );
            this.notifyObservers( );
        }
    }

}

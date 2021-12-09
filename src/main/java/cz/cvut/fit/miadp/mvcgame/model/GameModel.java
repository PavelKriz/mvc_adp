package cz.cvut.fit.miadp.mvcgame.model;


import cz.cvut.fit.miadp.mvcgame.abstractFactory.GameObjectsFactoryA;
import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.interpreter.IExpression;
import cz.cvut.fit.miadp.mvcgame.interpreter.LoadExpression;
import cz.cvut.fit.miadp.mvcgame.interpreter.PlayExpression;
import cz.cvut.fit.miadp.mvcgame.interpreter.StringValueExpression;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.*;
import cz.cvut.fit.miadp.mvcgame.observer.IObserver;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.strategy.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class GameModel implements IGameModel {

    private Random random;
    private AbsCannon cannon;
    private List<AbsMissile> missiles;
    private List<AbsEnemy> enemies;
    private List<AbsCollision> collisions;
    private List<IObserver> observers;
    private Map<String, IExpression> soundExpressions;
    private boolean playSounds;
    private IGameObjectsFactory goFact;
    private int score;
    private IMovingStrategy movingStrategy;
    private IEnemyMovingStrategy enemyMovingStrategy;

    private Queue<AbstractGameCommand> unexecutedCmds = new LinkedBlockingQueue<>();
    private Stack<AbstractGameCommand> executedCmds = new Stack<>();

    public GameModel(boolean playSounds) {
        this.random = new Random();
        this.goFact = new GameObjectsFactoryA(this);
        this.cannon = this.goFact.createCannon();
        this.missiles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.collisions = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.soundExpressions = new HashMap<>();
        this.score = 0;
        this.movingStrategy = new SimpleMovingStrategy();
        this.enemyMovingStrategy = new EnemyNotMovingStrategy();

        this.playSounds = playSounds;
        if (playSounds) {
            loadSounds();
        }
    }

    private void loadSounds() {
        IExpression loadSound = new LoadExpression("path", "name");

        String crashPath = "src/main/resources/sounds/Crash_louder.wav";
        Map<String, IExpression> crashLoadContext = new TreeMap<>();
        crashLoadContext.put("path", new StringValueExpression(crashPath));
        IExpression crashStr = new StringValueExpression("crash");
        crashLoadContext.put("name", crashStr);

        String shootPath = "src/main/resources/sounds/Shooting.wav";
        Map<String, IExpression> shootLoadContext = new TreeMap<>();
        shootLoadContext.put("path", new StringValueExpression(shootPath));
        IExpression shootStr = new StringValueExpression("shoot");
        shootLoadContext.put("name", shootStr);

        String damagePath = "src/main/resources/sounds/Damage.wav";
        Map<String, IExpression> damageLoadContext = new TreeMap<>();
        damageLoadContext.put("path", new StringValueExpression(damagePath));
        IExpression damageStr = new StringValueExpression("damage");
        damageLoadContext.put("name", damageStr);

        String spawnPath = "src/main/resources/sounds/Spawn.wav";
        Map<String, IExpression> spawnLoadContext = new TreeMap<>();
        spawnLoadContext.put("path", new StringValueExpression(spawnPath));
        IExpression spawnStr = new StringValueExpression("spawn");
        spawnLoadContext.put("name", spawnStr);

        this.soundExpressions.put(crashStr.interpret(), loadSound.interpret(crashLoadContext));
        this.soundExpressions.put(shootStr.interpret(), loadSound.interpret(shootLoadContext));
        this.soundExpressions.put(damageStr.interpret(), loadSound.interpret(damageLoadContext));
        this.soundExpressions.put(spawnStr.interpret(), loadSound.interpret(spawnLoadContext));
    }

    public Position getCannonPosition() {
        return this.cannon.getPosition();
    }

    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers();
    }

    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers();
    }

    public void aimCannonUp() {
        this.cannon.aimUp();
        this.notifyObservers();
    }

    public void aimCannonDown() {
        this.cannon.aimDown();
        this.notifyObservers();
    }

    public void cannonPowerUp() {
        this.cannon.powerUp();
        this.notifyObservers();
    }

    public void cannonPowerDown() {
        this.cannon.powerDown();
        this.notifyObservers();
    }

    private void updateLog(EventBus eventBus) {
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "!clear");
        this.cannon.publish(eventBus);
        String s1 = "Missile movement: " + this.movingStrategy.toString();
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, s1);

        String s2 = "Enemy movement: " + this.enemyMovingStrategy.toString();
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, s2);

        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, "SCORE: >" + this.score + "<");
    }

    private void handleEnemySpawn() {
        if (enemies.size() < 10) {
            int min = 0;
            int max = 120;
            int randInt = random.nextInt(max - min) + min;
            if (randInt < 5) {
                enemies.add(goFact.createEnemy());
                if (playSounds) {
                    IExpression player = new PlayExpression("spawn");
                    player.interpret(this.soundExpressions);
                }
                this.notifyObservers();
            }
        }
    }

    private void voidHandleEnemyAtBorder() {
        if (this.enemyMovingStrategy instanceof EnemyNotMovingStrategy) {
            return;
        }

        List<AbsEnemy> enemiesToRemove = new ArrayList<>();
        for (AbsEnemy enemy : this.enemies) {
            enemyMovingStrategy.updatePosition(enemy);
            if (enemy.getPosition().getX() < 0) {
                --this.score;
                if (playSounds) {
                    IExpression player = new PlayExpression("damage");
                    player.interpret(this.soundExpressions);
                }
                enemiesToRemove.add(enemy);
            }
        }
        this.enemies.removeAll(enemiesToRemove);
        if (enemiesToRemove.size() > 0) {
            this.notifyObservers();
        }

    }

    private void handleEnemies() {
        handleEnemySpawn();
        voidHandleEnemyAtBorder();
    }

    private void handleCollisions() {
        List<AbsCollision> collisionsToRemove = new ArrayList<>();
        for (AbsCollision collision : collisions) {
            if (collision.shouldExtinct()) {
                collisionsToRemove.add(collision);
                this.notifyObservers();
            }
        }
        collisions.removeAll(collisionsToRemove);
    }

    public void update(EventBus eventBus) {
        this.executeCmds();
        this.moveMissiles();
        this.updateLog(eventBus);
        this.handleEnemies();
        this.handleCollisions();
    }

    private void executeCmds() {
        while (!this.unexecutedCmds.isEmpty()) {
            AbstractGameCommand cmd = this.unexecutedCmds.poll();
            cmd.doExecute();
            this.executedCmds.push(cmd);
        }
    }

    private void moveMissiles() {
        for (AbsMissile missile : this.missiles) {
            missile.move();
        }
        this.destroyMissiles();
        this.notifyObservers();
    }

    private void handleHitEnemy(AbsEnemy enemy) {
        if (playSounds) {
            IExpression player = new PlayExpression("crash");
            player.interpret(this.soundExpressions);
        }

        enemy.die();
        this.score += enemy.scoreGain();
        if (enemy.getCollision() != null) {
            collisions.add(enemy.getCollision());
        }
    }

    private void destroyMissiles() {
        List<AbsMissile> toRemoveMissiles = new ArrayList<>();
        List<AbsEnemy> toRemoveEnemies = new ArrayList<>();
        for (AbsMissile missile : this.missiles) {
            // getting out of bounds
            if (missile.getPosition().getX() > MvcGameConfig.MAX_X) {
                toRemoveMissiles.add(missile);
            }
            //destroying enemy
            for (AbsEnemy enemy : enemies) {
                if (enemy.hit(missile)) {
                    if (this.playSounds) {
                        IExpression player = new PlayExpression("crash");
                        player.interpret(this.soundExpressions);
                    }

                    handleHitEnemy(enemy);
                    toRemoveEnemies.add(enemy);
                    toRemoveMissiles.add(missile);
                }
            }
        }

        this.enemies.removeAll(toRemoveEnemies);
        this.missiles.removeAll(toRemoveMissiles);
    }

    @Override
    public void registerObserver(IObserver obs) {
        if (!this.observers.contains(obs)) {
            this.observers.add(obs);
        }
    }

    @Override
    public void unregisterObserver(IObserver obs) {
        if (this.observers.contains(obs)) {
            this.observers.remove(obs);
        }
    }

    @Override
    public void notifyObservers() {
        for (IObserver obs : this.observers) {
            obs.update();
        }

    }

    public void cannonShoot() {
        this.missiles.addAll(cannon.shoot());

        if (playSounds) {
            IExpression player = new PlayExpression("shoot");
            player.interpret(this.soundExpressions);
        }

        this.notifyObservers();
    }

    public List<AbsMissile> getMissiles() {
        return this.missiles;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> go = new ArrayList<>();
        go.addAll(this.missiles);
        go.add(this.cannon);
        go.addAll(this.collisions);
        go.addAll(this.enemies);
        return go;
    }

    public IMovingStrategy getMovingStrategy() {
        return this.movingStrategy;
    }

    public void toggleMovingStrategy() {
        if (this.movingStrategy instanceof SimpleMovingStrategy) {
            this.movingStrategy = new RealisticMovingStrategy();
        } else if (this.movingStrategy instanceof RealisticMovingStrategy) {
            this.movingStrategy = new SimpleMovingStrategy();
        } else {
            //Another strategy
        }
    }

    public void toggleShootingMode() {
        this.cannon.toggleShootingMode();
    }

    @Override
    public void toggleEnemyMovingStrategy() {
        System.out.println("toggle difficulty");
        if (this.enemyMovingStrategy instanceof EnemyNotMovingStrategy) {
            this.enemyMovingStrategy = new EnemyMovingTowardsPlayerStrategy();
        } else if (enemyMovingStrategy instanceof EnemyMovingTowardsPlayerStrategy) {
            this.enemyMovingStrategy = new EnemyNotMovingStrategy();
        } else {
            //new strategies
        }
    }

    static private class Memento {
        private int score;
        Object cannonState;
        IMovingStrategy movingStrategy;
        IEnemyMovingStrategy enemyMovingStrategy;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Memento)) return false;
            Memento m2 = (Memento) obj;
            if (this.score != m2.score) return false;
            if (!this.cannonState.equals(m2.cannonState)) return false;
            if (!this.movingStrategy.getClass().equals(m2.movingStrategy.getClass())) return false;
            if (!this.enemyMovingStrategy.getClass().equals(m2.enemyMovingStrategy.getClass())) return false;
            return true;
        }
    }

    public Object createMemento() {
        Memento m = new Memento();
        m.score = this.score;
        m.cannonState = this.cannon.getCannonState();
        m.movingStrategy = this.movingStrategy;
        m.enemyMovingStrategy = this.enemyMovingStrategy;
        return m;
    }

    public void setMemento(Object memento) {
        Memento m = (Memento) memento;
        this.score = m.score;
        this.cannon.setCannonState(m.cannonState);
        this.movingStrategy = m.movingStrategy;
        this.enemyMovingStrategy = m.enemyMovingStrategy;
    }

    @Override
    public void registerCommand(AbstractGameCommand cmd) {
        this.unexecutedCmds.add(cmd);
    }

    @Override
    public void undoLastCommand() {
        if (!this.executedCmds.isEmpty()) {
            AbstractGameCommand cmd = this.executedCmds.pop();
            cmd.unExecute();
            this.notifyObservers();
        }
    }

}

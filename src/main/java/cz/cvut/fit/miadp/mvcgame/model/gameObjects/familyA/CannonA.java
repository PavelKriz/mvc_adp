package cz.cvut.fit.miadp.mvcgame.model.gameObjects.familyA;

import cz.cvut.fit.miadp.mvcgame.abstractFactory.IGameObjectsFactory;
import cz.cvut.fit.miadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.miadp.mvcgame.model.Position;
import cz.cvut.fit.miadp.mvcgame.model.Vector;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.miadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.miadp.mvcgame.publisher_subscriber.EventBus;
import cz.cvut.fit.miadp.mvcgame.state.IShootingMode;

import java.util.ArrayList;
import java.util.List;

public class CannonA extends AbsCannon {

    private final IGameObjectsFactory goFact;

    private double angle;
    private int power;
    private final List<AbsMissile> shootingBatch;

    public CannonA(Position initialPosition, IGameObjectsFactory goFact) {
        this.position = initialPosition;
        this.goFact = goFact;

        this.power = MvcGameConfig.INIT_POWER;
        this.angle = MvcGameConfig.INIT_ANGLE;

        this.shootingBatch = new ArrayList<>();
        this.shootingMode = AbsCannon.SINGLE_SHOOTING_MODE;
    }

    public void moveUp() {
        this.move(new Vector(0, -1 * MvcGameConfig.MOVE_STEP));
    }

    public void moveDown() {
        this.move(new Vector(0, MvcGameConfig.MOVE_STEP));

    }

    @Override
    public void publish(EventBus eventBus) {
        String shootingMode = "Shooting mode: " + this.shootingMode.getName();
        String power = "Cannon power: " + this.power;
        String angle = "Cannon angle: " + this.angle;
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, shootingMode);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, power);
        eventBus.publish(EventBus.ETopic.SCREEN_LOGGING, angle);
    }

    @Override
    public List<AbsMissile> shoot() {
        this.shootingBatch.clear();
        this.shootingMode.shoot(this);
        return this.shootingBatch;
    }

    @Override
    public void primitiveShoot() {
        this.shootingBatch.add(this.goFact.createMissile(this.angle, this.power));
    }

    @Override
    public void aimUp() {
        this.angle -= MvcGameConfig.ANGLE_STEP;
    }

    @Override
    public void aimDown() {
        this.angle += MvcGameConfig.ANGLE_STEP;
    }

    @Override
    public void powerUp() {
        this.power += MvcGameConfig.POWER_STEP;
    }

    @Override
    public void powerDown() {
        if (this.power - MvcGameConfig.POWER_STEP > 0) {
            this.power -= MvcGameConfig.POWER_STEP;
        }
    }

    static private class ExternalCannonAState {
        private int cannonX;
        private int cannonY;
        private int power;
        private double angle;
        private IShootingMode shootingMode;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ExternalCannonAState)) return false;
            ExternalCannonAState ecs = (ExternalCannonAState) obj;
            if (this.cannonX != ecs.cannonX) return false;
            if (this.cannonY != ecs.cannonY) return false;
            if (this.power != ecs.power) return false;
            if (this.angle != ecs.angle) return false;
            return this.shootingMode.getClass().equals(ecs.shootingMode.getClass());
        }
    }

    @Override
    public Object getCannonState() {
        ExternalCannonAState state = new ExternalCannonAState();
        state.cannonX = this.getPosition().getX();
        state.cannonY = this.getPosition().getY();
        state.power = this.power;
        state.angle = this.angle;
        state.shootingMode = this.shootingMode;

        return state;
    }

    @Override
    public void setCannonState(Object state) {
        ExternalCannonAState s = (ExternalCannonAState) state;
        this.getPosition().setX(s.cannonX);
        this.getPosition().setY(s.cannonY);
        this.power = s.power;
        this.angle = s.angle;
        this.shootingMode = s.shootingMode;
    }
}

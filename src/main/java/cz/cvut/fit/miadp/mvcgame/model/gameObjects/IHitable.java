package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

public interface IHitable {
    boolean hit(AbsMissile missile);
    void die();
}

package cz.cvut.fit.miadp.mvcgame.model.gameObjects;

public interface IHittable {
    boolean hit(AbsMissile missile);

    void die();
}

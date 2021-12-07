package cz.cvut.fit.miadp.mvcgame.model;

public class Vector {

    private int dX;
    private int dY;
    

    public Vector( int dX, int dY ) {
        this.dX = dX;
        this.dY = dY;
    }

    public int getDX( ){
        return this.dX;
    }

    public int getDY( ){
        return this.dY;
    }

    public void setDX( int x ){
        this.dX = x;
    }

    public void setDY( int y ){
        this.dY = y;
    }
}

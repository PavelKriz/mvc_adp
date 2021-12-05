package cz.cvut.fit.miadp.mvcgame.bridge;

import cz.cvut.fit.miadp.mvcgame.model.Position;

public interface IGameGraphics {
    public void drawImage( String path, Position pos );
    public void drawText( String text, Position pos );
    public void drawRectangle( Position leftTop, Position rightBottom );
    public void clear( );
}

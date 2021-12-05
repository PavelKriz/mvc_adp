package cz.cvut.fit.miadp.mvcgame.bridge;

import cz.cvut.fit.miadp.mvcgame.model.Position;

public class GameGraphics implements IGameGraphics {

    IGameGraphicsImplementor implementor;

    public GameGraphics( IGameGraphicsImplementor implementor ){
        this.implementor = implementor;
    }

    @Override
    public void drawImage(String path, Position pos) {
        this.implementor.drawImage( path, pos );
    }

    @Override
    public void drawText(String text, Position pos) {
        this.implementor.drawText( text, pos );
        
    }

    @Override
    public void drawRectangle(Position leftTop, Position rightBottom) {
        this.implementor.drawLine( leftTop, 
        new Position( 
            rightBottom.getX( ) , 
            leftTop.getY( ) 
        ) );

        this.implementor.drawLine(leftTop, 
        new Position( leftTop.getX( ), rightBottom.getY( )));

        this.implementor.drawLine(rightBottom, new Position( leftTop.getX( ), rightBottom.getY( )));

        this.implementor.drawLine(rightBottom, new Position( rightBottom.getX( ), leftTop.getY( )));
    }

    @Override
    public void clear() {
        this.implementor.clear( ); 
    }
    
}

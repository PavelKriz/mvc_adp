package cz.cvut.fit.miadp.mvcgame.bridge;

import cz.cvut.fit.miadp.mvcgame.model.Position;

public interface IGameGraphicsImplementor {

    void drawImage(String path, Position pos);

    void drawText(String text, Position pos);

    void drawLine(Position beginPosition, Position endPosition);

    void clear();
}

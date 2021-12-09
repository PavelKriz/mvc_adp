package cz.cvut.fit.miadp.mvcgame.interpreter;

import java.util.Map;

public interface IExpression {
    IExpression interpret(Map<String, IExpression> context);

    String interpret();
}

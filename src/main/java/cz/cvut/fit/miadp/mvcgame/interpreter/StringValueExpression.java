package cz.cvut.fit.miadp.mvcgame.interpreter;

import java.util.Map;

public class StringValueExpression implements IExpression {
    String value;

    public StringValueExpression(String value) {
        this.value = value;
    }

    @Override
    public IExpression interpret(Map<String, IExpression> context) {
        return this;
    }

    @Override
    public String interpret() {
        return value;
    }
}

package cz.cvut.fit.miadp;

import cz.cvut.fit.miadp.mvcgame.interpreter.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// add this as a parameter of the jdk: --add-opens java.base/java.lang=ALL-UNNAMED
public class ExpressionsTestCaseMock {
    //testing expressions without using the javafx - need to mock the load and sound expressions
    @Test
    public void testExpressions() {
        //this test is more about trying the mocking
        IExpression pathExpr = new StringValueExpression("path/path/ll.img");
        IExpression nameExpr = new StringValueExpression("name");
        IExpression loadExpression = mock(LoadExpression.class);
        when( loadExpression.interpret( ) ).thenReturn( "load path/path/ll.img as name");

        Map<String, IExpression> loadVals = new HashMap<String, IExpression>();
        loadVals.put("path", pathExpr);
        loadVals.put("name", nameExpr);

        IExpression soundExpr = mock(SoundExpression.class);

        when( loadExpression.interpret(loadVals ) ).thenReturn( soundExpr );

        String soundName = "testSound";
        IExpression playExpr = new PlayExpression(soundName);
        Map<String, IExpression> soundMap = new HashMap<String, IExpression>();
        soundMap.put(soundName, soundExpr);

        Assert.assertEquals(soundExpr, playExpr.interpret(soundMap));

    }
}

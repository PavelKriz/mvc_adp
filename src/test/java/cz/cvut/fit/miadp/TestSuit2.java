package cz.cvut.fit.miadp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class )

@Suite.SuiteClasses( {
        CommunicationTestCase.class,
        ExpressionsTestCaseMock.class,
        MementosTestCase.class
} )
// add this as a parameter of the jdk: --add-opens java.base/java.lang=ALL-UNNAMED
public class TestSuit2 {
}

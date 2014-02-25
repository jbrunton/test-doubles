package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class ServerEasymockTest {

    /*
        With easymock we create mocks using the createMock(..) method, and then we stub or mock it's methods.
        We can create three types of mock objects:
            default : throws assertion errors for any unexpected invocations
            nice : allows unexpected invocations by returning null, false, 0
            strict : similar to default with order checking enabled
     */


    @Test
    public void passes_shouldLogAction() {

        // use the stub to force the sut to use the logger (note the stubReturn)
        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        // set expectations on the logger
        Logger logger = createNiceMock(Logger.class);
        logger.log("did something"); // expectation with specific argument
        replay(logger);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger);
    }



    // Strict (Invocation)

    @Test
    public void fails_unexpected_invocation() {

        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        Logger logger = createMock(Logger.class); // the only difference from the previous test is that this is a default mock
        logger.log("did something");
        // logger.saveLogs();   // if this line wasn't commented the test would pass
        replay(logger);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger);
    }



    // Strict (Order)

    @Test
    public void fails_order_of_expectations() {
        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        Logger logger = createStrictMock(Logger.class);
        logger.saveLogs(); // fails because logger.saveLogs() is called after logger.log(..)
        logger.log("did something");
        replay(logger);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger);
    }



    // Partial mocking

    @Test
    public void passes_shouldLogActionToo() {

        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        // partial mock, using an implementation class
        Logger logger = createMockBuilder(LoggerImpl.class)
                .addMockedMethod("log", String.class)
                .createMock();
        logger.log("did something"); // set expectation
        replay(logger);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger);
    }


}

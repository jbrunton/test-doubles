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
        Logger loggerMock = createNiceMock(Logger.class);
        loggerMock.log("did something"); // expectation with specific argument
        replay(loggerMock);

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock);
    }


    // A strict mock for easymock is a mock, which expects the calls in a specific order.
    @Test
    public void fails_order_of_expectations() {
        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        Logger loggerMock = createStrictMock(Logger.class);
        loggerMock.saveLogs(); // fails because logger.saveLogs() is called after logger.log(..)
        loggerMock.log("did something");
        replay(loggerMock);

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock);
    }


    // Partial mocking

    @Test
    public void passes_shouldLogActionToo() {

        Preferences preferencesStub = createMock(Preferences.class);
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        // create the partial mock, using an implementation class
        Logger loggerMock = createMockBuilder(LoggerImpl.class)
                .addMockedMethod("log", String.class)
                .createMock();
        loggerMock.log("did something"); // set expectation
        replay(loggerMock);

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock);
    }


}

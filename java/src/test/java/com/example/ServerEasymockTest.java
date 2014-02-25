package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class ServerEasymockTest {

    /*
     With easymock we create mocks using the createMock(..) method, and then we stub or mock it's methods.
     */


    @Test
    public void passes_shouldLogAction() {

        Preferences preferencesStub = createMock(Preferences.class);
        // use the stub to force the sut to use the logger (note the stubReturn)
        expect(preferencesStub.isLoggingEnabled()).andStubReturn(true);
        replay(preferencesStub);

        Logger loggerMock = createNiceMock(Logger.class);
        // set expectations on the logger
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
        loggerMock.saveLogs(); // fails because the logger.saveLogs() is be called after logger.log(..)
        loggerMock.log("did something");
        replay(loggerMock);

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock);
    }



}

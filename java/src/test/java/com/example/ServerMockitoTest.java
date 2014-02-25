package com.example;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class ServerMockitoTest {

    @Test
    public void shouldLogAction() {
        // we stub the preferences dependency, to force our SUT down the path, on which the logger will be used
        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        // a mock which we'll use to verify some indirect output of the SUT.
        Logger logger = mock(Logger.class);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger).log("did something");
    }



    // Strict (Invocation)

    @Test
    public void fails_unexpected_invocation() {

        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        Logger logger = mock(Logger.class);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger).log("did something");
        verifyNoMoreInteractions(logger); // this is the only difference from the previous test
    }



    // Strict (Order)

    @Test
    public void fails_order_of_expectations() {
        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        Logger logger = mock(Logger.class);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        InOrder order = inOrder(logger);
        order.verify(logger).saveLogs(); // will fail, wrong order of calls
        order.verify(logger).log("did something");
    }



    // Partial mocking

    @Test
    public void passes_shouldLogActionToo() {

        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        // partial mock, using existing instance
        Logger logger = spy(new LoggerImpl());

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger).log("did something");
    }

}

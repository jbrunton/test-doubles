package com.example;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class ServerMockitoTest {

    @Test
    public void shouldLogAction() {
        // we stub the preferences dependency, to force our SUT down the path, on which the logger will be used
        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        // a mock which we'll use to verify some indirect output of the SUT. in this case that output is
        // the logging.
        Logger loggerMock = mock(Logger.class);

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock).log("did something");
    }

    // Partial mocking

    @Test
    public void passes_shouldLogActionToo() {

        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        // create the partial mock, using existing instance
        Logger loggerMock = spy(new LoggerImpl());

        Server sut = new Server(loggerMock, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(loggerMock).log("did something");
    }

}

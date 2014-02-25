package com.example;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServerMockitoTest {

    @Test
    public void shouldLogAction() {
        // we stub the preferences dependency, to force our SUT down the path, that the logger will be used
        Preferences preferencesStub = mock(Preferences.class);
        when(preferencesStub.isLoggingEnabled()).thenReturn(true);

        // a mock which we'll use to verify some indirect output of the SUT. in this case that output is
        // the logging.
        Logger logger = mock(Logger.class);

        Server sut = new Server(logger, preferencesStub);

        // act
        sut.doSomething();

        // assert
        verify(logger).log("did something");
    }
}

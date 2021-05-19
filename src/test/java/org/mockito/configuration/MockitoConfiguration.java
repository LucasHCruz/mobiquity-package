package org.mockito.configuration;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

/*
    This class is to define a default Answer to mocks that aren't stubbed.
    If a mock doesn't have a stub configuration and it was called, then return exception and not a null/empty value
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {
 
    public Answer<Object> getDefaultAnswer() {
        return new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new IllegalArgumentException(
                        String.format("Calling a mock with undefined arguments: %s %s",
                                invocation.getMethod(),
                                Arrays.toString(invocation.getArguments())));
            }
        };
    }
}
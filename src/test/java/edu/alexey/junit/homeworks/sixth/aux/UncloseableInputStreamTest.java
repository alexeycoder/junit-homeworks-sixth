package edu.alexey.junit.homeworks.sixth.aux;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class UncloseableInputStreamTest {

	@Test
	void closeTheProxyDoesNotCloseNestedResource() throws IOException {
		InputStream inputStreamMock = mock(InputStream.class);
		InputStream proxy = UncloseableInputStream.wrap(inputStreamMock);
		proxy.close();
		verify(inputStreamMock, never()).close();
	}

}

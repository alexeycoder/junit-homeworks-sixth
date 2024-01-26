package edu.alexey.junit.homeworks.sixth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.util.io.IOUtil;

class ConsoleLifecycleTest {

	private Scanner scannerMock;
	private PrintStream outDummy;
	private PrintStream errDummy;

	@BeforeEach
	void setUp() {
		scannerMock = mock(Scanner.class);
		outDummy = new PrintStream(OutputStream.nullOutputStream());
		errDummy = new PrintStream(OutputStream.nullOutputStream());
	}

	@AfterEach
	void tearDown() {
		scannerMock = null;
	}

	@Test
	void parseNumberThrowsExceptionIfNanOrNullInput() {
		assertThrows(NullPointerException.class, () -> ConsoleLifecycle.parseNumber(null));
		assertThrows(RuntimeException.class, () -> ConsoleLifecycle.parseNumber(""));
		assertThrows(RuntimeException.class, () -> ConsoleLifecycle.parseNumber("abc"));
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, 0.125, 10.259, 555, 123.123, -0.125, -10.259, -555, -123.123 })
	void parseNumberReturnsCorrectResult(double val) {
		assertThat(ConsoleLifecycle.parseNumber(Double.toString(val)).doubleValue()).isEqualTo(val);
	}

	@Test
	void parseNumbersListThrowsNpeIfNullInput() {
		assertThrows(NullPointerException.class, () -> ConsoleLifecycle.parseNumbersList(null));
	}

	@Test
	void parseNumbersListReturnCorrectList() {
		assertThat(ConsoleLifecycle.parseNumbersList("")).isEmpty();
		assertThat(ConsoleLifecycle.parseNumbersList("-5 10.5 8 16")).containsExactlyInAnyOrder(-5L, 10.5, 8L, 16L);
		assertThat(ConsoleLifecycle.parseNumbersList("9999.99")).containsExactly(9999.99);
	}

	@Test
	void representsNumbersThrowsNpeIfNullInput() {
		assertThrows(NullPointerException.class, () -> ConsoleLifecycle.representsNumbers(null));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"   -10.5   +15 -20 0.18  ",
			"123",
			"-456",
			".1 .2 -.3"
	})
	void representsNumbersReturnsTrueIfInputIsOk(String inp) {
		assertTrue(ConsoleLifecycle.representsNumbers(inp));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"     ",
			"-1 2 3.5 4..8 ",
			"5 8 10a",
			".1 0a.2 -.3",
			"   0x "
	})
	void representsNumbersReturnsFalseIfInputIsOk(String inp) {
		assertFalse(ConsoleLifecycle.representsNumbers(inp));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"n, true",
			"н, true",
			"т, true",
			"n, false",
			"'', false",
			"anything, false",
	})
	void askYesNoReturnsFalseIfCancellingIsImplied(String input, boolean isYesDefault) {

		when(scannerMock.nextLine()).thenReturn(input);
		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, errDummy);

		assertFalse(consoleLifecycle.askYesNo("", isYesDefault));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"y, true",
			"д, true",
			"l, true",
			"y, false",
			"'', true",
			"anything, true",
	})
	void askYesNoReturnsTrueIfContinueIsImplied(String input, boolean isYesDefault) {

		when(scannerMock.nextLine()).thenReturn(input);
		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, errDummy);

		assertTrue(consoleLifecycle.askYesNo("", isYesDefault));
	}

	@Test
	void askNumbersListReturnsNullForEmptyInput() {
		when(scannerMock.nextLine()).thenReturn("");
		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, errDummy);
		assertNull(consoleLifecycle.askNumbersList());
	}

	@ParameterizedTest
	@ValueSource(strings = { "1 2 3..5", "-1 -2a", "-..1 2", "abc", "$1.0" })
	void askNumbersListDoesNotAcceptWrongInput(String inp) {

		when(scannerMock.nextLine()).thenReturn(inp).thenReturn(""); // последний thenReturn("") чтобы прекратить ввод и вернуть управление из nextLine().

		var baos = new ByteArrayOutputStream();
		var err = new PrintStream(baos, true);
		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, err);
		assertNull(consoleLifecycle.askNumbersList());

		assertThat(baos.toString()).contains(ConsoleLifecycle.WRONG_INPUT);

		IOUtil.closeQuietly(err);
	}

	@ParameterizedTest
	@ValueSource(strings = { "1 2 3 5", "-1 -2", "-.1 2", "0", "1.0" })
	void askNumbersListDoesAcceptCorrectInput(String inp) {

		when(scannerMock.nextLine()).thenReturn(inp).thenReturn("");

		var baos = new ByteArrayOutputStream();
		var err = new PrintStream(baos, true);
		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, err);

		assertEquals(inp, consoleLifecycle.askNumbersList());

		assertThat(baos.toString()).doesNotContain(ConsoleLifecycle.WRONG_INPUT);

		IOUtil.closeQuietly(err);
	}

	@Test
	void compareAndGetResultReturnsCorrectResult() {

		ConsoleLifecycle consoleLifecycle = new ConsoleLifecycle(scannerMock, outDummy, errDummy);

		assertThat(consoleLifecycle.compareAndGetResult(List.of(-10), List.of(20, 100)))
				.isEqualTo(ConsoleLifecycle.RESULT_SECOND_GREATER);
		assertThat(consoleLifecycle.compareAndGetResult(List.of(20, 100), List.of(-10)))
				.isEqualTo(ConsoleLifecycle.RESULT_FIRST_GREATER);
		assertThat(consoleLifecycle.compareAndGetResult(List.of(-1, 2, -3), List.of(-3, 2, -1)))
				.isEqualTo(ConsoleLifecycle.RESULT_EQUALS);
		assertThat(consoleLifecycle.compareAndGetResult(List.of(10), List.of(0, 20)))
				.isEqualTo(ConsoleLifecycle.RESULT_EQUALS);
	}
}

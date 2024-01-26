package edu.alexey.junit.homeworks.sixth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;

class AppTest {

	private static final InputStream SYS_IN = System.in;
	private static final PrintStream SYS_ERR = System.err;
	private static final PrintStream SYS_OUT = System.out;

	private byte[] baisBuffer;
	private ByteArrayInputStream bais;
	private OutputStream baos;
	private PrintStream out;

	@BeforeEach
	void setUp() {
		baisBuffer = new byte[1024];
		bais = new ByteArrayInputStream(baisBuffer);
		System.setIn(bais);

		baos = new ByteArrayOutputStream();
		final boolean autoFlush = true;
		out = new PrintStream(baos, autoFlush);
		System.setOut(out);
	}

	@AfterEach
	void tearDown() {
		System.setIn(SYS_IN);
		System.setErr(SYS_ERR);
		System.setOut(SYS_OUT);

		IOUtil.closeQuietly(baos);
		IOUtil.closeQuietly(bais);
		out = null;
		baos = null;
		bais = null;
		baisBuffer = null;
	}

	@Test
	void appExitsSuccessfullyIfAskedToExit() {

		var inputBuffer = ByteBuffer.wrap(baisBuffer);

		// 1) нажатие Enter
		// 2) ввод ответа "n" на запрос продолжить
		// 3) нажатие Enter
		String inputs = System.lineSeparator() + "n" + System.lineSeparator();
		inputBuffer.put(inputs.getBytes());

		assertTimeout(Duration.ofSeconds(10), () -> App.main(null));
		assertThat(baos.toString()).contains(ConsoleLifecycle.EXIT_SUCCESSFULLY);

		inputBuffer.clear();
		bais.reset();

		// 1) ввод первого списка и нажатие Enter
		// 2) пустой ввод и Enter чтобы запросить преждевременное завершение
		// 3) ввод ответа "n" на запрос продолжить
		// 4) нажатие Enter
		String inputsCase2 = "1 2 3 4 5" + System.lineSeparator()
				+ System.lineSeparator()
				+ "n" + System.lineSeparator();
		inputBuffer.put(inputsCase2.getBytes());

		assertTimeout(Duration.ofSeconds(10), () -> App.main(null));
		assertThat(baos.toString()).contains(ConsoleLifecycle.EXIT_SUCCESSFULLY);
	}

	@Test
	void appExitsSuccessfullyIfCorrectInput() {

		var inputBuffer = ByteBuffer.wrap(baisBuffer);

		// Имитируем два цикла сравнения списков.
		// Ввод для первого:
		String firstInputs = "-10 -20.5 0 10 20.5" + System.lineSeparator()
				+ "  5 25 456 789.12  " + System.lineSeparator();
		//		System.setIn(new ByteArrayInputStream(firstInputs.getBytes()));
		inputBuffer.put(firstInputs.getBytes());

		// Ввод для второго:
		String secondInputs = "y" + System.lineSeparator()
				+ "  5 25 456 789.12  " + System.lineSeparator()
				+ "-10 -20.5 0 10 20.5" + System.lineSeparator();

		inputBuffer.put(secondInputs.getBytes());

		// Запрашиваем завершение:
		String thirdInputs = "n" + System.lineSeparator();
		inputBuffer.put(thirdInputs.getBytes());

		App.main(null);

		assertThat(baos.toString())
				.contains(ConsoleLifecycle.RESULT_SECOND_GREATER)
				.contains(ConsoleLifecycle.RESULT_FIRST_GREATER)
				.contains(ConsoleLifecycle.EXIT_SUCCESSFULLY);

	}

}

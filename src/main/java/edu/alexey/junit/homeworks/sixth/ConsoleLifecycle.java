package edu.alexey.junit.homeworks.sixth;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import edu.alexey.junit.homeworks.sixth.aux.UncloseableInputStream;

/**
 * Консольное приложение для ввода двух списков чисел и их сравнения по среднему
 * арифметическому.
 */
public class ConsoleLifecycle implements Runnable {

	static final String RESULT_EQUALS = "Средние значения равны";
	static final String RESULT_FIRST_GREATER = "Первый список имеет большее среднее значение";
	static final String RESULT_SECOND_GREATER = "Второй список имеет большее среднее значение";

	static final Locale NUMBERS_LOCALE = Locale.US;
	static final String INPUT_BEGINNING_SYMBOL = "❭";
	static final String PROMPT_NUMBERS = "Введите список числел,"
			+ " используя пробельный символ в качестве разделителя чисел,"
			+ " и точку в качестве десятичного разделителя"
			+ " (или пустой ввод чтобы завершить):";
	static final String WRONG_INPUT = "Некорректный ввод. Пожалуйста попробуйте ещё раз:";
	static final String PREVIEW_TEMPLATE = "Введён список: %s\n"
			+ "его среднее арифметическое: %.3f";
	static final String EXIT_SUCCESSFULLY = "Приложение успешно завершено.";

	private final Scanner inScanner;
	private final PrintStream out;
	private final PrintStream err;

	public ConsoleLifecycle() {
		this(null, null, null);
	}

	/**
	 * Консольное приложение для ввода двух списков чисел и их сравнения по среднему
	 * арифметическому.
	 *
	 * @param inScanner Стандартный поток ввода для приложения, обёрнутый в Scanner.
	 * @param out       Стандартный поток вывода для приложения.
	 * @param err       Стандартный поток ошибок для приложения.
	 */
	public ConsoleLifecycle(Scanner inScanner, PrintStream out, PrintStream err) {
		this.inScanner = inScanner == null
				? new Scanner(
						UncloseableInputStream.wrap(System.in),
						StandardCharsets.UTF_8)
				: inScanner;
		this.out = out == null ? System.out : out;
		this.err = err == null ? System.err : err;
	}

	@Override
	public void run() {

		do {
			out.println("\nСписок 1");
			String list1Str = askNumbersList();
			if (list1Str == null) {
				break;
			}
			List<Number> list1 = parseNumbersList(list1Str);
			previewNumbersList(list1);

			out.println("\nСписок 2");
			String list2Str = askNumbersList();
			if (list2Str == null) {
				break;
			}
			List<Number> list2 = parseNumbersList(list2Str);
			previewNumbersList(list2);

			String result = compareAndGetResult(list1, list2);
			out.println("\nОтвет:");
			out.println(result);

		} while (askYesNo("\nЖелаете повторить (Y/n)? ", true));

		out.println(EXIT_SUCCESSFULLY);
	}

	String compareAndGetResult(List<Number> list1, List<Number> list2) {
		int res = new ListByAverageComparator().compare(list1, list2);
		if (res < 0) {
			return RESULT_SECOND_GREATER;
		} else if (res > 0) {
			return RESULT_FIRST_GREATER;
		} else {
			return RESULT_EQUALS;
		}
	}

	void previewNumbersList(List<Number> list) {
		double avg = ListUtils.calcAverage(list).getAsDouble();
		out.printf(NUMBERS_LOCALE, PREVIEW_TEMPLATE, list, avg);
		out.println();
	}

	String askNumbersList() {
		out.println();
		out.print(PROMPT_NUMBERS);

		boolean wrong = false;
		while (true) {
			if (wrong) {
				wrong = false;
				err.println(WRONG_INPUT);
			}
			out.println();
			out.print(INPUT_BEGINNING_SYMBOL);
			String inp = inScanner.nextLine();
			if (inp.isEmpty()) {
				return null;
			}
			if (representsNumbers(inp)) {
				return inp;
			}
			wrong = true;
		}
	}

	boolean askYesNo(String prompt, boolean isYesDefault) {
		out.print(prompt);
		var answer = inScanner.nextLine();

		if (answer.isBlank()) {
			return isYesDefault;
		}

		if (answer.startsWith("y") || answer.startsWith("д")
				|| answer.startsWith("l")) {
			return true;
		} else if (answer.startsWith("n") || answer.startsWith("н")
				|| answer.startsWith("т")) {
			return false;
		} else {
			return isYesDefault;
		}
	}

	/**
	 * Проверяет, является строка представлением списка вещественных чисел.
	 *
	 * @param str Проверяемая строка.
	 * @return true, если строка содержит только вещественные числа, разделенные
	 *         пробельными символами и имеющие точку в качестве десятичного
	 *         разделителя.
	 * @throws NullPointerException если {@code str} является {@code null}.
	 */
	static boolean representsNumbers(String str) {
		Objects.requireNonNull(str);
		final String decimalRegex = "([+-]?(\\d*\\.?\\d+|\\d+\\.\\d*))";
		final Pattern pattern = Pattern.compile(
				"^\\s*" + decimalRegex + "(\\s+" + decimalRegex + "\\s*)*" + "$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	static List<Number> parseNumbersList(String str) {
		return Stream.of(str.strip().split("\\s+"))
				.filter(s -> !s.isEmpty())
				//.peek(s -> out.println("\"" + s + "\""))
				.map(ConsoleLifecycle::parseNumber)
				.toList();
	}

	static Number parseNumber(String str) {
		try {
			return NumberFormat.getInstance(NUMBERS_LOCALE).parse(str.strip());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}

package edu.alexey.junit.homeworks.sixth;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * Утилиты для работы со списками.
 */
public final class ListUtils {
	private ListUtils() {
	}

	/**
	 * Подсчёт среднего арифметического чисел списка.
	 *
	 * @param list Список чисел.
	 * @return Контейнер, содержащий значение среднего арифметического чисел списка,
	 *         либо пустой, если список пуст или содержит только null-элементы.
	 * @throws NullPointerException если {@code list} является {@code null}.
	 */
	public static OptionalDouble calcAverage(List<? extends Number> list) {
		Objects.requireNonNull(list);
		return list.stream()
				.filter(Objects::nonNull)
				.mapToDouble(Number::doubleValue).average();
	}
}

package edu.alexey.junit.homeworks.sixth;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Компаратор списков чисел по их среднему арифметическому. <br>
 * Не допускается сравнивать null, пустые списки и списки, содержащие
 * null-элементы.
 */
public class ListByAverageComparator implements Comparator<List<? extends Number>> {

	static final String NULL_LIST_MSG = "Значения null не допускаются";
	static final String EMPTY_LIST_MSG = "Пустые списки не допускаются";
	static final String NULL_ELEM_MSG = "Списки с null элементами не допускаются";

	/**
	 * Метод сравнения компаратора (см.
	 * {@link java.util.Comparator#compare(Object, Object)}) для пары числовых
	 * списков по их среднему арифметическому. списков чисел по их среднему
	 * арифметическому. <br>
	 * Не допускается сравнивать null, пустые списки и списки, содержащие
	 * null-элементы.
	 *
	 * @throws NullPointerException     если любой из аргументов {@code null}.
	 * @throws IllegalArgumentException если любой аз аргументов является пустым
	 *                                  списком или содержит null-элементы.
	 */
	@Override
	public int compare(List<? extends Number> list1, List<? extends Number> list2) {

		Objects.requireNonNull(list1, NULL_LIST_MSG + ": list1");
		Objects.requireNonNull(list2, NULL_LIST_MSG + ": list2");
		if (list1.isEmpty()) {
			throw new IllegalArgumentException(EMPTY_LIST_MSG + ": list1");
		}
		if (list2.isEmpty()) {
			throw new IllegalArgumentException(EMPTY_LIST_MSG + ": list2");
		}
		if (list1.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException(NULL_ELEM_MSG + ": list1");
		}
		if (list2.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException(NULL_ELEM_MSG + ": list2");
		}

		if (list1 == list2) {
			return 0;
		}

		double avg1 = ListUtils.calcAverage(list1).getAsDouble();
		double avg2 = ListUtils.calcAverage(list2).getAsDouble();

		return Double.compare(avg1, avg2);
	}
}

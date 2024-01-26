package edu.alexey.junit.homeworks.sixth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListByAverageComparatorTest {

	private ListByAverageComparator comparator;

	@BeforeEach
	void setUp() {
		// На случай, если реализация компаратора поменяется и из stateless
		// станет statefull -- создаём новый экземпляр для каждого теста
		comparator = new ListByAverageComparator();
	}

	@Test
	void compareThrowsNpeForNullInput() {

		List<Number> list = List.of(1, 2, 3);

		assertThatThrownBy(() -> comparator.compare(null, list))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_LIST_MSG);
		assertThatThrownBy(() -> comparator.compare(list, null))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_LIST_MSG);
		assertThatThrownBy(() -> comparator.compare(null, null))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_LIST_MSG);
	}

	@Test
	void compareThrowsExceptionForEmptyList() {

		List<Number> list = List.of(1, 2, 3);
		List<Number> empty = List.of();

		assertThatThrownBy(() -> comparator.compare(empty, list))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.EMPTY_LIST_MSG);
		assertThatThrownBy(() -> comparator.compare(list, empty))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.EMPTY_LIST_MSG);
		assertThatThrownBy(() -> comparator.compare(empty, empty))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.EMPTY_LIST_MSG);
	}

	@Test
	void compareThrowsExceptionForNullElementInList() {

		List<Number> list = List.of(1, 2, 3);
		List<Number> bad = Arrays.asList(1, 2, null, 3);

		assertThatThrownBy(() -> comparator.compare(bad, list))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_ELEM_MSG);
		assertThatThrownBy(() -> comparator.compare(list, bad))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_ELEM_MSG);
		assertThatThrownBy(() -> comparator.compare(bad, bad))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ListByAverageComparator.NULL_ELEM_MSG);
	}

	@Test
	void compareReturnsZeroForEqualByAvgLists() {

		List<Number> list = List.of(1, 2, 3);

		assertEquals(0, comparator.compare(list, list));
		assertEquals(0, comparator.compare(list, List.of(1, 2, 3)));
		assertEquals(0, comparator.compare(list, List.of(3, 2, 1)));
		assertEquals(0, comparator.compare(List.of(5), List.of(-10, -5, 0, 5, 10, 15, 20)));
		assertEquals(0, comparator.compare(List.of(-0.625, 0.625), List.of(-4, -3, -2, -1, 1, 2, 3, 4)));
	}

	@Test
	void compareReturnsCorrectResultForLegalInput() {

		assertThat(comparator.compare(List.of(10), List.of(10, 20, 30))).isLessThan(0);
		assertThat(comparator.compare(List.of(-10), List.of(-10, -20, -30))).isGreaterThan(0);

		assertThat(comparator.compare(List.of(10, 20, 30), List.of(10))).isGreaterThan(0);
		assertThat(comparator.compare(List.of(-10, -20, -30), List.of(-10))).isLessThan(0);
	}
}

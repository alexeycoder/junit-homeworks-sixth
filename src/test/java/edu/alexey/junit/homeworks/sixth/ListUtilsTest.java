package edu.alexey.junit.homeworks.sixth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ListUtilsTest {

	@Test
	void calcAverageThrowsNpeForNullInput() {
		assertThrows(NullPointerException.class, () -> ListUtils.calcAverage(null));
	}

	@Test
	void calcAverageReturnsEmptyOptForEmptyList() {
		assertTrue(ListUtils.calcAverage(List.of()).isEmpty());
	}

	@Test
	void calcAverageReturnsEmptyOptForListOfNullsOnly() {
		assertTrue(ListUtils.calcAverage(Arrays.asList(null, null)).isEmpty());
	}

	@Test
	void calcAverageReturnsCorrectResultForValidInput() {

		assertThat(ListUtils.calcAverage(List.of(5)).getAsDouble()).isEqualTo(5);
		assertThat(ListUtils.calcAverage(List.of(5, 5, 5)).getAsDouble()).isEqualTo(5);
		assertThat(ListUtils.calcAverage(List.of(-10, -5, 5, 5, 10)).getAsDouble()).isEqualTo(5 / 5);
		assertThat(ListUtils.calcAverage(Arrays.asList(-10, null, -5, 5, 5, 10, null)).getAsDouble()).isEqualTo(5 / 5);
		assertThat(ListUtils.calcAverage(List.of(-10, -20, -30)).getAsDouble()).isEqualTo((-10d - 20d - 30d) / 3);
	}
}

package com.example.hrm.util.dayoff;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DayOffUtilTest {

    @Test
    void given14Days_WhenCalculateAmount_ShouldReturn10() {
        // Act
        int amount = DayOffUtil.calculateAmount(LocalDate.now(), LocalDate.now().plusDays(14));

        // Assert
        assertThat(amount).isEqualTo(10);
    }

    @Test
    void givenMondayToFriday_WhenCalculateAmount_ShouldReturn5() {
        // Act
        int amount = DayOffUtil.calculateAmount(LocalDate.of(2022, 9, 26), LocalDate.of(2022, 9, 30));

        // Assert
        assertThat(amount).isEqualTo(5);
    }
}
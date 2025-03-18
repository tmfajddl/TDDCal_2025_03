package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Caltest {
    @Test
    @DisplayName("1 + 1 == 2")
    public void test1() {
        assertThat(Calc.run("1 + 1")).isEqualTo(2);
    }

    @Test
    @DisplayName("2 + 1 == 3")
    public void test2() {
        assertThat(Calc.run("2 + 1")).isEqualTo(3);
    }

    @Test
    @DisplayName("2 + 2 == 4")
    public void test3() {
        assertThat(Calc.run("2 + 2")).isEqualTo(4);
    }

    @Test
    @DisplayName("2 + 3 == 5")
    public void test4() {
        assertThat(Calc.run("2 + 3")).isEqualTo(5);
    }

    @Test
    @DisplayName("1000 + 200 == 1200")
    public void test5() {
        assertThat(Calc.run("1000 + 200")).isEqualTo(1200);
    }
}

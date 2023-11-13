package christmas.domain.model;

import christmas.constant.Menu;
import christmas.constant.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderInfoTest {

    @DisplayName("총메뉴 개수가 20을 넘으면 예외가 발생한다.")
    @Test
    void createOrderInfoByOver20CountOfMenu() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 8);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatThrownBy(() -> new OrderInfo(1, sample))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
    }

    @DisplayName("총메뉴 개수가 20을 넘지 않으면 예외가 발생하지 않는다.")
    @Test
    void createOrderInfoByBelow20CountOfMenu() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatCode(() -> new OrderInfo(1, sample))
                .doesNotThrowAnyException();
    }

    @DisplayName("1~31 에 속하지 않는 날짜를 입력하면 예외가 발생한다.")
    @Test
    void createOrderInfoByDayNotInRange() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatThrownBy(() -> new OrderInfo(32, sample))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("1~31 에 속하는 날짜를 입력하면 예외가 발생하지 않는다.")
    @Test
    void createOrderInfoByDayInRange() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatCode(() -> new OrderInfo(31, sample))
                .doesNotThrowAnyException();
    }
}
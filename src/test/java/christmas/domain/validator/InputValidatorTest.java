package christmas.domain.validator;

import christmas.constant.Menu;
import christmas.constant.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @DisplayName("1 ~ 31 에 해당하는 값이 들어오면 숫자로 변환해서 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "1,1",
            "3,3",
            "31,31"
    })
    void validateInputDayByCorrectValue(String input, int expected) {
        assertThat(InputValidator.validateInputDay(input))
                .isEqualTo(expected);
    }

    @DisplayName("숫자이면서 1 ~ 31 에 해당하지 않는 값을 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"32"})
    void validateInputDayByInvalidValue(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("공백만 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void validateInputDayByWhiteSpace(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_NOT_EXISTENT_VALUE);
    }

    @DisplayName("숫자가 아닌 값을 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㅁㄴㅇ", "asd", "/../", "-1", "0"})
    void validateInputDayByNotNumericValue(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("빈 값을 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void validateInputOrderValueByWhiteSpace(String whiteSpace) {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue(whiteSpace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_NOT_EXISTENT_VALUE);
    }

    @DisplayName("예시와 일치하지 않는 주문형식을 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"해산물파스타-3, 레드와인/2, 초코케이크-2", "해산물파스타/3, 레드와인-2, 초코케이크-2", "해산물파스타-3, 레드와인-2, 초코케이크/2"})
    void validateInputOrderValueByOutOfShape(String outOfShape) {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue(outOfShape))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

    @DisplayName("메뉴판에 없는 메뉴를 입력하면 예외가 발생한다.")
    @Test
    void validateInputOrderValueByNonExistentMenu() {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue("해물-1, 파스타-1, 라따뚜이-1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

    @DisplayName("한 번의 주문에 메뉴를 중복 입력하면 예외가 발생한다.")
    @Test
    void validateInputOrderValueByDuplicateMenu() {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue("해산물파스타-1, 레드와인-2, 해산물파스타-2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

    @DisplayName("메뉴의 총수량이 20을 넘으면 예외가 발생한다.")
    @Test
    void validateInputOrderValueByOver20CountOfMenu() {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue("해산물파스타-17, 레드와인-2, 초코케이크-2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
    }

    @DisplayName("음료만 주문하면 예외가 발생한다.")
    @Test
    void validateInputOrderValueByOnlyBeverageOrdered() {
        assertThatThrownBy(() -> InputValidator.validateInputOrderValue("레드와인-1, 샴페인-2, 제로콜라-2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_ORDER_ONLY_BEVERAGE);
    }

    @DisplayName("올바른 형식으로 메뉴를 입력하면 주문 정보 객체를 반환한다.")
    @Test
    void validateInputOrderValueByExistingMenu() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.SEAFOOD_PASTA, 2);
        sample.put(Menu.RED_WINE, 1);
        sample.put(Menu.CHOCOLATE_CAKE, 1);

        // when
        // then
        assertThat(InputValidator.validateInputOrderValue("해산물파스타-2, 레드와인-1, 초코케이크-1"))
                .isEqualTo(sample);
    }

}
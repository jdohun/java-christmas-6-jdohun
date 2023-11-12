package christmas.domain.validator;

import christmas.constant.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @DisplayName("1 ~ 31 에 해당하는 값이 들어오면 숫자로 변환되서 반환한다.")
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

    @DisplayName("숫자이면서 1 ~ 31 에 해당하지 않는 값이 들어오면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"32"})
    void validateInputDayByInvalidValue(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("공백만 입력되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void validateInputDayByWhiteSpace(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_NOT_EXISTENT_VALUE);
    }

    @DisplayName("숫자가 아닌 값이 입력 되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㅁㄴㅇ", "asd", "/../", "-1", "0"})
    void validateInputDayByNotNumericValue(String input) {
        assertThatThrownBy(() -> InputValidator.validateInputDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @Test
    void validateInputOrderList() {
    }
}
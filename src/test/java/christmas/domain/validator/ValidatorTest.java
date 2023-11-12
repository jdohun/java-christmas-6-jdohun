package christmas.domain.validator;

import christmas.constant.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {

    @DisplayName("공백이나 빈값이 입력되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   "})
    void validateExistValue(String whiteSpace) {
        assertThatThrownBy(() -> Validator.validateExistValue(whiteSpace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_NOT_EXISTENT_VALUE);
    }

    @DisplayName("맨 앞자리가 0으로 시작하거나 숫자가 아닌 값이 입력되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "일", "1000d"})
    void validateNumericInput(String notNumber) {
        assertThatThrownBy(() -> Validator.validateNumericInput(notNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("쉼표 전후에 아무런 값이 없으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,,,3,4,5,6", ",,1,2,3,4,5,6", "1,2,3,4,5,6,", "1, , 2, 3, 4, 5, 6"})
    void hasCommasWithoutSurroundingValues(String valuesSeparatedByCommas) {
        assertThatThrownBy(() -> Validator.hasCommasWithoutSurroundingValues(valuesSeparatedByCommas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_CONTAIN_CONSECUTIVE_COMMAS);
    }

    @DisplayName("올바른 값이 들어왔을 때 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,6", "1, 2, 3, 4, 5, 6", "1, 2, 3 , 4, 5, 6"})
    void hasCommasWithoutSurroundingValues2(String valuesSeparatedByCommas) {
        assertThatCode(() -> Validator.hasCommasWithoutSurroundingValues(valuesSeparatedByCommas))
                .doesNotThrowAnyException();
    }

    @DisplayName("1~31 에 해당하는 숫자 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 32, 2023, 2022})
    void validateDayInRangeByInvalidValue(int inputDay) {
        assertThatThrownBy(() -> Validator.validateDayInRange(inputDay))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("1~31 에 해당하는 숫자면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 31})
    void validateDayInRangeValidValue(int inputDay) {
        assertThatCode(() -> Validator.validateDayInRange(inputDay))
                .doesNotThrowAnyException();
    }
}
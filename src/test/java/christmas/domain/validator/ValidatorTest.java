package christmas.domain.validator;

import christmas.constant.Menu;
import christmas.constant.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {

    private static List<HashMap<Menu, Integer>> provideMenuForValidateIfOnlyBeverageOrdered() {
        return Arrays.asList(
                createSample(Menu.ZERO_COKE, Menu.TAPAS, Menu.CHAMPAGNE),
                createSample(Menu.ZERO_COKE, Menu.T_BONE_STEAK, Menu.CHAMPAGNE),
                createSample(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM, Menu.CHAMPAGNE)
        );
    }

    private static HashMap<Menu, Integer> createSample(Menu... menus) {
        HashMap<Menu, Integer> sample = new HashMap<>();
        for (Menu menu : menus) {
            sample.put(menu, 1);
        }
        return sample;
    }


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
    void validateDayInRangeByValidValue(int inputDay) {
        assertThatCode(() -> Validator.validateDayInRange(inputDay))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴판 형식에 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asda-1", "1-asdsa"})
    void validateMenuFormatByOutOfShapeMenu(String outOfShape) {
        assertThatThrownBy(() -> Validator.validateMenuFormat(outOfShape))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

    @DisplayName("메뉴판 형식에 맞으면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"해물-1", "파스타-1", "라따뚜이-1"})
    void validateMenuFormatByFitForFormat(String fitForFormat) {
        assertThatCode(() -> Validator.validateMenuFormat(fitForFormat))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴판에 없는 메뉴를 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"해물", "파스타", "라따뚜이"})
    void validateExistMenuByNonExistentMenu(String nonExistentMenu) {
        assertThatThrownBy(() -> Validator.validateExistMenu(nonExistentMenu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

    @DisplayName("메뉴판에 있는 메뉴를 입력하면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"해산물파스타", "티본스테이크", "초코케이크"})
    void validateExistMenuByExistingtMenu(String existingMenu) {
        assertThatCode(() -> Validator.validateExistMenu(existingMenu))
                .doesNotThrowAnyException();
    }

    @DisplayName("총메뉴 개수가 20을 넘으면 예외가 발생한다.")
    @Test
    void validateMenuCountByOver20() {
        assertThatThrownBy(() -> Validator.validateMenuCount(21))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
    }

    @DisplayName("총메뉴 개수가 20을 넘지 않으면 예외가 발생한다.")
    @Test
    void validateExistMenuByBelow20() {
        assertThatCode(() -> Validator.validateMenuCount(20))
                .doesNotThrowAnyException();
    }

    @DisplayName("음료만 주문하면 예외가 발생한다.")
    @Test
    void validateIfOnlyBeverageOrderedByOnlyBeverageOrdered() {
        // given
        HashMap<Menu, Integer> onlyBeverageOrdered = createSample(Menu.ZERO_COKE, Menu.CHAMPAGNE, Menu.RED_WINE);

        // when
        // then
        assertThatThrownBy(() -> Validator.validateIfOnlyBeverageOrdered(onlyBeverageOrdered))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_ORDER_ONLY_BEVERAGE);
    }

    @DisplayName("음료 외 음식을 주문하면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @MethodSource("provideMenuForValidateIfOnlyBeverageOrdered")
    void validateIfOnlyBeverageOrderedByWithBeverageOrdered(HashMap<Menu, Integer> sampleOrder) {
        assertThatCode(() -> Validator.validateIfOnlyBeverageOrdered(sampleOrder))
                .doesNotThrowAnyException();
    }

    @DisplayName("한 번의 주문에 중복된 메뉴가 존재하면 예외가 발생한다.")
    @Test
    void validateExistDuplicateMenuByDuplicateMenu() {
        // given
        HashMap<Menu, Integer> sample = createSample(Menu.ZERO_COKE, Menu.T_BONE_STEAK, Menu.CHAMPAGNE);

        // when
        // then
        assertThatThrownBy(() -> Validator.validateExistDuplicateMenu(sample, Menu.T_BONE_STEAK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_ORDER);
    }

}
package christmas.domain.validator;

import christmas.constant.PatternConstant;
import christmas.constant.RegularConstant;
import christmas.constant.message.ErrorMessage;

import java.util.regex.Matcher;

public final class Validator {

    private Validator() {
    }

    public static String validateExistValue(String inputValue) {
        String value = inputValue.trim();
        if (value.equals("")) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_NOT_EXISTENT_VALUE);
        }
        return value;
    }

    public static int validateNumericInput(String inputValue) {
        Matcher matcher = PatternConstant.NUMBER_PATTERN.matcher(inputValue);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_NOT_NUMERIC_VALUE);
        }
        return Integer.parseInt(inputValue);
    }

    public static String[] hasCommasWithoutSurroundingValues(String valuesSeparatedByCommas) {
        Matcher matcher = PatternConstant.HAS_COMMAS_WITHOUT_SURROUNDING_VALUES_PATTERNS.matcher(valuesSeparatedByCommas);

        if (matcher.find()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_CONTAIN_CONSECUTIVE_COMMAS);
        }

        return valuesSeparatedByCommas.split(RegularConstant.INPUT_DELIMITER);
    }

    public static int validateDayInRange(int expectedVisitDate) {
        if (expectedVisitDate > 31 || expectedVisitDate < 1) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_OUT_OF_DAY);
        }
        return expectedVisitDate;
    }

}

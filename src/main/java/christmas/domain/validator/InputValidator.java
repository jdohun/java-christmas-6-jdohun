package christmas.domain.validator;

import christmas.view.InputView;

public final class InputValidator {
    public static int validateInputDay() {
        String inputExpectedVisitDayValue = InputView.inputExpectedVisitDay();
        Validator.validateExistValue(inputExpectedVisitDayValue);
        int expectedVisitDay = Validator.validateNumericInput(inputExpectedVisitDayValue);
        Validator.validateDayInRange(expectedVisitDay);
        return expectedVisitDay;
    }

    public static void validateInputOrderList() {
        String inputExpectedVisitDayValue = InputView.inputOrderList();
        Validator.validateExistValue(inputExpectedVisitDayValue);
    }
}

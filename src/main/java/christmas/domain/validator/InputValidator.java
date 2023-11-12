package christmas.domain.validator;

import christmas.constant.Menu;
import christmas.constant.RegularConstant;

import java.util.HashMap;

public final class InputValidator {
    private InputValidator() {
    }

    public static int validateInputDay(String inputExpectedVisitDayValue) {
        Validator.validateExistValue(inputExpectedVisitDayValue);
        int expectedVisitDay = Validator.validateNumericInput(inputExpectedVisitDayValue);
        Validator.validateDayInRange(expectedVisitDay);
        return expectedVisitDay;
    }

    public static HashMap<Menu, Integer> validateInputOrderValue(String inputOrderMenu) {
        Validator.validateExistValue(inputOrderMenu);
        Validator.hasCommasWithoutSurroundingValues(inputOrderMenu);
        String[] splitInputOrderMenu = inputOrderMenu.split(RegularConstant.ORDER_DELIMITER);

        return validateOrderPolicy(splitInputOrderMenu);
    }

    private static HashMap<Menu, Integer> validateOrderPolicy(String[] splitInputOrderMenu) {
        HashMap<Menu, Integer> orderMenu = new HashMap<>();
        int menuCount = 0;

        for (String separatedInputMenu : splitInputOrderMenu) {
            Validator.validateMenuFormat(separatedInputMenu);
            String[] menuNameWithCount = separatedInputMenu.split(RegularConstant.MENU_DELIMITER);
            Menu menu = Validator.validateExistMenu(menuNameWithCount[0]);
            Validator.validateExistDuplicateMenu(orderMenu, menu);
            orderMenu.put(menu, Integer.parseInt(menuNameWithCount[1]));
            menuCount += Integer.parseInt(menuNameWithCount[1]);
            Validator.validateMenuCount(menuCount);
        }

        Validator.validateIfOnlyBeverageOrdered(orderMenu);

        return orderMenu;
    }
}

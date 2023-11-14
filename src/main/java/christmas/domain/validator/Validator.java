package christmas.domain.validator;

import christmas.constant.Menu;
import christmas.constant.MenuCategory;
import christmas.constant.PatternConstant;
import christmas.constant.RegularConstant;
import christmas.constant.message.ErrorMessage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
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
            throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
        }
        return Integer.parseInt(inputValue);
    }

    public static String[] hasCommasWithoutSurroundingValues(String valuesSeparatedByCommas) {
        Matcher matcher = PatternConstant.HAS_COMMAS_WITHOUT_SURROUNDING_VALUES_PATTERNS.matcher(valuesSeparatedByCommas);
        if (matcher.find()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_CONTAIN_CONSECUTIVE_COMMAS);
        }
        return valuesSeparatedByCommas.split(RegularConstant.ORDER_DELIMITER);
    }

    public static void validateDayInRange(int expectedVisitDate) {
        if (expectedVisitDate > RegularConstant.MONTH_LAST || expectedVisitDate < RegularConstant.MONTH_FIRST) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
        }
    }

    public static String validateMenuFormat(String separatedInputMenu) {
        String trimedSeparatedInputMenu = separatedInputMenu.trim();
        Matcher matcher = PatternConstant.MENU_PATTERN.matcher(trimedSeparatedInputMenu);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_ORDER);
        }
        return trimedSeparatedInputMenu;
    }

    public static Menu validateExistMenu(String targetMenu) {
        for (Menu menu : Menu.values()) {
            if (menu.getName().equals(targetMenu)) {
                return menu;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_ORDER);
    }

    public static void validateMenuCount(int menuCount) {
        if (menuCount > 20) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
        }
    }

    public static void validateIfOnlyBeverageOrdered(Map<Menu, Integer> orderMenu) {
        Set<Menu> menus = orderMenu.keySet();
        boolean appetizer = false;
        boolean main = false;
        boolean dessert = false;

        for (Menu menu : menus) {
            if (menu.getCategory().equals(MenuCategory.APPETIZER)) {
                appetizer = true;
            }
            if (menu.getCategory().equals(MenuCategory.MAIN)) {
                main = true;
            }
            if (menu.getCategory().equals(MenuCategory.DESSERT)) {
                dessert = true;
            }
        }

        if (!appetizer && !main && !dessert) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_ORDER_ONLY_BEVERAGE);
        }
    }

    public static void validateExistDuplicateMenu(Map<Menu, Integer> orderMenu, Menu menu) {
        if (orderMenu.containsKey(menu)) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_ORDER);
        }
    }

    public static void validateCountOfMenu(Map<Menu, Integer> orderMenu) {
        int totalCountOfMenus = 0;
        Collection<Integer> quantityOfEachMenu = orderMenu.values();
        for (int count : quantityOfEachMenu) {
            totalCountOfMenus += count;
        }
        if (totalCountOfMenus > 20) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
        }
    }
}

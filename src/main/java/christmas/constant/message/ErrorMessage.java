package christmas.constant.message;

public final class ErrorMessage {
    private ErrorMessage() {
    }

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    public static final String ERROR_NOT_EXISTENT_VALUE = ERROR_MESSAGE_PREFIX + "값이 존재하지 않습니다.\n";
    public static final String ERROR_NOT_NUMERIC_VALUE = ERROR_MESSAGE_PREFIX + "숫자로만 이루어진 값을 입력해야 합니다.\n";
    public static final String ERROR_CONTAIN_CONSECUTIVE_COMMAS = ERROR_MESSAGE_PREFIX + "쉼표(,) 전후에 값이 없는 경우가 존재합니다. 다시 입력해 주세요.\n";

    /**
     * 방문할 날짜에 숫자가 입력되지 않은 경우
     * 1 이상 31 이하의 숫자를 입력하지 않은 경우
     */
    public static final String ERROR_INVALID_EXPECTED_DAY = ERROR_MESSAGE_PREFIX + "유효하지 않은 날짜입니다. 다시 입력해 주세요.\n";

    /**
     * 메뉴판에 없는 메뉴를 입력하는 경우
     * 메뉴의 개수에 1 이상의 숫자가 아닌 값이 있는 경우
     * 메뉴 형식이 예시와 다른 경우
     * 중복 메뉴를 입력한 경우
     */
    public static final String ERROR_INVALID_ORDER = ERROR_MESSAGE_PREFIX + "유효하지 않은 주문입니다. 다시 입력해 주세요.\n";
    public static final String ERROR_OVER_COUNT_OF_MENU = ERROR_MESSAGE_PREFIX + "메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다. 다시 입력해 주세요.\n";
    public static final String ERROR_ORDER_ONLY_BEVERAGE = ERROR_MESSAGE_PREFIX + "음료만 주문 시, 주문할 수 없습니다. 다시 입력해 주세요.\n";
}

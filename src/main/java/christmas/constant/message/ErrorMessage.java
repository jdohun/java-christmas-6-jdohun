package christmas.constant.message;

public final class ErrorMessage {
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    public static final String ERROR_NOT_EXISTENT_VALUE = ERROR_MESSAGE_PREFIX + "값이 존재하지 않습니다.\n";
    public static final String ERROR_NOT_NUMERIC_VALUE = ERROR_MESSAGE_PREFIX + "숫자로만 이루어진 값을 입력해야 합니다.\n";
    public static final String ERROR_CONTAIN_CONSECUTIVE_COMMAS = ERROR_MESSAGE_PREFIX + "쉼표(,) 전후에 값이 없는 경우가 존재합니다. 다시 입력해 주세요.\n";

    /**
     * 1 이상 31 이하의 날짜를 입력하지 않은 경우
     */
    public static final String ERROR_OUT_OF_DAY_RANGE = ERROR_MESSAGE_PREFIX + "유효하지 않은 날짜입니다. 다시 입력해 주세요.\n";

    /**
     * 메뉴판에 없는 메뉴를 입력하는 경우
     * 메뉴의 개수에 1 이상의 숫자가 아닌 값이 있는 경우
     * 메뉴 형식이 예시와 다른 경우
     */
    public static final String ERROR_INVALID_ORDER = ERROR_MESSAGE_PREFIX + "유효하지 않은 주문입니다. 다시 입력해 주세요.\n";
}

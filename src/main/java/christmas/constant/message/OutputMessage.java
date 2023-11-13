package christmas.constant.message;

public enum OutputMessage {
    PREVIEW_EVENT_BENEFITS_FORMAT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n"),
    PREVIEW_ORDER_MENU_FORMAT("%s %d개"),
    OUTPUT_AMOUNT_FORMAT("%,d원");

    private final String messageFormat;

    OutputMessage(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessage(){
        return this.messageFormat;
    }

    public String getIncludingAnIntMessage(int value) {
        return String.format(this.messageFormat, value);
    }

    public String getOrderMenuFormat(String menuName, int countOfMenu) {
        return String.format(this.messageFormat, menuName, countOfMenu);
    }

    public String getAmountFormat(int money) {
        return String.format(this.messageFormat, money);
    }
}

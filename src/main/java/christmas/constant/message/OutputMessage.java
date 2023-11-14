package christmas.constant.message;

import christmas.constant.Menu;

public enum OutputMessage {
    PREVIEW_EVENT_BENEFITS_FORMAT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n"),
    PREVIEW_ORDER_MENU_FORMAT("%s %d개"),
    PREVIEW_BENEFIT_DETAIL_FORMAT("%s: %,d원"),
    OUTPUT_AMOUNT_FORMAT("%,d원");

    private final String messageFormat;

    OutputMessage(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessage() {
        return this.messageFormat;
    }

    public String getIncludingAnIntMessage(int value) {
        return String.format(this.messageFormat, value);
    }

    public String getOrderMenuFormat(String menuName, int countOfMenu) {
        if (menuName.equals(Menu.GIVEAWAY_NONE.getName())) {
            return menuName;
        }
        return String.format(this.messageFormat, menuName, countOfMenu);
    }

    public String getAmountFormat(int money) {
        return String.format(this.messageFormat, money);
    }

    public String getBenefitDetailFormat(String title, int amount) {
        return String.format(this.messageFormat, title, amount);
    }
}

package christmas.constant;

public enum Menu {
    /*
    <애피타이저>
    양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)

    <메인>
    티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)

    <디저트>
    초코케이크(15,000), 아이스크림(5,000)

    <음료>
    제로콜라(3,000), 레드와인(60,000), 샴페인(25,000)
     */

    BUTTON_MUSHROOM_SOUP("애피타이저", "양송이수프", 6_000),
    TAPAS("애피타이저", "타파스", 5_500),
    CAESAR_SALAD("애피타이저", "시저샐러드", 8_000),

    T_BONE_STEAK("메인", "티본스테이크", 55_000),
    BARBECUE_RIBS("메인", "바비큐립", 54_000),
    SEAFOOD_PASTA("메인", "해산물파스타", 35_000),
    CHRISTMAS_PASTA("메인", "크리스마스파스타", 25_000),

    CHOCOLATE_CAKE("디저트", "초코케이크", 15_000),
    ICE_CREAM("디저트", "아이스크림", 5_000),

    ZERO_COKE("음료", "제로콜라", 3_000),
    RED_WINE("음료", "레드와인", 60_000),
    CHAMPAGNE("음료", "샴페인", 25_000);

    private final String category;
    private final String name;
    private final int price;

    Menu(String category, String name, int price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

}

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
    BUTTON_MUSHROOM_SOUP(MenuCategory.APPETIZER, "양송이수프", 6_000),
    TAPAS(MenuCategory.APPETIZER, "타파스", 5_500),
    CAESAR_SALAD(MenuCategory.APPETIZER, "시저샐러드", 8_000),

    T_BONE_STEAK(MenuCategory.MAIN, "티본스테이크", 55_000),
    BARBECUE_RIBS(MenuCategory.MAIN, "바비큐립", 54_000),
    SEAFOOD_PASTA(MenuCategory.MAIN, "해산물파스타", 35_000),
    CHRISTMAS_PASTA(MenuCategory.MAIN, "크리스마스파스타", 25_000),

    CHOCOLATE_CAKE(MenuCategory.DESSERT, "초코케이크", 15_000),
    ICE_CREAM(MenuCategory.DESSERT, "아이스크림", 5_000),

    ZERO_COKE(MenuCategory.BEVERAGE, "제로콜라", 3_000),
    RED_WINE(MenuCategory.BEVERAGE, "레드와인", 60_000),
    CHAMPAGNE(MenuCategory.BEVERAGE, "샴페인", 25_000),

    GIVEAWAY_NONE(MenuCategory.GIVEAWAY, "없음", 0),
    GIVEAWAY_CHAMPAGNE(MenuCategory.GIVEAWAY, CHAMPAGNE.getName(), -CHAMPAGNE.getPrice());

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

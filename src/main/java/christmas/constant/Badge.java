package christmas.constant;

public enum Badge {
    /*
        5천 원 이상: 별
        1만 원 이상: 트리
        2만 원 이상: 산타
     */
    NONE("NONE", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String badge;
    private final int condition;

    Badge(String badge, int condition) {
        this.badge = badge;
        this.condition = condition;
    }

    public String getBadge() {
        return badge;
    }

    public int getCondition() {
        return condition;
    }

    public static Badge getBadgeForAmount(int amount) {
        if (amount >= SANTA.getCondition()) {
            return SANTA;
        }

        if (amount >= TREE.getCondition()) {
            return TREE;
        }

        if (amount >= STAR.getCondition()) {
            return STAR;
        }

        return NONE;
    }
}

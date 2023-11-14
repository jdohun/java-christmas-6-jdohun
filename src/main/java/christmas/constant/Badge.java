package christmas.constant;

public enum Badge {
    /*
        5천 원 이상: 별
        1만 원 이상: 트리
        2만 원 이상: 산타
     */
    NONE("없음", 0),
    STAR("별", -5_000),
    TREE("트리", -10_000),
    SANTA("산타", -20_000);

    private final String name;
    private final int condition;

    Badge(String name, int condition) {
        this.name = name;
        this.condition = condition;
    }

    public int getCondition() {
        return condition;
    }

    public String getName() {
        return name;
    }

    public static String getBadgeByTotalBenefitAmount(int totalBenefitAmount) {
        if (totalBenefitAmount <= SANTA.getCondition()) {
            return SANTA.getName();
        }
        if (totalBenefitAmount <= TREE.getCondition()) {
            return TREE.getName();
        }
        if (totalBenefitAmount <= STAR.getCondition()) {
            return STAR.getName();
        }
        return NONE.getName();
    }
}

package christmas.domain.model;

import christmas.constant.DecemberEvent;
import christmas.constant.DiscountConstant;
import christmas.constant.Menu;
import christmas.constant.MenuCategory;
import christmas.constant.message.OutputMessage;
import christmas.domain.validator.Validator;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class OrderInfo {
    private final int expectedVisitDay;
    private final HashMap<Menu, Integer> orderMenu;
    private final int totalAmountBeforeDiscount;
    private final boolean eventEligibility;
    private final AbstractMap.SimpleEntry<Menu, Integer> giveawayMenu;
    private final HashMap<String, Integer> benefitDetails;

    public OrderInfo(int expectedVisitDay, HashMap orderMenu) {
        Validator.validateDayInRange(expectedVisitDay);
        Validator.validateCountOfMenu(orderMenu);

        this.expectedVisitDay = expectedVisitDay;
        this.orderMenu = orderMenu;
        this.totalAmountBeforeDiscount = calculateTotalAmountBeforeDiscount();
        this.eventEligibility = checkEventEligibility();
        this.giveawayMenu = checkGiveawayEventEligibility();
        this.benefitDetails = initializeBenefitDetails();
    }

    public int getTotalAmountBeforeDiscount() {
        return totalAmountBeforeDiscount;
    }

    public AbstractMap.SimpleEntry<Menu, Integer> getGiveawayMenu() {
        return giveawayMenu;
    }


    /**
     * @return 예상 방문 날짜를 포함한 12월 이벤트 미리 보기 제목
     */
    public String getIncludingExpectedVisitDayPreviewTitle() {
        return OutputMessage
                .PREVIEW_EVENT_BENEFITS_FORMAT
                .getIncludingAnIntMessage(expectedVisitDay);
    }

    /**
     * @return 할인 전 총주문 금액
     */
    private int calculateTotalAmountBeforeDiscount() {
        return orderMenu.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    /**
     * 주문한 메뉴 내역과 수량을 출력
     */
    public void showMenu() {
        orderMenu.forEach((menu, count) -> {
            System.out.println(
                    OutputMessage
                            .PREVIEW_ORDER_MENU_FORMAT
                            .getOrderMenuFormat(menu.getName(), count)
            );
        });
        System.out.println();
    }

    /**
     * 혜택 내역 출력
     */
    public void showBenefitDetails() {
        benefitDetails.forEach((title, amount) -> {
            System.out.println(
                    OutputMessage
                            .PREVIEW_BENEFIT_DETAIL_FORMAT
                            .getBenefitDetailFormat(title, amount)
            );
        });
        System.out.println();
    }

    /**
     * @return 총혜택 금액
     */
    public int calculateBenefitAmount() {
        return benefitDetails.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    /**
     * 할인 이벤트 조건을 달성했는지 확인
     * 할인 전 총주문 금액이 10,000원 이상인지 확인
     */
    private boolean checkEventEligibility() {
        return totalAmountBeforeDiscount >= DecemberEvent.EVENT_ELIGIBILITY_AMOUNT;
    }

    /**
     * 조건을 달성한 12월 이벤트 혜택 내역을 조사
     *
     * @return 조건을 달성한 12월 이벤트 혜택 내역
     */
    private HashMap<String, Integer> initializeBenefitDetails() {
        HashMap<String, Integer> benefitDetails = new HashMap<>();
        AbstractMap.SimpleEntry<String, Integer> christmasDdayBenefitDetail = calculateChristmasDdayBenefitDetail();
        AbstractMap.SimpleEntry<String, Integer> giveawayEventDetail = calculateGiveawayEventDetail();
        AbstractMap.SimpleEntry<String, Integer> dailyEventDetail = checkDecemberDailyEventCategory();
        AbstractMap.SimpleEntry<String, Integer> specialDayEventDetail = checkDecemberEventSpecialDay();

        benefitDetails.put(christmasDdayBenefitDetail.getKey(), christmasDdayBenefitDetail.getValue());
        benefitDetails.put(giveawayEventDetail.getKey(), giveawayEventDetail.getValue());
        benefitDetails.put(dailyEventDetail.getKey(), dailyEventDetail.getValue());
        benefitDetails.put(specialDayEventDetail.getKey(), specialDayEventDetail.getValue());
        return benefitDetails;
    }

    /**
     * 증정 이벤트 조건을 달성했는지 확인
     *
     * @return 증정 메뉴, 수량
     */
    private AbstractMap.SimpleEntry<Menu, Integer> checkGiveawayEventEligibility() {
        if (eventEligibility && totalAmountBeforeDiscount >= DecemberEvent.EVENT_GIVEAWAY_ELIGIBILITY_AMOUNT) {
            return new AbstractMap.SimpleEntry<>(Menu.GIVEAWAY_CHAMPAGNE, 1);
        }
        return new AbstractMap.SimpleEntry<>(Menu.GIVEAWAY_NONE, 0);
    }

    /**
     * 증정 이벤트 혜택 금액을 계산
     *
     * @return 혜택 이름, 혜택 금액
     */
    private AbstractMap.SimpleEntry<String, Integer> calculateGiveawayEventDetail() {
        if (eventEligibility && totalAmountBeforeDiscount >= DecemberEvent.EVENT_GIVEAWAY_ELIGIBILITY_AMOUNT) {
            return new AbstractMap.SimpleEntry<>(DecemberEvent.GIVEAWAY_EVENT, Menu.GIVEAWAY_CHAMPAGNE.getPrice());
        }
        return new AbstractMap.SimpleEntry<>(DecemberEvent.NONE, 0);
    }

    /**
     * 크리스마스 디데이 이벤트 조건을 달성했는지 확인
     *
     * @return 혜택 이름, 혜택 금액
     */
    private AbstractMap.SimpleEntry<String, Integer> calculateChristmasDdayBenefitDetail() {
        if (eventEligibility && expectedVisitDay <= DecemberEvent.CHRISTMAS) {
            return new AbstractMap.SimpleEntry<>(DecemberEvent.CHRISTMAS_D_DAY_DISCOUNT,
                    DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_INITIAL
                            + DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_ADD * (expectedVisitDay - 1));
        }
        return new AbstractMap.SimpleEntry<>(DecemberEvent.NONE, 0);
    }

    /**
     * 해당하는 12월 일일 이벤트 혜택 금액을 계산
     *
     * @return 일일 혜택 이름, 혜택 금액
     */
    private AbstractMap.SimpleEntry<String, Integer> calculateDecemberDailyEventDiscountAmount(String dailyEventCategory, String discountMenuCategory) {
        int countOfDiscountMenu = orderMenu.entrySet().stream()
                .filter(entry -> entry.getKey().getCategory() == discountMenuCategory)
                .mapToInt(Map.Entry::getValue)
                .sum();

        return new AbstractMap.SimpleEntry<>(dailyEventCategory, countOfDiscountMenu * DiscountConstant.DAILY_DISCOUNT_AMOUNT);
    }

    /**
     * 12월 일일 이벤트 조건을 달성했는지 확인
     *
     * @return calculateDecemberEventDiscountAmount(일일 혜택 구분, 혜택 메뉴 구분)
     */
    private AbstractMap.SimpleEntry<String, Integer> checkDecemberDailyEventCategory() {
        if (eventEligibility) {
            if (expectedVisitDay % 7 != DecemberEvent.FRIDAY || expectedVisitDay % 7 != DecemberEvent.SATURDAY) { // 평일
                return calculateDecemberDailyEventDiscountAmount(DecemberEvent.WEEKDAY_DISCOUNT, MenuCategory.DESSERT);
            }

            if (expectedVisitDay == DecemberEvent.FRIDAY || expectedVisitDay % 7 == DecemberEvent.SATURDAY) { // 주말(금,토)
                return calculateDecemberDailyEventDiscountAmount(DecemberEvent.WEEKEND_DISCOUNT, MenuCategory.MAIN);
            }
        }
        return calculateDecemberDailyEventDiscountAmount(DecemberEvent.NONE, MenuCategory.NONE);
    }

    /**
     * 12월 일일 이벤트 중 특별 조건을 달성했는지 확인
     *
     * @return 일일 혜택 이름, 혜택 금액
     */
    private AbstractMap.SimpleEntry<String, Integer> checkDecemberEventSpecialDay() {
        if (eventEligibility) {
            if (expectedVisitDay % 7 == DecemberEvent.SUNDAY || expectedVisitDay == DecemberEvent.CHRISTMAS) { // 별이 있는 날(일요일, 크리스마스)
                return new AbstractMap.SimpleEntry<>(DecemberEvent.SPECIAL_DISCOUNT, DiscountConstant.SPECIAL_DISCOUNT_AMOUNT);
            }
        }
        return new AbstractMap.SimpleEntry<>(DecemberEvent.NONE, 0);
    }

    // 배지 부여
    // 총혜택 금액 계산
    // 크리스마스 디데이 할인 + 이벤트 할인 + 증정이벤트(샴페인-25,000)
    // 기준에 따라 배지부여

}

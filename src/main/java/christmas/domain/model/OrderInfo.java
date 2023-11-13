package christmas.domain.model;

import christmas.constant.DiscountConstant;
import christmas.constant.EventConstant;
import christmas.constant.Menu;
import christmas.constant.message.OutputMessage;
import christmas.domain.validator.Validator;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;

public class OrderInfo {
    private final int expectedVisitDay;
    private final HashMap<Menu, Integer> orderMenu;
    private final int totalAmountBeforeDiscount;
    private final boolean eventEligibility;
    private final AbstractMap.SimpleEntry<Menu, Integer> giveawayMenu;


    public OrderInfo(int expectedVisitDay, HashMap orderMenu) {
        Validator.validateDayInRange(expectedVisitDay);
        Validator.validateCountOfMenu(orderMenu);

        this.expectedVisitDay = expectedVisitDay;
        this.orderMenu = orderMenu;
        this.totalAmountBeforeDiscount = calculateTotalAmountBeforeDiscount();
        this.eventEligibility = checkEventEligibility();
        this.giveawayMenu = checkGiveawayEventEligibility();

    }

    public int getTotalAmountBeforeDiscount() {
        return totalAmountBeforeDiscount;
    }

    public AbstractMap.SimpleEntry<Menu, Integer> getGiveawayMenu() {
        return giveawayMenu;
    }


    public String getIncludingExpectedVisitDayPreviewTitle() {
        return OutputMessage
                .PREVIEW_EVENT_BENEFITS_FORMAT
                .getIncludingAnIntMessage(expectedVisitDay);
    }

    private int calculateTotalAmountBeforeDiscount() {
        int totalAmountBeforeDiscount = 0;
        Collection<Integer> values = orderMenu.values();
        for (int value : values) {
            totalAmountBeforeDiscount += value;
        }
        return totalAmountBeforeDiscount;
    }

    public void showMenu() {
        orderMenu.forEach((key, value) -> {
            System.out.println(
                    OutputMessage
                            .PREVIEW_ORDER_MENU_FORMAT
                            .getOrderMenuFormat(key.getName(), value)
            );
            System.out.println();
        });
    }

    // 할인 전 총주문 금액이 10,000원 이상인지 확인
    private boolean checkEventEligibility() {
        if (totalAmountBeforeDiscount >= EventConstant.EVENT_ELIGIBILITY_AMOUNT) {
            return true;
        }
        return false;
    }

    // 증정이벤트
    private AbstractMap.SimpleEntry<Menu, Integer> checkGiveawayEventEligibility() {
        if (eventEligibility && totalAmountBeforeDiscount >= EventConstant.EVENT_GIVEAWAY_ELIGIBILITY_AMOUNT) {
            return new AbstractMap.SimpleEntry<>(Menu.GIVEAWAY_CHAMPAGNE, 1);
        }
        return new AbstractMap.SimpleEntry<>(Menu.GIVEAWAY_NONE, 0);
    }

    private int calculateChristmasDdayDiscountAmount() {
        if (eventEligibility && expectedVisitDay <= EventConstant.CHRISTMAS) {
            return DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_INITIAL
                    + DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_ADD * (expectedVisitDay-1);
        }
        return 0;
    }

    // 12월 이벤트 할인
    // 할인 전 총주문 금액이 10,000원 이상인지 확인
    // 평일인지 주말인지 별이 있는 날인지 확인
    // 해당하는 할인 혜택 적용

    // 배지 부여
    // 총혜택 금액 계산
    // 크리스마스 디데이 할인 + 이벤트 할인 + 증정이벤트(샴페인-25,000)
    // 기준에 따라 배지부여

}

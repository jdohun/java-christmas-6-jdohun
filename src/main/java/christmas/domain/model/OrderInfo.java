package christmas.domain.model;

import christmas.constant.Menu;
import christmas.constant.message.OutputMessage;
import christmas.domain.validator.Validator;

import java.util.Collection;
import java.util.HashMap;

public class OrderInfo {
    private int expectedVisitDay;
    private HashMap<Menu, Integer> orderMenu;

    public OrderInfo(int expectedVisitDay, HashMap orderMenu) {
        Validator.validateDayInRange(expectedVisitDay);
        Validator.validateCountOfMenu(orderMenu);

        this.expectedVisitDay = expectedVisitDay;
        this.orderMenu = orderMenu;
    }

    public String getIncludingExpectedVisitDayPreviewTitle() {
        return OutputMessage
                .PREVIEW_EVENT_BENEFITS_FORMAT
                .getIncludingAnIntMessage(expectedVisitDay);
    }

    public void showMenu() {
        orderMenu.forEach((key, value) -> {
            System.out.println(
                    OutputMessage
                            .PREVIEW_ORDER_MENU_FORMAT
                            .getOrderMenuFormat(key.getName(), value)
            );
        });
    }

    public int calculateTotalAmountBeforeDiscount() {
        int totalAmountBeforeDiscount = 0;
        Collection<Integer> values = orderMenu.values();
        for (int value : values) {
            totalAmountBeforeDiscount += value;
        }
        return totalAmountBeforeDiscount;
    }

    // 크리스마스 디데이 할인
    // 총주문 금액이 10,000원 이상인지 확인
    // 크리스마스 디데이 기간에 해당하는 날짜인지 확인
    // 해당하면 할인 금액 계산


    // 12월 이벤트 할인
    // 총주문 금액이 10,000원 이상인지 확인
    // 평일인지 주말인지 별이 있는 날인지 확인
    // 해당하는 할인 혜택 적용

    // 증정이벤트
    // 총주문 금액이 120,000원 이상인지 확인
    // 해당하면 샴페인 증정
    // 샴페인이 주문내역에 존재하는지 확인
    // 존재하면 +1 아니면 추가

    // 배지 부여
    // 총혜택 금액 계산
    // 크리스마스 디데이 할인 + 이벤트 할인 + 증정이벤트(샴페인-25,000)
    // 기준에 따라 배지부여

}

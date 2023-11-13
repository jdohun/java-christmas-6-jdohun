package christmas.domain.model;

import christmas.constant.Menu;
import christmas.domain.validator.Validator;

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

    // 생성 전에 날짜가 1~31 사이인지 검증
    // 생성 전에 메뉴의 총개수가 20 이하인지 검증

    // 주문 메뉴 백브리핑

    // 할인 전 총 주문 금액 계산

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

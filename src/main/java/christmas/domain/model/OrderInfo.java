package christmas.domain.model;

import christmas.constant.Menu;

import java.util.HashMap;

public class OrderInfo {
    private int expectedVisitDay;
    private HashMap<Menu, Integer> orderMenu;

    public OrderInfo(int expectedVisitDay, HashMap orderMenu) {
        this.expectedVisitDay = expectedVisitDay;
        this.orderMenu = orderMenu;
    }
}

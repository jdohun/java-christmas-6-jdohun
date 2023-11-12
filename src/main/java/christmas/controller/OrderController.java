package christmas.controller;

import christmas.domain.model.OrderInfo;
import christmas.generator.OrderGenerator;

public class OrderController {
    private final OrderGenerator orderGenerator = new OrderGenerator();

    public void run() {
        OrderInfo orderInfo = orderGenerator.run();
    }

}

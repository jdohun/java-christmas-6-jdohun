package christmas.controller;

import christmas.generator.OrderGenerator;

public class OrderController {
    private final OrderGenerator orderGenerator = new OrderGenerator();

    public void run() {
        orderGenerator.run();
    }

}

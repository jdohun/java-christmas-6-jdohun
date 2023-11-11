package christmas.controller;

import christmas.domain.validator.InputValidator;
import christmas.utils.InputSupplier;

public class OrderController {

    public void run() {
        int expectedVisitDay = getInput(() -> InputValidator.validateInputDay());
    }

    private <T> T getInput(InputSupplier<T> inputSupplier) {
        while (true) try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

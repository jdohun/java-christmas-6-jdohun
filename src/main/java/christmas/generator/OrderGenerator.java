package christmas.generator;

import christmas.constant.Menu;
import christmas.domain.model.OrderInfo;
import christmas.domain.validator.InputValidator;
import christmas.utils.InputSupplier;
import christmas.view.InputView;

import java.util.HashMap;
import java.util.Map;

public class OrderGenerator {
    public OrderInfo run() {
        int expectedVisitDay = getInput(() -> InputValidator.validateInputDay(InputView.inputExpectedVisitDay()));
        Map<Menu, Integer> orderMenu = getInput(() -> InputValidator.validateInputOrderValue(InputView.inputOrderList()));

        return new OrderInfo(expectedVisitDay, orderMenu);
    }

    private <T> T getInput(InputSupplier<T> inputSupplier) {
        while (true) try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

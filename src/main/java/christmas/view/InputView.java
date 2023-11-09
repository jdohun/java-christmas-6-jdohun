package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.constant.message.InputMessage;

public final class InputView {
    private InputView() {
    }

    public static String inputExpectedVisitDay() {
        System.out.println(InputMessage.INPUT_EXPECTED_VISIT_DAY);
        return Console.readLine();
    }

    public static String inputOrderList() {
        System.out.println(InputMessage.INPUT_ORDER_LIST);
        return Console.readLine();
    }

}

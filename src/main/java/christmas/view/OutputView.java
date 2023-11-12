package christmas.view;

import christmas.constant.message.OutputMessage;

public final class OutputView {
    private OutputView() {
    }

    public static void previewEventBenefits(int expectedVisitDay) {
        System.out.println(OutputMessage
                .PREVIEW_EVENT_BENEFITS_FORMAT
                .getIncludingValueMessage(expectedVisitDay));
    }
}

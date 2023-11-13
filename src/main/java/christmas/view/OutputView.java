package christmas.view;

import christmas.constant.Menu;
import christmas.constant.PreviewTitle;
import christmas.constant.message.OutputMessage;
import christmas.domain.model.OrderInfo;

import java.util.AbstractMap;

public final class OutputView {
    private OutputView() {
    }

    public static void previewEventBenefits(OrderInfo orderInfo) {
        AbstractMap.SimpleEntry<Menu, Integer> giveawayMenu = orderInfo.getGiveawayMenu();

        System.out.println(orderInfo.getIncludingExpectedVisitDayPreviewTitle());
        System.out.println(PreviewTitle.ORDER_MENU);
        orderInfo.showMenu();
        System.out.println(PreviewTitle.TOTAL_PRICE_BEFORE_DISCOUNT);
        System.out.println(OutputMessage.OUTPUT_AMOUNT_FORMAT
                .getAmountFormat(orderInfo.getTotalAmountBeforeDiscount()) + "\n");
        System.out.println(PreviewTitle.GIVEAWAY_MENU);
        System.out.println(
                OutputMessage
                        .PREVIEW_ORDER_MENU_FORMAT
                        .getOrderMenuFormat(giveawayMenu.getKey().getName(), giveawayMenu.getValue()) + "\n"
        );
    }
}

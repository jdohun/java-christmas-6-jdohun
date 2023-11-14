package christmas.view;

import christmas.constant.Menu;
import christmas.constant.PreviewTitle;
import christmas.constant.message.OutputMessage;
import christmas.domain.model.OrderInfo;

import java.util.AbstractMap;

public final class OutputView {
    private OutputView() {
    }

    public static void showPreviewTitle(OrderInfo orderInfo) {
        System.out.println(orderInfo.getIncludingExpectedVisitDayPreviewTitle());
    }

    public static void showPreviewOrderMenu(OrderInfo orderInfo) {
        System.out.println(PreviewTitle.ORDER_MENU);
        orderInfo.showMenu();
    }

    public static void showPreviewTotalPriceBeforeDiscount(OrderInfo orderInfo) {
        System.out.println(PreviewTitle.TOTAL_PRICE_BEFORE_DISCOUNT);
        System.out.println(
                OutputMessage.OUTPUT_AMOUNT_FORMAT
                .getAmountFormat(orderInfo.getTotalAmountBeforeDiscount()) + "\n"
        );
    }

    public static void showPreviewGiveawayMenu(OrderInfo orderInfo) {
        AbstractMap.SimpleEntry<Menu, Integer> giveawayMenu = orderInfo.getGiveawayMenu();
        System.out.println(PreviewTitle.GIVEAWAY_MENU);
        System.out.println(
                OutputMessage
                        .PREVIEW_ORDER_MENU_FORMAT
                        .getOrderMenuFormat(giveawayMenu.getKey().getName(), giveawayMenu.getValue()) + "\n"
        );
    }

    public static void showBenefitDetail(OrderInfo orderInfo) {
        System.out.println(PreviewTitle.BENEFIT_DETAILS);
        orderInfo.showBenefitDetails();
    }

    public static void showTotalBenefitAmount(OrderInfo orderInfo) {
        System.out.println(PreviewTitle.TOTAL_BENEFIT_AMOUNT);
        System.out.println(
                OutputMessage
                        .OUTPUT_AMOUNT_FORMAT
                        .getAmountFormat(orderInfo.calculateBenefitAmount()) + "\n"
        );
    }

    public static void previewEventBenefits(OrderInfo orderInfo) {
        showPreviewTitle(orderInfo);
        showPreviewOrderMenu(orderInfo);
        showPreviewTotalPriceBeforeDiscount(orderInfo);
        showPreviewGiveawayMenu(orderInfo);
        showBenefitDetail(orderInfo);
        showTotalBenefitAmount(orderInfo);

    }
}

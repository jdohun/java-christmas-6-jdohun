package christmas.domain.model;

import christmas.constant.Badge;
import christmas.constant.DecemberEvent;
import christmas.constant.DiscountConstant;
import christmas.constant.Menu;
import christmas.constant.message.ErrorMessage;
import christmas.constant.message.OutputMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class OrderInfoTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    // 원래 System.out 원복
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @DisplayName("총메뉴 개수가 20을 넘으면 예외가 발생한다.")
    @Test
    void createOrderInfoByOver20CountOfMenu() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 8);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatThrownBy(() -> new OrderInfo(1, sample))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_OVER_COUNT_OF_MENU);
    }

    @DisplayName("총메뉴 개수가 20을 넘지 않으면 예외가 발생하지 않는다.")
    @Test
    void createOrderInfoByBelow20CountOfMenu() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatCode(() -> new OrderInfo(1, sample))
                .doesNotThrowAnyException();
    }

    @DisplayName("1~31 에 포함되지 않는 날짜를 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 32, 33, 34, 35})
    void createOrderInfoByDayNotInRange(int expectedVisitDay) {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatThrownBy(() -> new OrderInfo(expectedVisitDay, sample))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_INVALID_EXPECTED_DAY);
    }

    @DisplayName("1~31 에 포함되는 날짜를 입력하면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void createOrderInfoByDayInRange(int expectedVisitDay) {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ZERO_COKE, 12);
        sample.put(Menu.T_BONE_STEAK, 7);
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatCode(() -> new OrderInfo(expectedVisitDay, sample))
                .doesNotThrowAnyException();
    }

    @DisplayName("음료만 주문하면 예외가 발생한다.")
    @Test
    void createOrderInfoByOnlyBeverage() {
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.CHAMPAGNE, 1);

        // when
        // then
        assertThatThrownBy(() -> new OrderInfo(1, sample))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ERROR_ORDER_ONLY_BEVERAGE);
    }

    @DisplayName("할인 전 총주문 금액이 10,000원 미만이면 이벤트가 적용되지 않는다.")
    @Test
    void createOrderInfoByUnder10_000MenuAmount() {
        // <디저트> 아이스크림(5,000)
        // given
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 1);

        // when
        OrderInfo orderInfo = new OrderInfo(1, sample);

        // then
        assertThat(orderInfo.calculateBenefitAmount())
                .isEqualTo(0);

        assertThat(orderInfo.calculateTotalDiscountAmount())
                .isEqualTo(0);

        assertThat(orderInfo.getTotalAmountBeforeDiscount())
                .isEqualTo(orderInfo.calculateTotalAmountAfterDiscount());

        assertThat(orderInfo.getBadgeName())
                .isEqualTo(DecemberEvent.NONE);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).contains(DecemberEvent.NONE);
    }

    @DisplayName("할인 전 총주문 금액이 10,000원 이상이고 날짜가 1~크리스마스(25) 이면 디데이 이벤트가 적용된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25})
    void checkChristmasDdayEventByOver10_000MenuAmount(int expectedVisitDay) {
        // Arrange
        int countOfIceCream = 2;

        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, countOfIceCream);

        // Act
        OrderInfo orderInfo = new OrderInfo(expectedVisitDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).contains(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT.getBenefitDetailFormat(
                DecemberEvent.CHRISTMAS_D_DAY_DISCOUNT, DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_INITIAL + DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_ADD * (expectedVisitDay - 1)));
    }

    @DisplayName("할인 전 총주문 금액이 10,000원 이상이고 25(크리스마스)가 지난 이후이면 디데이 이벤트가 적용되지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    void checkChristmasDdayEventByOver10_000MenuAmountAfterChristmas(int expectedVisitDay) {
        // Arrange
        int countOfIceCream = 2;

        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, countOfIceCream);

        // Act
        OrderInfo orderInfo = new OrderInfo(expectedVisitDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).doesNotContain(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT.getBenefitDetailFormat(
                DecemberEvent.CHRISTMAS_D_DAY_DISCOUNT, DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_INITIAL + DiscountConstant.CHRISTMAS_D_DAY_DISCOUNT_AMOUNT_ADD * (expectedVisitDay - 1)));
    }

    @DisplayName("금요일과 토요일(7로 나눈 나머지가 1 또는 2)은 주말 할인(메인 메뉴 할인)을 메뉴 개수만큼 받는다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1,8", "2,2,7", "8,3,6", "9,4,5", "15,5,4", "16,6,3", "22,7,2", "23,8,1", "29,9,10", "30,10,9"})
    void checkWeekendEventByWeekendWithMain(int fridayOrSaturDay, int countOfChristmasPasta, int countOfSeafoodPasta) {
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.CHRISTMAS_PASTA, countOfChristmasPasta);
        sample.put(Menu.SEAFOOD_PASTA, countOfSeafoodPasta);

        // Act
        OrderInfo orderInfo = new OrderInfo(fridayOrSaturDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT.getBenefitDetailFormat(
                DecemberEvent.WEEKEND_DISCOUNT, DiscountConstant.DAILY_DISCOUNT_AMOUNT * (countOfChristmasPasta + countOfSeafoodPasta)));

        assertThat(outputStream.toString()).contains(expectedOutput.toString());
    }

    @DisplayName("금요일과 토요일(7로 나눈 나머지가 1 또는 2)에 메인 메뉴를 주문하지 않으면 혜택이 포함되지 않는다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1,8", "2,2,7", "8,3,6", "9,4,5", "15,5,4", "16,6,3", "22,7,2", "23,8,1", "29,9,10", "30,10,9"})
    void checkWeekendEventByWeekendWithNotMain(int fridayOrSaturDay, int countOfIceCream, int countOfTapas) {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, countOfIceCream);
        sample.put(Menu.TAPAS, countOfTapas);

        // Act
        OrderInfo orderInfo = new OrderInfo(fridayOrSaturDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).doesNotContain(DecemberEvent.WEEKEND_DISCOUNT);
    }

    @DisplayName("금요일과 토요일(7로 나눈 나머지가 1 과 2)이 아닌 평일은 평일 할인(디저트 메뉴 할인)을 받는다.")
    @ParameterizedTest
    @CsvSource(value = {"3,1,19", "4,2,18", "5,3,17", "6,4,16", "7,5,15", "10,6,14", "11,7,13", "12,8,12", "13,9,11", "14,10,10", "17,11,9", "18,12,8", "19,13,7", "20,14,6", "21,15,5", "24,16,4", "25,17,3", "26,18,2", "27,19,1", "28,11,9", "31,9,11"})
    void checkWeekdayEventByWeekdayWithDessert(int weekday, int countOfIceCream, int countOfChocolateCake) {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, countOfIceCream);
        sample.put(Menu.CHOCOLATE_CAKE, countOfChocolateCake);

        // Act
        OrderInfo orderInfo = new OrderInfo(weekday, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT.getBenefitDetailFormat(
                DecemberEvent.WEEKDAY_DISCOUNT, DiscountConstant.DAILY_DISCOUNT_AMOUNT * (countOfIceCream + countOfChocolateCake)));

        assertThat(outputStream.toString()).contains(expectedOutput.toString());
    }

    @DisplayName("금요일과 토요일(7로 나눈 나머지가 1 과 2)이 아닌 평일에 디저트 메뉴를 주문하지 않으면 혜택에 포함되지 않는다.")
    @ParameterizedTest
    @CsvSource(value = {"3,1,19", "4,2,18", "5,3,17", "6,4,16", "7,5,15", "10,6,14", "11,7,13", "12,8,12", "13,9,11", "14,10,10", "17,11,9", "18,12,8", "19,13,7", "20,14,6", "21,15,5", "24,16,4", "25,17,3", "26,18,2", "27,19,1", "28,11,9", "31,9,11"})
    void checkWeekdayEventByWeekdayWithNotDessert(int weekday, int countOfCaesarSalad, int countOfZeroCoke) {
        // <애피타이저> 시저샐러드(8,000)
        // <음료> 제로콜라(3,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.CAESAR_SALAD, countOfCaesarSalad);
        sample.put(Menu.ZERO_COKE, countOfZeroCoke);

        // Act
        OrderInfo orderInfo = new OrderInfo(weekday, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).doesNotContain(DecemberEvent.WEEKDAY_DISCOUNT);
    }

    @DisplayName("일요일(7로 나눈 나머지가 3) 이거나 크리스마스(25)는 특별 할인을 받는다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void checkSpecialDayEventBySpecialDay(int specialDay) {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 2);

        // Act
        OrderInfo orderInfo = new OrderInfo(specialDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT
                .getBenefitDetailFormat(DecemberEvent.SPECIAL_DISCOUNT, DiscountConstant.SPECIAL_DISCOUNT_AMOUNT));

        assertThat(outputStream.toString()).contains(expectedOutput.toString());
    }

    @DisplayName("일요일(7로 나눈 나머지가 3) 과 크리스마스(25)가 아니면 특별 할인을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1,2,4,5,6,7,8,9,11,12,13,14,15,16,18,19,20,21,22,23,26,27,28,29,30})
    void checkSpecialDayEventByNotSpecialDay(int notSpecialDay) {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 2);

        // Act
        OrderInfo orderInfo = new OrderInfo(notSpecialDay, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).doesNotContain(DecemberEvent.SPECIAL_DISCOUNT);
    }

    @DisplayName("할인 전 총주문 금액이 120,000원 이상이면 샴페인 1개를 증정 받는다.")
    @Test
    void checkGiveawayEventByOverTotalAmount120_000BeforeDiscount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.T_BONE_STEAK, 3);

        // Act
        OrderInfo orderInfo = new OrderInfo(1, sample);

        // Verify
        assertThat(orderInfo.getGiveawayMenu())
                .isEqualTo(new AbstractMap.SimpleEntry<Menu, Integer>(Menu.GIVEAWAY_CHAMPAGNE, 1) {
                });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(OutputMessage.PREVIEW_BENEFIT_DETAIL_FORMAT
                .getBenefitDetailFormat(DecemberEvent.GIVEAWAY_EVENT, Menu.GIVEAWAY_CHAMPAGNE.getPrice()));

        assertThat(outputStream.toString()).contains(expectedOutput.toString());
    }

    @DisplayName("할인 전 총주문 금액이 120,000원 미만이면 증정 이벤트 혜택을 받지 않는다.")
    @Test
    void checkGiveawayEventByUnderTotalAmount120_000BeforeDiscount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.T_BONE_STEAK, 1);

        // Act
        OrderInfo orderInfo = new OrderInfo(1, sample);

        // Verify
        assertThat(orderInfo.getGiveawayMenu())
                .isEqualTo(new AbstractMap.SimpleEntry<Menu, Integer>(Menu.GIVEAWAY_NONE,0) {
                });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderInfo.showBenefitDetails();

        assertThat(outputStream.toString()).doesNotContain(DecemberEvent.GIVEAWAY_EVENT);
    }

    @DisplayName("총혜택 금액은 증정 이벤트 가격을 포함하고 총할인 금액은 증정 이벤트 가격을 제외한다.")
    @Test
    void checkTotalBenefitAmount() {
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.T_BONE_STEAK, 3);

        // Act
        OrderInfo orderInfo = new OrderInfo(1, sample);

        // Verify
        assertThat(orderInfo.getTotalAmountBeforeDiscount()
                - orderInfo.calculateTotalAmountAfterDiscount()
                + orderInfo.getTotalBenefitAmount())
                .isEqualTo(Menu.GIVEAWAY_CHAMPAGNE.getPrice());
    }

    @DisplayName("총혜택 금액이 5000 미만이면 배지를 부여하지 않는다.")
    @Test
    void checkGrantBadgeByUnder5000BenefitAmount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 2);

        // Act
        OrderInfo orderInfo = new OrderInfo(1, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ;

        assertThat(orderInfo.getBadgeName())
                .isEqualTo(Badge.NONE.getName());
    }

    @DisplayName("총혜택 금액이 5_000 이상 10_000 미만이면 별 배지를 부여한다.")
    @Test
    void checkGrantBadgeByOver5_000Under10_000BenefitAmount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        int sunday = 3; // -1200 -1000 = -2200
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 2); // -2023 * 2 = -4046

        // total = 6646

        // Act
        OrderInfo orderInfo = new OrderInfo(sunday, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThat(orderInfo.getBadgeName())
                .isEqualTo(Badge.STAR.getName());
    }

    @DisplayName("총혜택 금액이 10_000 이상 20_000 미만이면 트리 배지를 부여한다.")
    @Test
    void checkGrantBadgeByOver10_000Under20_000BenefitAmount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        int sunday = 3; // -1200 -1000 = -2200
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 5); // -2023 * 5 = -10115

        // total = -12315

        // Act
        OrderInfo orderInfo = new OrderInfo(sunday, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThat(orderInfo.getBadgeName())
                .isEqualTo(Badge.TREE.getName());
    }

    @DisplayName("총혜택 금액이 20_000 이상이면 산타 배지를 부여한다.")
    @Test
    void checkGrantBadgeByOver20_000BenefitAmount() {
        // <디저트> 아이스크림(5,000)
        // Arrange
        int sunday = 3; // -1200 -1000 = -2200
        HashMap<Menu, Integer> sample = new HashMap<>();
        sample.put(Menu.ICE_CREAM, 9); // -2023 * 9 = -18207

        // total = -22207

        // Act
        OrderInfo orderInfo = new OrderInfo(sunday, sample);

        // Verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThat(orderInfo.getBadgeName())
                .isEqualTo(Badge.SANTA.getName());
    }
}
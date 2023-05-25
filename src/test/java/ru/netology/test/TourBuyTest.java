package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.var;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.HeaderPage;
import ru.netology.page.PaymentPage;
import ru.netology.util.ScreenShooterReportPortalExtension;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.util.LoggingUtils.logInfo;

@ExtendWith({ScreenShooterReportPortalExtension.class})
public class TourBuyTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void SetUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void TearDown() {
        SQLHelper.cleanDataBase();
        SelenideLogger.removeListener("allure");
    }

    //
    // ПРОВЕРКА ПОКУПКИ ДЕБЕТОВОЙ КАРТОЙ:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с валидными данными")
    public void validAcceptedPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(validInfo);
        payment.showNotification("Операция одобрена Банком.");
        logInfo("Ожидаемое оповещение системы: Операция одобрена Банком");
        logInfo("Оповещение системы: " + payment.getNotificationTest());
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getApprovedPaymentOrderStatus());
        logInfo("Ожидаемый статус заказа: approved. Фактический статус заказа: " + SQLHelper.getApprovedPaymentOrderStatus());
        assertEquals("45000", SQLHelper.getPaymentAmount());
        logInfo("Ожидаемая сумма оплаты: 45000. Фактическая сумма: " + SQLHelper.getPaymentAmount());
    }

    @Test
    @DisplayName("Попытка покупки дебетовой картой с валидными данными, но заявку отклоняет банк")
    public void validDeclinedPaymentTest() {
        var declinedInfo = DataHelper.getDeclinedInfo();
        logInfo("Данные для покупки: " + declinedInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(declinedInfo);
        payment.showNotification("Операция отклонена Банком");
        logInfo("Ожидаемое оповещение системы: Операция отклонена Банком");
        logInfo("Оповещение системы: " + payment.getNotificationTest());
        var status = DataHelper.getDeclined();
        assertEquals(status, SQLHelper.getDeclinedPaymentOrderStatus());
        logInfo("Ожидаемый статус заказа: declined. Фактический статус заказа: " + SQLHelper.getDeclinedPaymentOrderStatus());
        assertEquals("0", SQLHelper.getPaymentAmount());
        logInfo("Ожидаемая сумма оплаты: 0. Фактическая сумма: " + SQLHelper.getPaymentAmount());
    }

    //
    // Проверка пустых полей:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустого поля карты)")
    public void emptyCardFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCardPayment(validInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустого поля месяца)")
    public void emptyMonthFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyMonthPayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустого поля года)")
    public void emptyYearFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyYearPayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустого поля имени)")
    public void emptyNameFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyNamePayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустого CVC поля)")
    public void emptyCVCFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    //
    // Проверка неверной информации в номере карты:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (использование запрещенных символов в поле карты)")
    public void wrongCardInfoSymbolsPaymentTest() {
        var wrongInfo = DataHelper.getWrongCardSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (использование букв в поле карты)")
    public void wrongCardInfoLettersPaymentTest() {
        var wrongInfo = DataHelper.getWrongCardRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (слишком короткий номер карты)")
    public void wrongCardInfoLessDigitsPaymentTest() {
        var wrongInfo = DataHelper.getWrongCardLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    //
    // Проверка неверной информации в месяце:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (буквы в поле месяца)")
    public void wrongMonthWithLettersCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongMonthRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (символы в поле месяца)")
    public void wrongMonthWithSymbolsCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongMonthSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (несуществующий месяц)")
    public void wrongUnrealMonthCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongMonthUnrealDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверно указан срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Неверно указан срок действия карты");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (недостаточно цифр в поле месяца)")
    public void wrongLessDigMonthCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongMonthLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    //
    // Проверка неверной информации в году:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (срок действия карты истек)")
    public void overdueCardPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.earlyYearPayment(validInfo);
        payment.showError("Истёк срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Истёк срок действия карты");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (срок действия карты нереалистичный)")
    public void unrealYearCardPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.laterYearPayment(validInfo);
        payment.showError("Неверно указан срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Неверно указан срок действия карты");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (символы в поле года)")
    public void wrongYearWithSymbolsCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongYearSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (буквы в поле года)")
    public void wrongYearWithLettersCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongYearRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (одна цифра в поле года)")
    public void wrongYearLessDigCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongYearLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    //
    // Проверка неверной информации в имени:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (одна буква в поле имени)")
    public void wrongNameLessLettersCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongNameOneInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (большое количество букв в поле имени)")
    public void wrongNameMoreLettersCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongNameLongInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (имя на английском)")
    public void wrongEngNameCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongNameEngInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (символы в поле имени)")
    public void wrongSymbolsNameCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongNameSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (цифры в поле имени)")
    public void wrongDigNameCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongNameDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с валидными данными (двойное имя)")
    public void wrongDoubleNameCardPaymentTest() {
        var validInfo = DataHelper.getValidNameDoubleInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(validInfo);
        payment.showNotification("Операция одобрена Банком.");
        logInfo("Ожидаемое оповещение системы: Операция одобрена Банком");
        logInfo("Оповещение системы: " + payment.getNotificationTest());
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getApprovedPaymentOrderStatus());
        logInfo("Ожидаемый статус заказа: approved. Фактический статус заказа: " + SQLHelper.getApprovedPaymentOrderStatus());
        assertEquals("45000", SQLHelper.getPaymentAmount());
        logInfo("Ожидаемая сумма оплаты: 45000. Фактическая сумма: " + SQLHelper.getPaymentAmount());
    }

    //
    // Проверка неверной информации в CVC:
    //
    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (символы в поле CVC)")
    public void wrongCVCWithSymbolsCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongCVCSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (буквы в поле CVC)")
    public void wrongCVCWithLettersCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongCVCLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (недостаточно цифр в поле CVC)")
    public void wrongLessDigCVCCardPaymentTest() {
        var wrongInfo = DataHelper.getWrongCVCLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(wrongInfo);
        payment.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + payment.getErrorTest());
    }

    //
    // ПРОВЕРКА ПОКУПКИ ТУРА В КРЕДИТ:
    //
    @Test
    @DisplayName("Покупка в кредит с валидными данными")
    public void validAcceptedCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для кредита: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(validInfo);
        credit.showNotification("Операция одобрена Банком.");
        logInfo("Ожидаемое оповещение системы: Операция одобрена Банком");
        logInfo("Оповещение системы: " + credit.getNotificationTest());
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getApprovedCreditOrderStatus());
        logInfo("Ожидаемый статус заказа: approved. Фактический статус заказа: " + SQLHelper.getApprovedCreditOrderStatus());
    }

    @Test
    @DisplayName("Попытка покупки в кредит с валидными данными, но заявку отклоняет банк")
    public void validDeclinedCreditTest() {
        var declinedInfo = DataHelper.getDeclinedInfo();
        logInfo("Данные для кредита: " + declinedInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(declinedInfo);
        credit.showNotification("Операция отклонена Банком.");
        logInfo("Ожидаемое оповещение системы: Операция отклонена Банком");
        logInfo("Оповещение системы: " + credit.getNotificationTest());
        var status = DataHelper.getDeclined();
        assertEquals(status, SQLHelper.getDeclinedCreditOrderStatus());
        logInfo("Ожидаемый статус заказа: declined. Фактический статус заказа: " + SQLHelper.getDeclinedCreditOrderStatus());
    }

    //
    // Проверка пустых полей:
    //
    @Test
    @DisplayName("Попытка покупки в кредит с невалидными данными (наличие пустого поля карты)")
    public void emptyCardFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCardCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Попытка покупки в кредит с невалидными данными (наличие пустого поля месяца)")
    public void emptyMonthFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyMonthCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Попытка покупки в кредит с невалидными данными (наличие пустого поля года)")
    public void emptyYearFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyYearCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Попытка покупки в кредит с невалидными данными (наличие пустого поля имени)")
    public void emptyNameFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyNameCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (наличие пустых полей в форме оплаты)")
    public void emptyFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для кредита: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
        logInfo("Ожидаемое сообщение об ошибке: Поле обязательно для заполнения");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    //
    // Проверка неверной информации в номере карты:
    //
    @Test
    @DisplayName("Покупка в кредит с невалидными данными (использование запрещенных символов в поле карты)")
    public void wrongCardInfoSymbolsCreditTest() {
        var wrongInfo = DataHelper.getWrongCardSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (использование букв в поле карты)")
    public void wrongCardInfoLettersCreditTest() {
        var wrongInfo = DataHelper.getWrongCardRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (слишком короткий номер карты)")
    public void wrongCardInfoLessDigitsCreditTest() {
        var wrongInfo = DataHelper.getWrongCardLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    //
    // Проверка неверной информации в месяце:
    //
    @Test
    @DisplayName("Покупка в кредит с невалидными данными (буквы в поле месяца)")
    public void wrongMonthWithLettersCardCreditTest() {
        var wrongInfo = DataHelper.getWrongMonthRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (символы в поле месяца)")
    public void wrongMonthWithSymbolsCardCreditTest() {
        var wrongInfo = DataHelper.getWrongMonthSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (несуществующий месяц)")
    public void wrongUnrealMonthCardCreditTest() {
        var wrongInfo = DataHelper.getWrongMonthUnrealDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверно указан срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Неверно указан срок действия карты");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (недостаточно цифр в поле месяца)")
    public void wrongLessDigMonthCardCreditTest() {
        var wrongInfo = DataHelper.getWrongMonthLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    //
    // Проверка неверной информации в году:
    //
    @Test
    @DisplayName("Покупка в кредит с невалидными данными (срок действия карты истек)")
    public void overdueCardCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.earlyYearCredit(validInfo);
        credit.showError("Истёк срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Истёк срок действия карты");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (срок действия карты нереалистичный)")
    public void unrealYearCardCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.laterYearCredit(validInfo);
        credit.showError("Неверно указан срок действия карты");
        logInfo("Ожидаемое сообщение об ошибке: Неверно указан срок действия карты");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (символы в поле года)")
    public void wrongYearWithSymbolsCardCreditTest() {
        var wrongInfo = DataHelper.getWrongYearSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (буквы в поле года)")
    public void wrongYearWithLettersCardCreditTest() {
        var wrongInfo = DataHelper.getWrongYearRusLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (буквы в поле года)")
    public void wrongYearLessDigCardCreditTest() {
        var wrongInfo = DataHelper.getWrongYearLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    //
    // Проверка неверной информации в имени:
    //
    @Test
    @DisplayName("Покупка в кредит с невалидными данными (одна буква в поле имени)")
    public void wrongNameLessLettersCardCreditTest() {
        var wrongInfo = DataHelper.getWrongNameOneInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (большое количество букв в поле имени)")
    public void wrongNameMoreLettersCardCreditTest() {
        var wrongInfo = DataHelper.getWrongNameLongInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (имя на английском)")
    public void wrongEngNameCardCreditTest() {
        var wrongInfo = DataHelper.getWrongNameEngInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (символы в поле имени)")
    public void wrongSymbolsNameCardCreditTest() {
        var wrongInfo = DataHelper.getWrongNameSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (цифры в поле имени)")
    public void wrongDigNameCardCreditTest() {
        var wrongInfo = DataHelper.getWrongNameDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с валидными данными (двойное имя)")
    public void wrongDoubleNameCardCreditTest() {
        var validInfo = DataHelper.getValidNameDoubleInfo();
        logInfo("Данные для покупки: " + validInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(validInfo);
        credit.showNotification("Операция одобрена Банком.");
        logInfo("Ожидаемое оповещение системы: Операция одобрена Банком");
        logInfo("Оповещение системы: " + credit.getNotificationTest());
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getApprovedCreditOrderStatus());
        logInfo("Ожидаемый статус заказа: approved. Фактический статус заказа: " + SQLHelper.getApprovedCreditOrderStatus());
    }

    //
    // Проверка неверной информации в CVC:
    //
    @Test
    @DisplayName("Покупка в кредит с невалидными данными (символы в поле CVC)")
    public void wrongCVCWithSymbolsCardCreditTest() {
        var wrongInfo = DataHelper.getWrongCVCSymbolsInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (буквы в поле CVC)")
    public void wrongCVCWithLettersCardCreditTest() {
        var wrongInfo = DataHelper.getWrongCVCLettersInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (недостаточно цифр в поле CVC)")
    public void wrongLessDigCVCCardCreditTest() {
        var wrongInfo = DataHelper.getWrongCVCLessDigInfo();
        logInfo("Данные для покупки: " + wrongInfo);
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(wrongInfo);
        credit.showError("Неверный формат");
        logInfo("Ожидаемое сообщение об ошибке: Неверный формат");
        logInfo("Сообщение об ошибке: " + credit.getErrorTest());
    }
}

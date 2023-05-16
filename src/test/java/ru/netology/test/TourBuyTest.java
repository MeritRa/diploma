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

    @Test
    @DisplayName("Покупка дебетовой картой с валидными данными")
    public void validAcceptedPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(validInfo);
        payment.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getPaymentOrderStatus());
    }

    @Test
    @DisplayName("Попытка покупки дебетовой картой с валидными данными, но заявку отклоняет банк")
    public void validDeclinedPaymentTest() {
        var declinedInfo = DataHelper.getDeclinedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(declinedInfo);
        payment.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getDeclined();
        assertEquals(status, SQLHelper.getPaymentOrderStatus());
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (наличие пустых полей в форме оплаты)")
    public void emptyFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (использование запрещенных символов в форме)")
    public void wrongInfoPaymentTest() {
        var wrongInfo = DataHelper.getWrongInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(wrongInfo);
        payment.showError("Неверный формат");
    }

    @Test
    @DisplayName("Покупка дебетовой картой с невалидными данными (срок действия карты истек)")
    public void overdueCardPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.earlyYearPayment(validInfo);
        payment.showError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Покупка в кредит с валидными данными")
    public void validAcceptedCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(validInfo);
        credit.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getCreditOrderStatus());
    }

    @Test
    @DisplayName("Попытка покупки в кредит с валидными данными, но заявку отклоняет банк")
    public void validDeclinedCreditTest() {
        var declinedInfo = DataHelper.getDeclinedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.fullInfoCredit(declinedInfo);
        credit.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getDeclined();
        assertEquals(status, SQLHelper.getCreditOrderStatus());
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (наличие пустых полей в форме оплаты)")
    public void emptyFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (использование запрещенных символов в форме)")
    public void wrongInfoCreditTest() {
        var wrongInfo = DataHelper.getWrongInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(wrongInfo);
        credit.showError("Неверный формат");
    }

    @Test
    @DisplayName("Покупка в кредит с невалидными данными (срок действия карты истек)")
    public void overdueCardCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.earlyYearCredit(validInfo);
        credit.showError("Истёк срок действия карты");
    }
}
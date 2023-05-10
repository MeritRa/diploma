package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.HeaderPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourBuyTest {
    @BeforeEach
    void SetUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void TearDown() {
        SQLHelper.cleanDataBase();
    }

    @Test
    public void validAcceptedPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(validInfo);
        payment.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getApproved();
        assertEquals(status, SQLHelper.getCreditOrderStatus());
    }

    @Test
    public void validDeclinedPaymentTest() {
        var declinedInfo = DataHelper.getDeclinedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.fullInfoPayment(declinedInfo);
        payment.showNotification("Операция одобрена Банком.");
        var status = DataHelper.getDeclined();
        assertEquals(status, SQLHelper.getCreditOrderStatus());
    }

    @Test
    public void emptyFieldPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(validInfo);
        payment.showError("Поле обязательно для заполнения");
    }

    @Test
    public void wrongInfoPaymentTest() {
        var wrongInfo = DataHelper.getWrongInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.emptyCvcPayment(wrongInfo);
        payment.showError("Неверный формат");
    }

    @Test
    public void overdueCardPaymentTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.choosePaymentPath();
        PaymentPage payment = new PaymentPage();
        payment.earlyYearPayment(validInfo);
        payment.showError("Истёк срок действия карты");
    }

    @Test
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
    public void emptyFieldCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(validInfo);
        credit.showError("Поле обязательно для заполнения");
    }

    @Test
    public void wrongInfoCreditTest() {
        var wrongInfo = DataHelper.getWrongInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.emptyCvcCredit(wrongInfo);
        credit.showError("Неверный формат");
    }

    @Test
    public void overdueCardCreditTest() {
        var validInfo = DataHelper.getApprovedInfo();
        var header = new HeaderPage();
        header.chooseCreditPath();
        CreditPage credit = new CreditPage();
        credit.earlyYearCredit(validInfo);
        credit.showError("Истёк срок действия карты");
    }
}

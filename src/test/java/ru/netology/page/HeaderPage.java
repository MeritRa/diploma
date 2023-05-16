package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


public class HeaderPage {
    private final SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));
    private final SelenideElement payButton = $$("button").find(exactText("Купить"));

    public HeaderPage() {
    }

    public PaymentPage choosePaymentPath() {
        payButton.click();
        return new PaymentPage();
    }

    public CreditPage chooseCreditPath() {
        creditButton.click();
        return new CreditPage();
    }
}
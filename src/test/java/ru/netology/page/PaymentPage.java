package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private final SelenideElement paymentField = $$("h3").find(exactText("Оплата по карте"));
    ;
    private final SelenideElement cardField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement nameField = $$("input").get(3);
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement button = $$("button").find(Condition.exactText("Продолжить"));
    private final SelenideElement notification = $("[class='notification__content']");
    private final SelenideElement error = $(".input_invalid .input__sub");

    public PaymentPage() {
        paymentField
                .shouldBe(visible);
    }

    public void fullInfoPayment(DataHelper.CardInfo info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.setValue(info.getName());
        cvcField.setValue(info.getCVC());
        button.click();
    }

    public void emptyCvcPayment(DataHelper.CardInfo info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.setValue(info.getName());
        button.click();
        ;
    }

    public void earlyYearPayment(DataHelper.CardInfo info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(DataHelper.getEarlyYear());
        nameField.setValue(info.getName());
        cvcField.setValue(info.getCVC());
        button.click();
    }

    public void showNotification(String value) {
        notification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText(value));
    }

    public void showError(String value) {
        error
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText(value));
    }

    public String getNotificationTest() {
        return notification.getText();
    }

    public String getErrorTest() {
        return error.getText();
    }
}

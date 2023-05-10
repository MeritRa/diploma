package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private final SelenideElement creditField = $$("h3").find(exactText("Кредит по данным карты"));
    private final SelenideElement cardField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement nameField = $$("input").get(3);
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement button = $$("button").find(exactText("Продолжить"));
    private final SelenideElement notification = $("[class='notification__content']");
    private final SelenideElement error = $(".input_invalid .input__sub");

    public CreditPage() {
        creditField
                .shouldBe(Condition.visible);
    }

    public void fullInfoCredit(DataHelper.CardInfo info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.setValue(info.getName());
        cvcField.setValue(info.getCVC());
        button.click();
    }

    public void emptyCvcCredit(DataHelper.CardInfo info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.setValue(info.getName());
        button.click();
    }

    public void earlyYearCredit(DataHelper.CardInfo info) {
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
                .shouldHave(exactText(value));
    }

    public void showError(String value) {
        error
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText(value));
    }
}

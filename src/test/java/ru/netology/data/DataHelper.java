package ru.netology.data;

import com.codeborne.selenide.conditions.Or;
import com.github.javafaker.Faker;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Value;
import lombok.var;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    static Faker faker = new Faker(new Locale("RU"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String name;
        String CVC;
    }

    @Value
    public static class OrderStatus {
        String status;
    }
    public static OrderStatus getApproved() {
        return new OrderStatus("APPROVED");
    }
    public static OrderStatus getDeclined() {
        return new OrderStatus("DECLINED");
    }
    public static String generateMonth() {
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        var randomIndex = Math.floor(Math.random() * (months.length - 1));
        return months[(int) randomIndex];
    }

    public static String generateYear() {
        int random = ThreadLocalRandom.current().nextInt(1, 5);
        LocalDate today = LocalDate.now().plusYears(random);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy");
        return today.format(dateTimeFormatter);
    }

    public static String getEarlyYear() {
        int random = ThreadLocalRandom.current().nextInt(1, 5);
        LocalDate today = LocalDate.now().minusYears(random);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy");
        return today.format(dateTimeFormatter);
    }

    public static CardInfo getApprovedInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getDeclinedInfo() {
        return new CardInfo("4444444444444442", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongInfo() {
        return new CardInfo("!@#$%!^&!3232hgfh", "00", "00", "John_Smith", "000");
    }
}
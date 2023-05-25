package ru.netology.data;

import com.github.javafaker.Faker;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Value;
import lombok.var;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public static String getLaterYear() {
        int random = ThreadLocalRandom.current().nextInt(6, 25);
        LocalDate today = LocalDate.now().plusYears(random);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy");
        return today.format(dateTimeFormatter);
    }

    public static CardInfo getApprovedInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getDeclinedInfo() {
        return new CardInfo("4444444444444442", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongCardSymbolsInfo() {
        return new CardInfo("!@#$%!^&!!!!!!!!", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongCardRusLettersInfo() {
        return new CardInfo("аывавыавакаввакы", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongCardLessDigInfo() {
        return new CardInfo("123456789112345", generateMonth(), generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongMonthLessDigInfo() {
        return new CardInfo("4444444444444441", "1", generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongMonthUnrealDigInfo() {
        return new CardInfo("4444444444444441", "13", generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongMonthRusLettersInfo() {
        return new CardInfo("4444444444444441", "ап", generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongMonthSymbolsInfo() {
        return new CardInfo("4444444444444441", "!@", generateYear(), String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongYearLessDigInfo() {
        return new CardInfo("4444444444444441", generateMonth(), "1", String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongYearRusLettersInfo() {
        return new CardInfo("4444444444444441", generateMonth(), "вы", String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongYearSymbolsInfo() {
        return new CardInfo("4444444444444441", generateMonth(), "!№", String.valueOf(faker.name()), String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongCVCLessDigInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), String.valueOf(faker.name()), "2");
    }

    public static CardInfo getWrongCVCLettersInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), String.valueOf(faker.name()), "аве");
    }

    public static CardInfo getWrongCVCSymbolsInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), String.valueOf(faker.name()), "аве");
    }

    public static CardInfo getWrongNameDigInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "43242352352 242423", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongNameSymbolsInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "!!!!@@#$!@$ @#!#!", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongNameOneInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "А", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getValidNameDoubleInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "Воронцов-Дашков Иван", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongNameEngInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "John Smith", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }

    public static CardInfo getWrongNameLongInfo() {
        return new CardInfo("4444444444444441", generateMonth(), generateYear(), "ааывввввввввввввввввввввввввааыввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввааыввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввпппппппппппппппппп", String.valueOf(ThreadLocalRandom.current().nextInt(100, 999)));
    }
}
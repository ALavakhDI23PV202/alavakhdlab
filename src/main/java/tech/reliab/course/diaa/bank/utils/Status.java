package main.java.tech.reliab.course.diaa.bank.utils;

public enum Status {
    NOT_WORKING("Не работает"),
    WORKING("Работает"),
    NO_MONEY("Нет денег");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


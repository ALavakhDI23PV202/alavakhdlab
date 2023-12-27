package main.java.tech.reliab.course.diaa.bank.exception;

public class NotEnoughMoneyException extends Exception {
	public NotEnoughMoneyException() {
		super("Ошибка! Недостаточно денег");
	}
}

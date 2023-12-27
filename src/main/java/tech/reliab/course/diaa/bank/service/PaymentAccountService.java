package main.java.tech.reliab.course.diaa.bank.service;

import main.java.tech.reliab.course.diaa.bank.exception.NotEnoughMoneyException;
import main.java.tech.reliab.course.diaa.bank.exception.NotUniqueIdException;
import main.java.tech.reliab.course.diaa.bank.entity.PaymentAccount;

import java.util.List;

public interface PaymentAccountService {

	/**
	 * Создание платежного аккаунта
	 **/
	PaymentAccount create(PaymentAccount paymentAccount) throws NotUniqueIdException;

	/**
	 * Добавление платежного аккаунта
     * Логика добавления передается userService
	 **/
	PaymentAccount addPaymentAccount(PaymentAccount paymentAccount) throws NotUniqueIdException;

	/**
	 * Получение платежного аккаунта по id
	 **/
	PaymentAccount getPaymentAccountById(int paymentAccountId);

	/**
	 * Удаление платежного аккаунта
     * Логика удаления передается userService
	 **/
	boolean deletePaymentAccountById(int paymentAccountId);

	/**
	 * Получение всех платежных аккаунтов
	 **/
	List<PaymentAccount> getAllPaymentAccounts();

	/**
	Внести деньги на платежный счет.
	**/
	void depositMoney(PaymentAccount account, double sum);

    /**
    Снять деньги с платежного счета.
    В операции может быть отказано, если на счету недостаточно денег.
    **/

	void withdrawMoney(PaymentAccount account, double sum) throws NotEnoughMoneyException;

	List<PaymentAccount> getAllPaymentAccountByIdUser(int userId);

	String read(int userId);
}

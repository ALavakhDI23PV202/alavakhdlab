package main.java.tech.reliab.course.diaa.bank;

import  main.java.tech.reliab.course.diaa.bank.utils.Status;
import main.java.tech.reliab.course.diaa.bank.entity.*;
import main.java.tech.reliab.course.diaa.bank.service.*;
import main.java.tech.reliab.course.diaa.bank.service.impl.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankServiceImpl();
        Bank bank = bankService.create(new Bank(1, "банк №1"));
        System.out.println(bank);


        BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
        BankOffice bankOffice = bankOfficeService.create(new BankOffice(1, "офис №1", "Костюкова 46",
                bank, true, true, true, true, true,
                10000, 200));
        System.out.println(bankOffice);


        EmployeeService employeeService = new EmployeeServiceImpl();
        Employee employee = employeeService.create(new Employee(1, "Сотрудник №1", LocalDate.of(2000, 01, 01),
                        "Работа №1", true, bankOffice, true, 1000));
        System.out.println(employee);


        AtmService atmService = new AtmServiceImpl();
        BankAtm bankAtm = atmService.create(new BankAtm(1, "банкомат №1", Status.WORKING, true,
                true, 5000, 200, bankOffice));
        System.out.println(bankAtm);


        UserService userService = new UserServiceImpl();
        User user = userService.create(new User(1, "пользователя №1", LocalDate.of(2001, 2,
                9), 9000, "Костюкова 46", bank));
        System.out.println(user);


        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl();
        PaymentAccount paymentAccount = paymentAccountService.create(new PaymentAccount(1, user, bank, 4000));
        System.out.println(paymentAccount);


        CreditAccountService creditAccountService = new CreditAccountServiceImpl();
        CreditAccount creditAccount = creditAccountService.create(new CreditAccount(1, user, bank,
                LocalDate.of(2001, 2, 9), 4, 1000, employee, paymentAccount));
        System.out.println(creditAccount);

        System.out.println(bank);

    }
}
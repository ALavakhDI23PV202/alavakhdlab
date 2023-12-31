package main.java.tech.reliab.course.diaa.bank.service.impl;

import main.java.tech.reliab.course.diaa.bank.entity.Bank;
import main.java.tech.reliab.course.diaa.bank.entity.BankAtm;
import main.java.tech.reliab.course.diaa.bank.entity.BankOffice;
import main.java.tech.reliab.course.diaa.bank.service.AtmService;
import main.java.tech.reliab.course.diaa.bank.service.BankOfficeService;
import main.java.tech.reliab.course.diaa.bank.utils.Status;

public class AtmServiceImpl implements AtmService {

    @Override
    public BankAtm create(BankAtm bankAtm) {
        if (bankAtm != null) {

            if (bankAtm.getId() < 0) {
                System.out.println("Ошибка! ID не может быть отрицательным числом!");
                return null;
            }

            if (bankAtm.getMoney() < 0) {
                System.out.println("Ошибка! Кол-во денег не может быть отрицательным числом!");
                return null;
            }

            if (bankAtm.getServicePrice() < 0) {
                System.out.println("Ошибка! Стоимость обслуживания не может быть отрицательным числом!");
                return null;
            }

            if (bankAtm.getBankOffice() == null) {
                System.out.println("Ошибка! Нельзя создать банкомат без офиса!");
                return null;
            }

            BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
            if (bankOfficeService.installAtm(bankAtm.getBankOffice(), bankAtm)) {
                return new BankAtm(bankAtm);
            }
        }

        return null;
    }

    @Override
    public void depositMoney(BankAtm bankAtm, double sum) {
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {

            if (bankAtm.getStatus() != Status.NOT_WORKING) {
                if (bankAtm.getIsPayInMoney()) {
                    BankOffice office = bankAtm.getBankOffice();
                    Bank bank = bankAtm.getBank();
                    double newSum = bankAtm.getMoney() + sum;

                    bankAtm.setMoney(newSum);
                    office.setMoney(office.getMoney() + newSum);
                    bank.setMoney(bank.getMoney() + newSum);
                } else {
                    System.out.println("В банкомат " + bankAtm.getName() + " нельзя вносить деньги\n");
                }
            } else {
                System.out.println("Банкомат " + bankAtm.getName() + " не работает\n");
            }
        }
    }

    @Override
    public void withdrawMoney(BankAtm bankAtm, double sum) {
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {

            if (bankAtm.getStatus() == Status.WORKING) {
                if (bankAtm.getIsGiveMoney()) {
                    if (bankAtm.getMoney() >= sum) {

                        BankOffice office = bankAtm.getBankOffice();
                        Bank bank = bankAtm.getBank();
                        double newSum = bankAtm.getMoney() - sum;

                        bankAtm.setMoney(newSum);
                        office.setMoney(office.getMoney() + newSum);
                        bank.setMoney(bank.getMoney() + newSum);
                    } else {
                        System.out.println("В банкомате" + bankAtm.getName() + " недостаточно денег для выдачи\n");
                    }
                } else {
                    System.out.println("Банкомат " + bankAtm.getName() + " не работает на выдачу денег\n");
                }
            }
        }
    }
}

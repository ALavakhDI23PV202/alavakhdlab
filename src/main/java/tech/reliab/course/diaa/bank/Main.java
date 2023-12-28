package main.java.tech.reliab.course.diaa.bank;

import main.java.tech.reliab.course.diaa.bank.utils.Status;
import main.java.tech.reliab.course.diaa.bank.entity.*;
import main.java.tech.reliab.course.diaa.bank.exception.CreditException;
import main.java.tech.reliab.course.diaa.bank.exception.NotFoundException;
import main.java.tech.reliab.course.diaa.bank.exception.NotUniqueIdException;
import main.java.tech.reliab.course.diaa.bank.service.impl.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws NotUniqueIdException, NotFoundException, IOException {
		BankServiceImpl bankService = BankServiceImpl.getInstance();
		BankOfficeServiceImpl bankOfficeService = BankOfficeServiceImpl.getInstance();
		AtmServiceImpl atmService = AtmServiceImpl.getInstance();
		UserServiceImpl userService = UserServiceImpl.getInstance();
		EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
		PaymentAccountServiceImpl paymentAccountService = PaymentAccountServiceImpl.getInstance();
		CreditAccountServiceImpl creditAccountService = CreditAccountServiceImpl.getInstance();

		try {
			bankService.create(new Bank(1, "БАНК№1"));
			bankService.create(new Bank(2, "БАНК№2"));
			bankService.create(new Bank(3, "БАНК№3"));
			bankService.create(new Bank(4, "БАНК№4"));
			bankService.create(new Bank(5, "БАНК№5"));

			// Создание по 3 офиса в каждом банке
			List<Bank> banks = bankService.getAllBanks();
			Random random = new Random();
			int id = 1;
			for (Bank bank : banks) {
				bankOfficeService.create(new BankOffice(
						id, "Офис " + id + " банка " + bank.getName(), "КОСЮКОВА 46 " + id, bank, true,
						true, true, true, true, random.nextInt(1000) + 1000,
						random.nextInt(100) + 50));
				id += 1;

				bankOfficeService.create(new BankOffice(
						id, "Офис " + id + " банка " + bank.getName(), "КОСЮКОВА 46 " + id, bank, true,
						true, true, false, true, random.nextInt(4000) + 1000,
						random.nextInt(100) + 30));
				id += 1;

				bankOfficeService.create(new BankOffice(
						id, "Офис " + id + " банка " + bank.getName(), "КОСЮКОВА 46 " + id, bank, true,
						true, true, true, true, random.nextInt(3000) + 1000,
						random.nextInt(100) + 200));
				id += 1;
			}

			// Создание по 2 банкомата в каждом офисе
			List<BankOffice> bankOffices = bankOfficeService.getAllBankOffice();
			id = 1;
			for (BankOffice bankOffice : bankOffices) {
				atmService.create(new BankAtm(
						id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.WORK, true,
						true, random.nextInt(1000) + 500, random.nextInt(100) + 100, bankOffice));

				id += 1;

				atmService.create(new BankAtm(
						id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.WORK, true,
						true, random.nextInt(1000) + 500, random.nextInt(100) + 100, bankOffice));

				id += 1;
			}

			// Создание по 5 работников в каждом офисе
			id = 1;
			for (BankOffice bankOffice : bankOffices) {
				employeeService.create(new Employee(
						id, "Работник " + id + " офиса " + bankOffice.getName(),
						LocalDate.of(2001, random.nextInt(11) + 1, random.nextInt(27) + 1),
						"должность", true, bankOffice, true, random.nextInt(5000) + 1000));
				id += 1;

				employeeService.create(new Employee(
						id, "Работник " + id + " офиса " + bankOffice.getName(),
						LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
						"должность", true, bankOffice, true, random.nextInt(3000) + 1000));
				id += 1;

				employeeService.create(new Employee(
						id, "Работник " + id + " офиса " + bankOffice.getName(),
						LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
						"должность", false, bankOffice, true, random.nextInt(2000) + 3000));
				id += 1;

				employeeService.create(new Employee(
						id, "Работник " + id + " офиса " + bankOffice.getName(),
						LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
						"должность", false, bankOffice, true, random.nextInt(5000) + 1000));
				id += 1;

				employeeService.create(new Employee(
						id, "Работник " + id + " офиса " + bankOffice.getName(),
						LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
						"должность", true, bankOffice, true, random.nextInt(5000) + 2000));
				id += 1;
			}

			// Создание по 2 пользователя в банке
			id = 1;
			for (Bank bank : banks) {
				userService.create(new User(
						id, "Пользователь " + id,
						LocalDate.of(2004, random.nextInt(11) + 1,
								random.nextInt(27) + 1), random.nextInt(3000) + 3000,
						"адрес работы", bank));

				id += 1;

				userService.create(new User(
						id, "Пользователь " + id,
						LocalDate.of(2004, random.nextInt(11) + 1,
								random.nextInt(27) + 1), random.nextInt(3000) + 3000,
						"адрес работы", bank));
				id += 1;
			}

			List<User> users = userService.getAllUsers();

			// Создание по 2 платежных аккаунта у каждого пользователя в каждом банке
			id = 1;
			for (User user : users) {
				for (Bank bank : banks) {
					paymentAccountService.create(new PaymentAccount(id, user, bank, random.nextInt(4000) + 2000));
					id += 1;
					paymentAccountService.create(new PaymentAccount(id, user, bank, random.nextInt(4000) + 2000));
					id += 1;
				}
			}

			// Создание по 1 кредитных аккаунта у каждого пользователя в каждом банке
			id = 1;
			for (User user : users) {
				List<PaymentAccount> paymentAccounts = paymentAccountService.getAllPaymentAccountByIdUser(user.getId());
				for (BankOffice bankOffice : bankOffices) {
					List<Employee> employees = employeeService.getAllEmployeeByIdBankOffice(bankOffice.getId());
					creditAccountService.create(new CreditAccount(id, user, bankOffice.getBank(), bankOffice,
							LocalDate.now(), 2, random.nextInt(1000) + 200,
							employeeService.getEmployeeById(random.nextInt(employees.size() + 1)),
							paymentAccountService.getPaymentAccountById(random.nextInt(paymentAccounts.size() + 1))));
					id += 1;
				}
			}

		} catch (NotFoundException | NotUniqueIdException e) {
			System.out.println(e.getMessage());
		} catch (CreditException e) {
			throw new RuntimeException(e);
		}

		try (Scanner in = new Scanner(System.in)) {

			// Опция вывода информации о банке
			List<Bank> banksList = bankService.getAllBanks();
			StringBuilder bankOption = new StringBuilder("************************************\n");
			bankOption.append("Введите id банка для вывода подробной информации\n");
			bankOption.append("Введите -1 для выхода\n");
			bankOption.append("ID существующих банков: ");
			for (Bank bank : banksList) {
				bankOption.append(bank.getId()).append("  ");
			}
			bankOption.append("\n************************************\n");
			System.out.println(bankOption);

			int inputValue = in.nextInt();
			while (inputValue != -1) {
				System.out.println(bankService.read(inputValue));
				System.out.println(bankOption);
				inputValue = in.nextInt();
			}

			// Опция вывода информации о счетах пользователя
			List<User> usersList = userService.getAllUsers();
			StringBuilder userOption = new StringBuilder("************************************\n");
			userOption.append("Введите id пользователя для вывода подробной информации\n");
			userOption.append("Введите -1 для выхода\n");
			userOption.append("ID существующих пользователей: ");
			for (User user : usersList) {
				userOption.append(user.getId()).append("  ");
			}
			userOption.append("\n************************************\n");
			System.out.println(userOption);

			inputValue = in.nextInt();
			while (inputValue != -1) {
				System.out.println(userService.read(inputValue));
				System.out.println(userOption);
				inputValue = in.nextInt();
			}
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Взятие кредита
		try (Scanner in = new Scanner(System.in)) {
			List<User> usersList = userService.getAllUsers();
			StringBuilder creditOption = new StringBuilder("************************************\n");
			creditOption.append("Введите id пользователя, от лица которого хотите взять кредит\n");
			creditOption.append("Введите -1 для выхода\n");
			creditOption.append("ID существующих пользователей: ");
			for (User user : usersList) {
				creditOption.append(user.getId()).append("  ");
			}
			creditOption.append("\n************************************\n");
			System.out.println(creditOption);
			int idAccount = 0;
			int idUser = in.nextInt();
			while (idUser != -1) {
				System.out.print("Введите сумму кредита в рублях: ");
				double sum = in.nextDouble();
				System.out.print("Введите период взятия кредита в месяцах: ");
				int countMonth = in.nextInt();

				List<Bank> suitableBanks = bankService.getBanksSuitable(sum, countMonth);

				System.out.print("ID подходящих банков для взятия кредита: ");
				StringBuilder str = new StringBuilder();
				for (Bank bank : suitableBanks) {
					str.append(bank.getId()).append(" ");
				}
				System.out.println(str);
				System.out.println("Введите id банка");
				int idBank = in.nextInt();
				Bank bank = bankService.getBankById(idBank);
				BankOffice bankOffice = bankService.getBankOfficeSuitableInBank(bank, sum).get(0);
				Employee employee = bankOfficeService.getSuitableEmployeeInOffice(bankOffice).get(0);
				PaymentAccount paymentAccount = userService.getBestPaymentAccountByUserID(idUser);
				CreditAccount creditAccount = creditAccountService.create(new CreditAccount(
						idAccount, userService.getUserById(idUser), bank, bankOffice, LocalDate.now(), countMonth,
						sum, employee, paymentAccount));

				System.out.println(creditAccount);
				System.out.println(creditOption);
				idUser = in.nextInt();
				idAccount += 1;
			}
		} catch (CreditException e) {
			System.out.println(e.getMessage());
		}

		// Опция записи в файл информации о счетах пользователя в выбранном банке
		try (Scanner in = new Scanner(System.in)) {

			List<User> usersList = userService.getAllUsers();
			StringBuilder userOption = new StringBuilder("\n************************************\n");
			userOption.append("Введите id пользователя для записи информации о его счетах в файл\n");
			userOption.append("Введите -1 для выхода\n");
			userOption.append("ID существующих пользователей: ");
			for (User user : usersList) {
				userOption.append(user.getId()).append("  ");
			}

			userOption.append("\n************************************\n");
			System.out.println(userOption);
			int userId = in.nextInt();
			while (userId != -1) {
				System.out.println("Введите имя файла, в который необходимо записать счета : ");
				String fileName = in.nextLine();
				fileName = in.nextLine();
				StringBuilder bankOption = new StringBuilder("Введите банк, счета в котором надо записать в файл\n");
				bankOption.append("ID существующих банков: ");
				List<Bank> banks = userService.getAllBanksByIdUser(userId);
				for (Bank bank : banks) {
					bankOption.append(bank.getId() + " ");
				}
				System.out.println(bankOption);
				int bankId = in.nextInt();
				userService.saveToFileByUserId(fileName, bankId, userId);

				System.out.println(userOption);
				userId = in.nextInt();
			}

			System.out.println("Введите имя файла из которого хотите перенести счет : ");
			System.out.println("Для выхода введите -1 ");
			in.nextLine();
			var fileName = in.nextLine();
			while (!Objects.equals(fileName, "-1")) {
				System.out.println("Введите имя файла в который хотите перенести счет : ");
				var fileNameNew = in.nextLine();

				System.out.println("Введите id пользователя счета которого хотите перенести : ");
				userId = in.nextInt();

				System.out.println("Введите id банка в который хотите перенести счет : ");
				var bankId = in.nextInt();
				System.out.println("Введите номер платежного счета, который хотите перенести\nесли переносить платежный счет не нужно введите -1 : ");
				var payAccId = in.nextInt();
				System.out.println("Введите номер кредитного счета, который хотите перенести\n если переносить кредитный счет не нужно введите -1 : ");
				var creditAccId = in.nextInt();
				userService.transfer(fileName, bankId, creditAccId, payAccId);
				userService.saveToFileByUserId(fileNameNew, bankId, userId);
				System.out.println("********************************************************: ");
				System.out.println("Введите -1 для выхода : ");
				System.out.println("Введите имя файла из которого хотите перенести счет : ");
				in.nextLine();
				fileName = in.nextLine();
			}
		}
	}
}

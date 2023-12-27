package main.java.tech.reliab.course.diaa.bank.service;

import main.java.tech.reliab.course.diaa.bank.entity.BankOffice;
import main.java.tech.reliab.course.diaa.bank.entity.Employee;

public interface EmployeeService {
    // Создание работника
    Employee create(Employee employee);

    /*
    Перевод работника в другой офис.
    При этом, если работника переводят в офис другого банка, количество работников соответствующих офисов меняется.
     */
    void transferEmployee(Employee employee, BankOffice bankOffice);
}

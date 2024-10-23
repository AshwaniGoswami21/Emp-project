package org.Ashwani.Emp_Project.Service;

import jakarta.servlet.http.HttpSession;
import org.Ashwani.Emp_Project.Model.Employee;

import java.util.List;

public interface EmployeeService {
    boolean createEmployee(Employee employee, HttpSession session);
    Employee readEmployee(Long id);
    List<Employee> readEmployees(HttpSession httpSession);
    boolean deleteEmployee(Long id);
    String updateEmployee(Employee employee);
}

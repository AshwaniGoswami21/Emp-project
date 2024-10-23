package org.Ashwani.Emp_Project.Service;

import jakarta.servlet.http.HttpSession;
import org.Ashwani.Emp_Project.Model.Employee;
import org.Ashwani.Emp_Project.Entity.EmployeeEntity;
import org.Ashwani.Emp_Project.Repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean createEmployee(Employee employee, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        EmployeeEntity existingEmployee = employeeRepository.findByEmailAndCompanyId(employee.getEmail(), userId);
        if (existingEmployee != null) {
            return false;

        }

        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity.setCompanyId(userId); // Set the userId as the companyId
        employeeRepository.save(employeeEntity);
        return true;
    }

    @Override
    public Employee readEmployee(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        Employee emp = new Employee();
        BeanUtils.copyProperties(employeeEntity,emp);

        return emp;
    }

    @Override
    public List<Employee> readEmployees(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return Collections.emptyList();
        }

        List<EmployeeEntity> employeesList = employeeRepository.findByCompanyId(userId);
        List<Employee> employees = new ArrayList<>();

        for(EmployeeEntity employeeEntity : employeesList){
            Employee emp = new Employee();
            emp.setId(employeeEntity.getId());
            emp.setName(employeeEntity.getName());
            emp.setEmail(employeeEntity.getEmail());
            emp.setPhone(employeeEntity.getPhone());
            employees.add(emp);
        }

        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {
//        return employees.removeIf(employee -> employee.getId().equals(id));
        EmployeeEntity emp = employeeRepository.findById(id).get();
        employeeRepository.delete(emp);
        return true;
    }

    @Override
    public String updateEmployee(Employee employee) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employee.getId()).get();

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());

        employeeRepository.save(existingEmployee);
        return "Update successfully";
    }


}

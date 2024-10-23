package org.Ashwani.Emp_Project.Controller;

import jakarta.servlet.http.HttpSession;
import org.Ashwani.Emp_Project.Model.Employee;
import org.Ashwani.Emp_Project.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/Home")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    ModelAndView modelAndView = new ModelAndView();

    //Employees list
    @GetMapping("/")
    public ModelAndView getAllEmployees(HttpSession session){
        List<Employee> employees = employeeService.readEmployees(session);
        modelAndView.setViewName("Home");
        modelAndView.addObject("employeeList",employees);
        return modelAndView;
    }

    //Add employee
    @GetMapping("/addEmployee")
    public ModelAndView showAddEmployeeForm() {
        modelAndView.setViewName("addEmployee");
        return modelAndView;
    }
    @PostMapping("/addEmployee")
    public ModelAndView createEmployee(@ModelAttribute Employee employee, HttpSession session) {
        boolean isSaved = employeeService.createEmployee(employee, session);

        if (!isSaved) {
            modelAndView.setViewName("addEmployee");
            modelAndView.addObject("message", "Employee already exists!");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/Home/");
        return modelAndView;
    }

    // Edit Employee
    @GetMapping("/editEmployee/{id}")
    public ModelAndView showEditEmployeeForm(@PathVariable Long id) {
        Employee employee = employeeService.readEmployee(id);
        modelAndView.setViewName("updateEmployee");
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }
    @PostMapping("/updateEmployee")
    public ModelAndView updateEmployee(@ModelAttribute Employee employee) {
        if (employee.getId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null for update");
        }
        employeeService.updateEmployee(employee);
        modelAndView.setViewName("redirect:/Home/");
        return modelAndView;
    }


    @PostMapping("/deleteEmployee")
    public ModelAndView deleteEmployee(@RequestParam Long id) {
        employeeService.deleteEmployee(id);
        modelAndView.setViewName("redirect:/Home/");
        return modelAndView;
    }

    //Employee by id, this is used in postman for backend if, want to fetch employee by id
    @GetMapping("employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.readEmployee(id);

    }

}

package org.Ashwani.Emp_Project.Controller;

import jakarta.servlet.http.HttpSession;
import org.Ashwani.Emp_Project.Entity.UserEntity;
import org.Ashwani.Emp_Project.Service.EmailService;
import org.Ashwani.Emp_Project.Service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView login(){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = userService.findByEmailAndPassword(username, password);

        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", username);
            modelAndView.setViewName("redirect:/Home/");
        } else {
            modelAndView.addObject("signInError", 1);
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }


    @PostMapping("/signup")
    public ModelAndView registerUser(@RequestParam String companyName, @RequestParam String email, @RequestParam String passkey, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        if (emailService.emailExists(email)) {
            modelAndView.addObject("error", "Email already exists");
            modelAndView.setViewName("register");
            return modelAndView;
        }

        // Temporarily store user details in session
        session.setAttribute("companyName", companyName);
        session.setAttribute("email", email);
        session.setAttribute("passkey", passkey);

        // Redirect to OTP sending process
        modelAndView.setViewName("redirect:/send-otp");
        return modelAndView;
    }


}

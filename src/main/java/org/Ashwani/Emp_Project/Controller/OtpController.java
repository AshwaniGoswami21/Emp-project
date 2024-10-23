package org.Ashwani.Emp_Project.Controller;

import jakarta.servlet.http.HttpSession;
import org.Ashwani.Emp_Project.Service.EmailService;
import org.Ashwani.Emp_Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@RestController
public class OtpController {

    @Autowired
    private final EmailService emailService;
    private final UserService userService;
    public OtpController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    private final Random random = new Random();
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/send-otp")
    public ModelAndView sendOTP(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String email = (String) session.getAttribute("email");

        if (email != null) {
            int otp = random.nextInt(999999);
            String subject = "OTP from Emp-project application";
            String message = "OTP = " + otp;

            boolean emailSent = emailService.sendEmail(message, subject, email);
            session.setAttribute("otp", String.valueOf(otp));

            modelAndView.setViewName("verifyOtp");
            modelAndView.addObject("emailVerified", emailSent);
        } else {
            modelAndView.setViewName("register");
            modelAndView.addObject("emailError", "Check your email again!!");
        }

        return modelAndView;
    }


    @PostMapping("/verify-otp")
    public ModelAndView verifyOTP(@RequestParam("otp") String otpInput, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String sessionOTP = (String) session.getAttribute("otp");

        if (sessionOTP != null && sessionOTP.equals(otpInput)) {
            String companyName = (String) session.getAttribute("companyName");
            String email = (String) session.getAttribute("email");
            String passkey = (String) session.getAttribute("passkey");

            boolean userAdded = userService.addUser(companyName, email, passkey);
            if (userAdded) {
                modelAndView.setViewName("login");
            } else {
                modelAndView.setViewName("register");
                modelAndView.addObject("signUpError", "Error occurred while registering user.");
            }
        } else {
            modelAndView.addObject("incorrectOTP", "Incorrect OTP! Check your email.");
            modelAndView.setViewName("verifyOtp");
        }

        return modelAndView;
    }

}
package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.entity.model.LoginUser;
import com.epam.exhibitions.service.ExhibitionService;
import com.epam.exhibitions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserViewMVCController {

    // TODO: Let's try to use Lombok annotation to log the info
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ExhibitionService exhibitionService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/")
    String getHomePage() {
        return "home";
    }

    @GetMapping("/login-form")
    String getLoginPage() {
        return "login-form";
    }

    /**
     * <p>Method getLoginRedirection</p>
     * @param authentication the actual authentication (user and password) used to access the application
     * @return the redirection to the right user view
     * @since 1.0
     */
    @GetMapping("/login-redirection")
    String getLoginRedirection(Authentication authentication, Model model) {
        User loginUser = (User) authentication.getPrincipal();
        User user = userService.getUserByNickname(loginUser.getNickname());

        if(user != null) {
            if(loginUser.getPassword().equals(user.getPassword())) {
                if (user.getRole().equals("Administrator")) {
                    model.addAttribute("user", user);
                    return "redirect:/administrator";
                } else if (user.getRole().equals("Authorized User")) {
                    model.addAttribute("user", user);
                    return "redirect:/authorized-user";
                } else if (user.getRole().equals("Normal User")) {
                    model.addAttribute("user", user);
                    return "redirect:/normal-user";
                }
            }
        }

        model.addAttribute("error", "Incorrect Username or Password");
        return "redirect:/login";
    }

    /**
     * <p>Method setAuthentication</p>
     * @param loginUser the user information (user and password) used to access the application
     * @return the redirection to the right user view
     * @since 1.0
     */
    @PostMapping("/authentication")
    String setAuthentication(@Valid @ModelAttribute(name="loginUser") LoginUser loginUser, Model model) {
        User user = userService.getUserByNickname(loginUser.getNickname());

        // TODO: Change the order
        if(user != null) {
            if(passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                if (user.getRole().equals("Administrator")) {
                    model.addAttribute("user", user);
                    return "redirect:/administrator";
                } else if (user.getRole().equals("Authorized User")) {
                    model.addAttribute("user", user);
                    return "redirect:/authorized-user";
                } else if (user.getRole().equals("Normal User")) {
                    model.addAttribute("user", user);
                    return "redirect:/normal-user";
                }
            }
        }

        model.addAttribute("error", "Incorrect Username or Password");
        return "redirect:/login";
    }

    @GetMapping("/registration")
    String getUserRegistration(Model model) {
        return "registration";
    }

    /**
     * <p>Method addUser</p>
     * @param user the user information (user name, nickname, password and role) to be saved
     * @return the redirection to the right page (registration or home)
     * @since 1.0
     */
    @PostMapping("/add-user")
    String addUser(@Valid @ModelAttribute(name="user") User user, Model model) throws Exception {
        LOG.info(user.getNickname());
        LOG.info(user.getRole());

        if(user != null) {
            User userToCheck = userService.getUserByNickname(user.getNickname());
            if(userToCheck != null) {
                model.addAttribute("message", "User already in use. Please reenter a new User");
                return "registration";
            } else {
                userService.addUser(user);
                return "redirect:/";
            }
        }

        model.addAttribute("error", "All fields must be filled in");
        return "registration";
    }

    @GetMapping("/normal-user")
    String getNormalUserExhibitions(Model model) {
        model.addAttribute("exhibitions", exhibitionService.listActiveExhibitionsServiceCall());
        return "normal-user-view";
    }

    @GetMapping("/authorized-user")
    String getAuthorizedUserExhibitions(Model model) {
        model.addAttribute("exhibitions", exhibitionService.listActiveExhibitionsServiceCall());
        return "authorized-user-view";
    }

    @GetMapping("/administrator")
    String getAdministratorExhibitions(Model model) {
        model.addAttribute("exhibitions", exhibitionService.listExhibitions());
        return "administrator-view";
    }
}

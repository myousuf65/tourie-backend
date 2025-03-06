package xyz.yousuf.tourie.controller;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import xyz.yousuf.tourie.entity.UserModel;
import xyz.yousuf.tourie.repository.UserRepository;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final AuthenticationManager authenticationManager;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


//    @PostMapping("/login")
//    public String login(@RequestBody UserModel userModel, HttpServletRequest request, HttpServletResponse response) {
//
//        System.out.println("user----"+userModel);
//        Authentication unauthenticatedToken = new UsernamePasswordAuthenticationToken(userModel.getName(), userModel.getPassword());
//
//        Authentication authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);
//
//        HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authenticatedToken);
//        SecurityContextHolder.setContext(context);
//        securityContextRepository.saveContext(context, request, response);
//
//        return "login page";
//    }
@PostMapping("/login")
public String login(@RequestBody UserModel userModel, HttpServletRequest request, HttpServletResponse response) {

    try {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(userModel.getName(), userModel.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        SecurityContextHolder.setContext(securityContext);

        return "Login successful";
    } catch (AuthenticationException e) {
        return "Authentication failed: " + e.getMessage();
    }
}

    @PostMapping("/signup")
    public UserModel signup(@RequestBody UserModel user){
        System.out.println("user is " + user);
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println("user is " + user);
        return userRepository.save(user);
    }
}

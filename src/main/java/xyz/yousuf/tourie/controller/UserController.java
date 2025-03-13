package xyz.yousuf.tourie.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import xyz.yousuf.tourie.entity.UserModel;
import xyz.yousuf.tourie.repository.UserRepository;
import xyz.yousuf.tourie.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    private final AuthenticationManager authenticationManager;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel, HttpServletRequest request, HttpServletResponse response) {

       //make sure user exists the database and credentials are correct
        if(userService.checkCredentials(userModel)){

            System.out.println("SETTING SECURITY CONTEXT FOR " +userModel);
            Authentication unauthenticatedToken = new UsernamePasswordAuthenticationToken(userModel.getName(), userModel.getPassword());

            Authentication authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);

            HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticatedToken);
            SecurityContextHolder.setContext(context);

            // if you want to use session based authentication
            // don't need for statless authentication (i.e jwt)
            request.getSession(true);
            //-----------------------------------------------
            securityContextRepository.saveContext(context, request, response);
            return "USER IS LOGGED IN";
        }else{
            return "USER CREDENTIALS NOT CORRECT";
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserModel user){

        System.out.println("GOT USER CREATION REQUEST" + user);

        try {
            UserModel _user = userService.createUser(user);
            System.out.println("USER SUCCESSFULLY CREATED");
            return ResponseEntity.ok(_user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("USER ALREADY EXISTS IN DATABASE");
        }
    }

    @PostMapping("/getdetails/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username){
        UserModel user = userRepository.findByName(username);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email" , user.getEmail());
        userInfo.put("username" , user.getName());
        userInfo.put("id", String.valueOf(user.getId()));

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        // Optionally, you can also clear any cookies or headers related to authentication
        // For example, if you're using JWT, you might want to clear the token from the response headers

        return "USER IS LOGGED OUT";
    }

}

package com.potatogod123.codefellowship.controllers;

import com.potatogod123.codefellowship.models.ApplicationUser;
import com.potatogod123.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ApplicationUserController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    AuthenticationManager authenticationManager;



    @GetMapping("/")
    public String homeRoute(){

        return "home.html";
    }

    @PostMapping("/application-user")
    public RedirectView signUpUser(String username, String password, HttpServletRequest request){
        String encodedPassword = passwordEncoder.encode(password);
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        //this will try to save the users, since we have the username as unique it it fail it will mean a user exist
        //then we just reroute them to a page telling them that
        try {
            applicationUserRepository.save(user);
            System.out.println("this is saved user"+user);
        }catch (Exception e){
            return new RedirectView("/usernamedupe");
        }

        //remember to pass the password that they typed, not the encoded one
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication= authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/userinfo");
    }

    @GetMapping("/usernamedupe")
    public String dupeFail(){
        System.out.println("why is it here");
        return "usernamedupe.html";
    }

    @GetMapping("/userinfo")
    public RedirectView userInfoRequest(Principal p){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        return new RedirectView("/userinfo/"+user.getId());
    }



    @GetMapping("/userinfo/{id}")
    public String userInfo(@PathVariable Long id, Principal p, Model m){
        System.out.println(p);
        if(p!=null){
            m.addAttribute("user", p.getName());
            ApplicationUser person= applicationUserRepository.getOne(id);
            m.addAttribute("person",person);
        }

        return "userinfo.html";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("loginPage");
        return "login.html";
    }


    @GetMapping("/allpost")
    public String allPost(){
        return "allpost.html";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup.html";
    }

    @GetMapping("/application-users")
    public String allUsers(){
        return "allusersAdmin.html";
    }
}

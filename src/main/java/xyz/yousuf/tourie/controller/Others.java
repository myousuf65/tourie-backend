package xyz.yousuf.tourie.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class Others {

    @GetMapping("/private")
    String test(){
        System.out.println("i got something");
        return "private endpoint";
    }

}

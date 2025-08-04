package com.blog.personal.controller;

import com.blog.personal.UserSQL;
import com.blog.personal.repository.SystemUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController

@Controller
@RequestMapping("/home")
public class HomeController {

    public final SystemUser systemUser;

    public HomeController (SystemUser systemUserEx){

        this.systemUser = systemUserEx;

    }

    @GetMapping("/api/usuer/{id}")
    public ResponseEntity<UserSQL> webUser (@PathVariable int id){

        UserSQL user =  systemUser.findUser(id);

      if (user!=null){

          return ResponseEntity.ok(user);

      } else {

          return ResponseEntity.notFound().build();
      }


    }


}

package com.blog.personal.controller;

import com.blog.personal.ContentSQL;
import com.blog.personal.UserSQL;
import com.blog.personal.repository.SystemContent;
import com.blog.personal.repository.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

//@RestController

@Controller
@RequestMapping("/home")
public class HomeController {
    //Siempre deberemos iniciar una instancia del repositorio que se conecte a los datos
    //para conectarnos la base de datos o usar la capa de datos.
    public final SystemUser systemUser;
    public final SystemContent systemSQL;

    @Autowired
    public HomeController (SystemUser systemUserEx, SystemContent systemSQLEX){

        this.systemUser = systemUserEx;
        this.systemSQL = systemSQLEX;

    }

    // localhost:8080/home/api/content/{x}
    @GetMapping("/api/usuer/{id}")
    public String webUser (@PathVariable int id, Model model){

        UserSQL user =  systemUser.findUser(id);

      if (user!=null){
          model.addAttribute("usuario", user); // pasas el objeto a la vista

          return "index";

      } else {

          return "index_no_user";
      }

    }

    // localhost:8080/home/api/content
    @GetMapping("/api/content")
    public String webTitle  (Model model) throws SQLException {

        List<ContentSQL> list = systemSQL.titleList();
        model.addAttribute("newlist", list);
        return "index_list";
    }











}

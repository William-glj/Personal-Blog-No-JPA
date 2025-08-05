package com.blog.personal.controller;

import com.blog.personal.ContentSQL;
import com.blog.personal.UserSQL;
import com.blog.personal.repository.SystemContent;
import com.blog.personal.repository.SystemUser;
import com.blog.personal.service.ContentCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


//La clase HomeController es la encargada de direccionar los endpoints. Crear las rutas, para las vistas de los usuarios.
//Utilizamos controller para poder trabajar sobre las vistas de Thymeleaf
@Controller
@RequestMapping("/home")
public class HomeController {
    //Siempre deberemos iniciar una instancia del repositorio que se conecte a los datos
    //para conectarnos la base de datos o usar la capa de datos.
    public final SystemUser systemUser;
    public final SystemContent contentSQL;

    //Generear o Inyectar dependecias
    @Autowired
    public HomeController (SystemUser systemUserEx, SystemContent systemSQLEX){

        this.systemUser = systemUserEx;
        this.contentSQL = systemSQLEX;

    }

    // localhost:8080/home/api/usuer/x
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

        List<ContentSQL> list = contentSQL.titleList();
        model.addAttribute("newlist", list);
        return "index_list";
    }


    //localhost:8080/home/api/content/element/num
    @GetMapping("/api/content/element/{num}")
    public String webElementRead  (@PathVariable int num, Model model) throws SQLException {

        //Utilizo la lógica de búsqueda
        ContentCRUD contentCRUD =  new ContentCRUD(contentSQL.getDataSource());
        //Guardo el objeto
        ContentSQL content = contentCRUD.readContentSQL(num);
        //Envío el objeto
        model.addAttribute("element", content);
        return "index_element_list";
    }



    /*
    @DeleteMapping("/api/content/delete")
    public String webElementDelete  (Model model) throws SQLException {

        List<ContentSQL> list = systemSQL.titleList();
        model.addAttribute("newlist", list);
        return "index_list";
    }

   */




}

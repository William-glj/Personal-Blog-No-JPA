package com.blog.personal.controller;

import com.blog.personal.ContentSQL;
import com.blog.personal.UserSQL;
import com.blog.personal.repository.SystemContent;
import com.blog.personal.repository.SystemUser;
import com.blog.personal.service.ContentCRUD;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


//La clase HomeController es la encargada de direccionar los endpoints.
//Crear las rutas, para las vistas de los usuarios.
//Utilizamos controller para poder trabajar sobre las vistas de Thymeleaf
@Controller
@RequestMapping("/home") //Raíz de cada servicio/controlador ubicado en la clase
public class HomeController {
    //Siempre deberemos iniciar una instancia del repositorio que se conecte a los datos
    //para conectarnos la base de datos haciendo uso de la capa de datos.
    public final SystemUser systemUser; //Conexión a la entindad usuario de MySQL
    public final SystemContent systemContentSQL; //Conexión a la entindad publicaciones de MySQL

    //Generear o Inyectar dependecias. Obligatorio para que Sprint lo reconozca.
    @Autowired
    public HomeController (SystemUser systemUserEx, SystemContent systemSQLEX){

        this.systemUser = systemUserEx;
        this.systemContentSQL = systemSQLEX;

    }


    //Una vez se activa el servicio, todas las solicitudes serán recogidas en index.html
    //La ventana principal y inicial.
    //Lugar de login.
    //Ruta   localhost:8080/home
    @GetMapping
    public String showHome(Model model){
        //Creamos el recurso web/objeto  userSQL que será recogida en la plantilla index
        //por th:object="${userSQL} y sus elementos
        // th:field="*{name}" y th:field="*{password}"
        model.addAttribute("userSQL",  new UserSQL());
        return "index";
    }


    //Recogemos la información del formulario inicial en index
    //Ruta   localhost:8080/home/api/user
    //Un fallo sin arreglar es la recogida de valores en el campo. Si están vacios uno o ambos campos dará error.
    @PostMapping("/api/user")
    public String processUserForm(@ModelAttribute UserSQL userSQL) {
        //@ModelAttribute le dice o avisa a Spring que debe recibir un objeto de th.
        //El tipo de ese objeto será UserSQL, porque lo establecimos así anteriormente.

        //Redirimos al lugar deseado.Con los parámetros necesarios.
        return "redirect:/home/api/user/" + userSQL.getName().toLowerCase().trim() + "/" + userSQL.getPassword().toLowerCase().trim();
    }

    //Usando los datos del formulario inicial, identificaremos al usuario.
    // Ruta localhost:8080/home/api/user/nombre/contraseña
    @GetMapping("/api/user/{name}/{pssw}") //Esto es una tarea básica.
    // Utilizar la url para el manejo de credenciales es una mala práctica más cuando no hay un cifrado de por medio.
    // Un buen cambio sería usar la liberia de Spring Security o clases de cifrado cómo BCryptPasswordEncoder
    public String webUser(@PathVariable String name, @PathVariable String pssw, Model model, HttpSession session) {
        //@PathVariable nos permite recoger/marcar parámetros en el endpoint.
        //Revisamos la existencia del usuario.
        UserSQL user = systemUser.verifyUser(name, pssw);
        UserSQL userFinal = systemUser.findUser(user.getName());

        if (userFinal != null) {
            session.setAttribute("logUser",userFinal); //Establecemos durante el servicio de la sesión un perfil para el usuario.
            model.addAttribute("user", userFinal); //Enviamos a la vista de th -->  index_list el objeto user
            return "redirect:/home/api/content";
        } else {
            return "index_no_user";
        }
    }

    //Método sencillo para cerrar sesión.
    // Ruta localhost:8080/home/api/user/logout
    @PostMapping("/api/user/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Elimina la sesión registrada -->  session.setAttribute("logUser",userFinal);
        return "redirect:/home"; // Redirige al login
    }

    //Lista de las publicaciones accesibles.
    // localhost:8080/home/api/content
    @GetMapping("/api/content")
    public String webTitle (Model model, HttpSession session) throws SQLException {

        //Forma antigua de verificar el logging del usuario, dentro de cada método de mapeado.
        //Necasia para evitar malos funcionamientos dentro de la página.
        /*

        // Recuperamos el usuario de la sesión
        UserSQL user = (UserSQL) session.getAttribute("logUser");
        if (user == null) {
            // Si no hay usuario logeado, volvemos al login
            return "redirect:/home";
        }
           //Pasamos en cada momento el atributo user para conocer al usuario
           model.addAttribute("user", user);
        */
        //Forma antigua de recopilar el listado de publicaciones/contenidos del blog.
        //Indispenable, porque el ecosistema gira alrededor de esa vista.
        /*
        List<ContentSQL> list = systemContentSQL.titleList();
        model.addAttribute("newlist", list);
         */


        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;

        //Si existe el usuario, procedemos con el resto del código.
        //Obtiendo el listado de publicaciones.

        contentList(model);


        //Redirigimos a la vista deseada.
        return "index_list";
    }


    //Seleccionamos la publicación deseada --> esto funciona junto a enlace en --> index_list
    //localhost:8080/home/api/content/element/num
    @GetMapping("/api/content/element/{num}")
    public String webElementRead  (@PathVariable int num, Model model, HttpSession session) {

        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;

        //Establezco una conexión a la capa de datos mediante el repositorio
        ContentCRUD contentCRUD =  new ContentCRUD(systemContentSQL.getDataSource());
        //Hago uso del servicio para el proceso lógico de la leectura. La almaceno.
        ContentSQL content = contentCRUD.readContentSQL(num);
        //Envío el objeto
        model.addAttribute("element", content); //Nuevo elemento que recogera th

        return "index_element_list";
    }

    //Recogemos la id del formulario en --> index_element_list y eliminamos la publicación de la entidad en MySQL.
    //localhost:8080/home/api/content/delete
    @PostMapping("/api/content/delete")
    public String webElementDelete  (@ModelAttribute ContentSQL contentSQLEx, HttpSession session, Model model) throws SQLException {
        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;

        //Uso del repositorio para marcar la conexión y el servicio para llevar acabo la lógica.
        ContentCRUD contentCRUD = new ContentCRUD(systemContentSQL.getDataSource());
        contentCRUD.deleteContentSQL(contentSQLEx.getId_content());   //Llamamos al método de borrado.


        contentList(model);


         return "index_list";

    }
    //Recogemos la id del formulario en --> index_element_list
    //localhost:8080/home/api/content/delete
    @PostMapping("/api/content/update")
    public String webElementUpdate (@ModelAttribute ContentSQL contentSQLEx, HttpSession session, Model model) throws SQLException {
        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;

        ContentCRUD contentCRUD = new ContentCRUD(systemContentSQL.getDataSource());
        contentCRUD.updateContentSQL(contentSQLEx.getId_content(),contentSQLEx.getTitle(),contentSQLEx.getText());


        contentList(model);


        return "index_list";

    }


    //Redirigimos al lugar de creacción de publicaciones
    //Ruta localhost:8080/home/api/content/create

    @GetMapping("/api/content/create")
    public String redirectWebElementCreate ( HttpSession session, Model model) throws SQLException {
        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;

        //Generamos el nuevo objeto que recibira el formulario en index_create_element
        model.addAttribute("newElement", new ContentSQL());


        return "index_create_element";
    }

    //Recibimos la petición post y ejecutamos la sentencia de inserción en la BD
    //Ruta localhost:8080/home/api/content/create/execute
    @PostMapping("/api/content/create/execute")
    public String webElementCreate (@ModelAttribute ContentSQL contentSQLEx, HttpSession session, Model model) throws SQLException {
        //Si no hay un usuario, enviamos al usuario a home.
        String value = redirectUserLog(session,model);
        if (value != null )return value;


        ContentCRUD contentCRUD = new ContentCRUD(systemContentSQL.getDataSource());
        contentCRUD.createContentSQL(contentSQLEx.getTitle(),contentSQLEx.getText());


        contentList(model);


        return "index_list";
    }

    //Comprobación de la sesión del usuario.
    public String redirectUserLog (HttpSession session, Model model){

        UserSQL user = (UserSQL) session.getAttribute("logUser");
        if (user == null) {
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        return null;

    }
    //Cargamos el contenido
    public List<ContentSQL> contentList (Model model) throws SQLException {
        List<ContentSQL> list = systemContentSQL.titleList();
        model.addAttribute("newlist", list);
        return list;
    }

















}




















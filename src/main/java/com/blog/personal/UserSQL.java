package com.blog.personal;

public class UserSQL {
    /*
    Estos atributos reflejan los datos de la entidad Usuarios de MySQL.

    Anteriormente ubicados en la clase SystemUser.

    ¿Por qué es esto?

    En Spring Boot se distinguen tres funcionalidades manejadas por;

    @Controller - Capa de presentación
    @Repository - Capa de acceso a datos
    @Service - Capa de negocio

    Ninguna de estas anotaciones controlan los datos en su aspecto físico, esa función la realizan sistemas como JPA,Hibernate..etc.

    Por eso es importante separar el modelo de los datos, las clases o la información de los objetos, del resto. Evitando interferencias.

     */
    private Integer id;

    private  String name;

    private  String password;


    public UserSQL (){

    }
     public UserSQL (Integer idEx, String nameEx){
        this.id = idEx;
        this.name = nameEx;
     //   this.password = password;
     }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId (Integer id){
        this.id =id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

package com.blog.personal;
//Modelo de datos, fuera de JPA.
public class UserSQL {

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

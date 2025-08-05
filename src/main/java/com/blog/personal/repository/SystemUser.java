package com.blog.personal.repository;

import com.blog.personal.UserSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Siguiendo el modelo de trabajo MVC.
//La clase SystemUser es un modelo de datos, que trabajo a la par o se correponde con las funciones de su clase "superior" UserSQL.
@Repository
public class SystemUser {

    private DataSource dataSource;

    //Spring suele exigir un constructor vacío.
    public SystemUser() {

    }

    //Obligatorio para crear un objeto inyectado en Spring
    @Autowired
    public SystemUser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public UserSQL searchUser (String nameEx, String passwordEx){
        try {
            Connection con = dataSource.getConnection();
            Statement sttm = con.createStatement();


            //    "SELECT COUNT(*) FROM usuarios WHERE nombre = '" + nameEx + "' AND contraseña = '" + passwordEx + "'");
            ResultSet rs =  sttm.executeQuery(
                    "SELECT * FROM usuarios WHERE nombre = '" + nameEx + "' AND contraseña = '" + passwordEx + "' LIMIT 1");


            if(rs.next()){

                return new UserSQL(rs.getInt("id_Usuario"), rs.getString("nombre"));

            }


            sttm.close();
            con.close();


        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return new UserSQL(null, null);
    }

    public UserSQL findUser (Integer idEx){
        try {
            Connection con = dataSource.getConnection();
            Statement sttm = con.createStatement();



            ResultSet rs =  sttm.executeQuery(
                    "SELECT * FROM usuarios WHERE id_Usuario =" + idEx );


            if(rs.next()){

                return new UserSQL(rs.getInt("id_Usuario"), rs.getString("nombre"));
            }



            sttm.close();
            con.close();

        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return null;
    }











}

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

@Repository
public class SystemUser {

    private DataSource dataSource;


    public SystemUser() {

    }
    @Autowired
    public SystemUser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public int searchUser (String nameEx, String passwordEx){
        try {
            Connection con = dataSource.getConnection();
            Statement sttm = con.createStatement();



            ResultSet rs =  sttm.executeQuery(
                    "SELECT COUNT(*) FROM usuarios WHERE nombre = '" + nameEx + "' AND contraseña = '" + passwordEx + "'");


            if(rs.next()){

                return rs.getInt(1);

            }





        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return 0;
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





        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return null;
    }











}

package com.blog.personal.repository;

import com.blog.personal.UserSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

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

    public UserSQL verifyUser(String nameEx, String passwordEx){
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) FROM usuarios WHERE nombre = ? AND contraseña = ?"
            );

            ps.setString(1, nameEx);
            ps.setString(2, passwordEx);

            ResultSet rs = ps.executeQuery();

            int countUpdate = 0;

            if (rs.next()) {
                countUpdate = rs.getInt(1);




                return new UserSQL(nameEx);
            }



            ps.close();
            con.close();


        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return new UserSQL(null);
    }



    public UserSQL findUser (String  nameEx){
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id_Usuario FROM usuarios WHERE nombre = ?");

            ps.setString(1, nameEx);


            ResultSet rs =  ps.executeQuery();


            if(rs.next()){

                return new UserSQL(rs.getInt("id_Usuario"), nameEx );
            }



            ps.close();
            con.close();

        } catch (SQLException e){
            System.out.println("Fallo: Clase SystemUser error a la hora de" +
                    " instanciar el conector a la base de datos. Revisar el archivo --> application.properties, " +
                    "la base de datos o los parámetros");
        }
        return null;
    }











}

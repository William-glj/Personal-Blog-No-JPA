package com.blog.personal.repository;

import com.blog.personal.ContentSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Un modelo de datos, que representaba y/o trabajo con información y lógica.
//Su clase "superior" sería ContentSQL.
@Repository
public class SystemContent {


    private DataSource dataSource;


    public SystemContent() {

    }
    @Autowired
    public SystemContent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }



    public List<ContentSQL> titleList () throws SQLException {
        ArrayList<ContentSQL> store = new ArrayList<>();
        Connection con = dataSource.getConnection();
        Statement sttm = con.createStatement();
        ResultSet rs = sttm.executeQuery("Select id_Publicaciones, titulo, fecha_Creacion from publicaciones ");


        while (rs.next()){

            store.add(new ContentSQL(rs.getInt("id_Publicaciones"),rs.getString("titulo"),rs.getDate("fecha_Creacion")));

        }
        rs.close();
        sttm.close();
        con.close();
        return  List.copyOf(store);

    }








}

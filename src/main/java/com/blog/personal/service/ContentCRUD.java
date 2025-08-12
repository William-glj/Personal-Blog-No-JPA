package com.blog.personal.service;

import com.blog.personal.ContentSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//La complejidad de un servicio es bastante llamativa.
//Refleja en su totalidad las acciones de negocio dentro de una clase, a nivel avanzado o intermedio. Muchas veces es redundante su creación.
//Principalmente interactúa con el controlador.
@Service
public class ContentCRUD {

    private DataSource dataSource;

    @Autowired
    public ContentCRUD(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public ContentCRUD() {


    }


    public ContentSQL readContentSQL(Integer idEx) {
        try {
            Connection conn = dataSource.getConnection();
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery("SELECT * FROM publicaciones where id_Publicaciones =" + idEx);

            if (rs.next()) {
                // Datos del constructor
                // ContentSQL(Integer id_contentEx, String titleEx, String textEx, Date dateEx )
                return new ContentSQL(
                        rs.getInt("id_Publicaciones"),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        rs.getDate("fecha_Creacion"));
            }


            //Si para un futuro da error, añadir el finally y comprobar hasta la información de los conectores.
            //pero por ahora para ahorrar tiempo los cerramos de golpe.
            rs.close();
            sttm.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("La operación de lectura, en la clase ContentCRUD, ha fallado.");
            ex.printStackTrace();
        }

        //Datos del constructor (Integer id_contentEx, String titleEx, Date dateEx )
        return new ContentSQL(null, null, null);

    }


    public void deleteContentSQL(Integer idEx) {
        try {
            Connection conn = dataSource.getConnection();
            Statement sttm = conn.createStatement();

            int filasAfectadas = sttm.executeUpdate("DELETE FROM publicaciones WHERE id_Publicaciones = " + idEx);

            if (filasAfectadas > 0) {
                System.out.println("Publicación eliminada con éxito.");
            } else {
                System.out.println("No se encontró ninguna publicación con ese ID.");
            }

            sttm.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("La operación de borrado, en la clase ContentCRUD, ha fallado.");
            ex.printStackTrace();
        }
    }
    public void updateContentSQL(Integer idEx, String titleEx, String contentEx) {

        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE publicaciones SET titulo = ?, contenido = ? WHERE id_Publicaciones = ?");
            ps.setString(1, titleEx);
            ps.setString(2, contentEx);
            ps.setInt(3, idEx);

            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("La operación de actualización en la clase ContentCRUD ha fallado.");
            ex.printStackTrace();
        }
    }

    public void createContentSQL (String titleEx, String contentEx) {

        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn
                    .prepareStatement
                            ("INSERT INTO publicaciones (creador, titulo, contenido) VALUE\n" +
                                    "(?,?,?)");



            ps.setInt(1, 2);
            ps.setString(2, titleEx);
            ps.setString(3, contentEx);

            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("La operación de creación ha fallado en la clase ContentCRU.");
            ex.printStackTrace();
        }
    }





}

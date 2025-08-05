package com.blog.personal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentSQL {

    private Integer id_content;
    private Integer id_user;
    private String title;
    private String text;

    private Date date ;

    public ContentSQL(){}
    public ContentSQL(Integer id_contentEx, String titleEx, String textEx, Date dateEx ){

        this.id_content = id_contentEx;
        this.title = titleEx;
        this.text = textEx;
        this.date = dateEx;
    }
    public ContentSQL(Integer id_contentEx, String titleEx, Date dateEx ){

        this.id_content = id_contentEx;
        this.title = titleEx;
        this.date = dateEx;

    }





    public void setId_content(Integer id_content) {
        this.id_content = id_content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_content() {
        return id_content;
    }

    public Integer getId_user() {
        return id_user;
    }


    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}

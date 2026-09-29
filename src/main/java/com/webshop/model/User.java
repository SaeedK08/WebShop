package com.webshop.model;

public class User {
    private String name;
    private String password;
    private String id;
    private String role;

    // användare försöker logga in -> jsp via dto paktera data -> service -> dto -> auttentisera mot db
    // -> service skapar en session och en cookie -> cookie skickas tillbaka till browsern
}

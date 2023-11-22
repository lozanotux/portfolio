package com.redhat.apidemo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/*
 * Spring Data JDBC uses a NamingStrategy to map an entity name to a table name.
 * By converting the entities class name. However, you can specify the table name
 * using @Table annotation.
 * */
@Table("user")
public class User {

    @Id
    private long id;
    private String name;

    public User() {
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

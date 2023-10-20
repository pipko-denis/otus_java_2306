package ru.otus.crm.model;

import ru.otus.jdbc.api.Column;
import ru.otus.jdbc.api.Id;
import ru.otus.jdbc.api.Table;

@Table(name = "client")
public class Client {
    @Id
    @Column
    private Long id;

    @Column
    private String name;

    public Client() {}

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

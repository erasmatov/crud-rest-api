package net.erasmatov.crudapi.model;

import jakarta.persistence.*;
import net.erasmatov.crudapi.annotation.Exclude;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Exclude
    @OneToMany(mappedBy = "user")
    private List<Event> events;
    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private Status status;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

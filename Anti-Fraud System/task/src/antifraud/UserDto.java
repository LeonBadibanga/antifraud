package antifraud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    private Long id;
    private String name;
    private String username;
    private Role role; // Role property

    // No-args constructor
    public UserDto() {
    }

    // Constructor with parameters
    public UserDto(Long id, String name, String username, Role role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role; // Set role
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
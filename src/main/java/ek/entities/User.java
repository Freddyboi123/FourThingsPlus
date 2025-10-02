package ek.entities;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    String username;
    String password;
    String email;
    String role;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}

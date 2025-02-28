package com.estudos.reservas.persistence;

import com.estudos.reservas.enums.UserType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "tb_user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType role;
    @Column(nullable = false, unique = true)
    private String email;

    private User() {}

    public String getPassword() {
        return password;
    }

    public UserType getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder name(String name) {
            user.name = name;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder role(UserType role) {
            user.role = role;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public User build() {
            return user;
        }
    }
}

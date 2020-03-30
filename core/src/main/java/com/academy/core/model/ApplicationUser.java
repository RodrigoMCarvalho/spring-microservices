package com.academy.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationUser implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Campo 'username' é obrigatório")
    @Column(nullable = true)
    private String username;

    @NotNull(message = "Campo 'password' é obrigatório")
    @Column(nullable = true)
    @ToString.Exclude
    private String password;

    @NotNull(message = "Campo 'role' é obrigatório")
    @Column(nullable = true)
    private String role = "USER"; //valor padrão

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ApplicationUser(@NotNull ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
    }

    @Override
    public Long getId() {
        return id;
    }



}

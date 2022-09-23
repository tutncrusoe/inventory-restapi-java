package com.lam.jira.user.model;

import com.lam.jira.common.model.BaseEntity;
import com.lam.jira.role.model.Role;
import com.lam.jira.role.model.RoleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = UserEntity.User.TABLE_NAME)
public class User extends BaseEntity {
    @Column(name = UserEntity.User.EMAIL, length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = UserEntity.User.PASSWORD)
    private String password;

    @Column(name = UserEntity.User.USERNAME, unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = UserEntity.User.DISPLAY_NAME, nullable = false)
    private String displayName;

    @Column(name = UserEntity.User.FIRST_NAME)
    private String firstName;

    @Column(name = UserEntity.User.LAST_NAME)
    private String lastName;

    @Column(name = UserEntity.User.AVATAR)
    private String avatar;

    @Column(name = UserEntity.User.STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = UserEntity.User.DEPARTMENT)
    private String department;

    @Column(name = UserEntity.User.MAJOR)
    private String major;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = RoleEntity.Role.TABLE_NAME,
            joinColumns = @JoinColumn(name = UserEntity.User.USER_ID),
            inverseJoinColumns = @JoinColumn(name = UserEntity.User.ROLE_ID)
    )
    private Set<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void clearRole() {
        this.roles.clear();
    }
}

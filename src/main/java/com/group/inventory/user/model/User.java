package com.group.inventory.user.model;

import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.department.model.Department;
import com.group.inventory.role.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = UserEntity.UserRole.USER_ROLE_TABLE_NAME,
            joinColumns = @JoinColumn(name = UserEntity.UserRole.USER_ID),
            inverseJoinColumns = @JoinColumn(name = UserEntity.UserRole.ROLE_ID)
    )
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = UserEntity.UserDepartment.USER_DEPARTMENT_TABLE_NAME,
            joinColumns = @JoinColumn(name = UserEntity.UserDepartment.USER_ID),
            inverseJoinColumns = @JoinColumn(name = UserEntity.UserDepartment.DEPARTMENT_ID)
    )
    private Department department;

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addDepartment(Department department) {
        this.department = department;
        department.getUsers().add(this);
    }

    public void removeDepartment(Department department) {
        this.department = null;
        department.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return super.equals(obj)
                && user.getEmail().equals(email)
                && user.getUsername().equals(username);
    }

    public void clearRole() {
        this.roles.clear();
    }
}

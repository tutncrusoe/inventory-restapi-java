package com.group.inventory.user.model;

import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.role.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = UserEntity.User.TABLE_NAME)
public class User extends BaseEntity {

    @Column(name = UserEntity.User.NAME,
            nullable = false,
            length = 100)
    private String username;

    @Column(name = UserEntity.User.EMAIL,
            nullable = false,
            unique = true,
            length = 100)
    private String email;

    @Column(name = UserEntity.User.PASSWORD,
            nullable = false)
    private String password;

    @Column(name = UserEntity.User.AVATAR)
    private String avatar;

    @Column(name = UserEntity.User.STATUS)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "i_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        User user = (User) obj;

        return this.id != null && Objects.equals(this.id, user.id)
                && Objects.equals(this.email, user.getEmail());
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

}

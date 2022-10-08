package com.group.inventory.user.model;

import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.role.model.UserGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
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
            unique = true,
            length = 100,
            updatable = false)
    private String name;

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

    @Column(name = UserEntity.User.DEPARTMENT)
    private String department;

    @ManyToMany(mappedBy = "users")
    private Set<UserGroup> userGroups = new LinkedHashSet<>();

    @Column(name = UserEntity.User.STATUS)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

}

package com.group.inventory.role.model;

import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = RoleEntity.Role.TABLE_NAME)
public class Role extends BaseEntity {

    @Column(name = RoleEntity.Role.NAME, unique = true)
    @Enumerated(EnumType.STRING)
    private ERole name;

    @Column(name = RoleEntity.Role.DESCRIPTION)
    @NotBlank
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Role role = (Role) obj;

        return this.id != null && Objects.equals(this.id, role.id)
                && Objects.equals(this.name, role.getName());
    }

}

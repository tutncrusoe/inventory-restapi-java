package com.group.inventory.department.model;

import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.user.model.User;
import com.group.inventory.user.model.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = DepartmentEntity.Department.TABLE_NAME)
public class Department extends BaseEntity {

    @Column(name = DepartmentEntity.Department.NAME,
            nullable = false,
            unique = true,
            length = 100)
    private String name;

    @Column(name = DepartmentEntity.Department.DESCRIPTION,
            length = 100)
    private String description;

    @OneToMany(mappedBy = DepartmentEntity.DepartmentMappedUser.MAPPED)
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Department department = (Department) obj;

        return this.id != null && Objects.equals(this.id, department.id)
                && Objects.equals(this.name, department.getName());
    }

    /**
     * If the user is not blocked and has no department, add the user to the department.
     *
     * @param user The user to be added to the department.
     */
    public void addUser(User user) {
        if (user.getDepartment() != null
                || Objects.equals(UserStatus.BLOCKED, user.getStatus()))
            return;

        this.users.add(user);
        user.setDepartment(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.setDepartment(null);
    }
}

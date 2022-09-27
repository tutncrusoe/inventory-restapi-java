package com.group.inventory.department.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.user.model.User;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = DepartmentEntity.Department.TABLE_NAME)
public class Department extends BaseEntity {
    @Column(name = DepartmentEntity.Department.NAME)
    private String name;
    @Column(name = DepartmentEntity.Department.DESCRIPTION)
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<User> users = new LinkedHashSet();
}

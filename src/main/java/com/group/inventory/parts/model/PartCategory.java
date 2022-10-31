package com.group.inventory.parts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.model.BaseEntity;
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
@Table(name = PartEntity.PartCategory.TABLE_NAME)
public class PartCategory extends BaseEntity {
    @Column(name = PartEntity.PartCategory.NAME)
    private String name;

    @Column(name = PartEntity.PartCategory.DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = PartEntity.PartCategory.PART_SESSION_MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PartSession> partSessions = new LinkedHashSet<>();

    public void addPartSession(PartSession partSession) {
        this.partSessions.add(partSession);
        partSession.setPartCategory(this);
    }

    public void removePartSession(PartSession partSession) {
        this.partSessions.remove(partSession);
        partSession.setPartCategory(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        PartCategory partCategory = (PartCategory) obj;

        return this.id != null && Objects.equals(this.id, partCategory.id)
                && Objects.equals(this.name, partCategory.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

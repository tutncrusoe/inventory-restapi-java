package com.group.inventory.parts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = PartEntity.PartSession.TABLE_NAME)
public class PartSession extends BaseEntity {
    @Column(name = PartEntity.PartSession.NAME)
    private String name;

    @Column(name = PartEntity.PartSession.DESCRIPTION)
    private String description;

    @Column(name = PartEntity.PartSession.PART_NUMBER)
    private String partNumber;

    @ManyToOne
    @JoinColumn(name = PartEntity.PartSession.PART_CATEGORY_ID)
    @JsonIgnore
    private PartCategory partCategory;

    @OneToMany(mappedBy = PartEntity.PartSession.PART_DETAILS_MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PartDetails> partDetailsList = new ArrayList<>();

    public void addPartDetails(PartDetails partDetails) {
        this.partDetailsList.add(partDetails);
        partDetails.setPartSession(this);
    }

    public void removePartDetails(PartDetails partDetails) {
        this.partDetailsList.remove(partDetails);
        partDetails.setPartSession(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        PartSession partSession = (PartSession) obj;

        return this.id != null && Objects.equals(this.id, partSession.id)
                && Objects.equals(this.name, partSession.getName())
                && Objects.equals(this.partNumber, partSession.partNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

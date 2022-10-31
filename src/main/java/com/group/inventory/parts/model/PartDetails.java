package com.group.inventory.parts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.action.model.Action;
import com.group.inventory.action.model.EActionStatus;
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
@Table(name = PartEntity.PartDetails.TABLE_NAME)
public class PartDetails extends BaseEntity {
    @Column(name = PartEntity.PartDetails.NAME)
    private String name;

    @Column(name = PartEntity.PartDetails.DESCRIPTION)
    private String description;

    @Column(name = PartEntity.PartDetails.PHOTO)
    private String photo;

    @Column(name = PartEntity.PartDetails.MADE_BY)
    private String madeBy;

    @Column(name = PartEntity.PartDetails.PART_NUMBER)
    private String partNumber;

    @Column(name = PartEntity.PartDetails.PART_IS_SPECIAL)
    private boolean isSpecial = false;

    @Column(name = PartEntity.PartDetails.PART_QUANTITY)
    private long quantity;

    @Column(name = PartEntity.PartStatus.NAME, nullable = false)
    @Enumerated(EnumType.STRING)
    private EPartStatus partStatus;

    @Column(name = PartEntity.PartStatus.ACTION_STATUS)
    @Enumerated(EnumType.STRING)
    private EActionStatus actionStatus;

    @OneToMany(mappedBy = PartEntity.PartDetails.ACTION_MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Action> action = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PartEntity.PartDetails.PART_SESSION_ID)
    @JsonIgnore
    private PartSession partSession;

    public void addAction(Action action) {
        this.action.add(action);
        action.setPartDetails(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        PartDetails partDetails = (PartDetails) obj;

        return this.id != null && Objects.equals(this.id, partDetails.id)
                && Objects.equals(this.partNumber, partDetails.getPartNumber());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

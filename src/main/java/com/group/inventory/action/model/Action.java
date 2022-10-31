package com.group.inventory.action.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.parts.model.EPartStatus;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = ActionEntity.Action.TABLE_NAME)
public class Action extends BaseEntity {

    @Column(name = ActionEntity.Action.NAME)
    @Enumerated(EnumType.STRING)
    private EActionStatus name;

    @Column(name = ActionEntity.Action.DESCRIPTION)
    private String description;

    private String photo;

    @Column(name = ActionEntity.Action.CUR_PART_STATUS)
    @Enumerated(EnumType.STRING)
    private EPartStatus partStatus;

    @Column(name = ActionEntity.Action.QUANTITY_USED)
    private long quantityUsed;

    @Column(name = ActionEntity.Action.IS_SPECIAL_PART)
    private boolean isSpecialPart = false;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = ActionEntity.Action.USER_ID, referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ActionEntity.Action.PART_DETAILS_ID)
    @JsonIgnore
    private PartDetails partDetails;
}

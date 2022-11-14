package com.group.inventory.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.model.BaseEntity;
import com.group.inventory.parts.model.PartDetails;
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
@Table(name = StorageEntity.Storage.TABLE_NAME)
public class Storage extends BaseEntity {

    @Column(name = StorageEntity.Storage.NAME,
            nullable = false,
            length = 100)
    private String name;

    @Column(name = StorageEntity.Storage.CODE,
            nullable = false,
            unique = true,
            length = 20)
    private String code;

    @Column(name = StorageEntity.Storage.DESCRIPTION,
            length = 100)
    private String description;

    @Column(name = StorageEntity.Storage.CAPACITY,
            nullable = false)
    private double volume;

    // The rate between storage volume with current volume
    @Transient
    private static final double RATE = 0.7;

    // A new storage must is empty
    @Column(name = StorageEntity.Storage.CURRENT_VOLUME,
            nullable = false)
    private double currentVolume = 0;

    // Default value for Status is available
    @Column(name = StorageEntity.Storage.STATUS,
            nullable = false)
    @Enumerated(EnumType.STRING)
    private StorageStatus status = StorageStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = StorageEntity.LocationMappedStorage.JOIN_COLUMN)
    @JsonIgnore
    private Location location;

    @OneToMany(mappedBy = StorageEntity.StorageMappedPartDetail.MAPPED,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JsonIgnore
    private Set<PartDetails> partDetails = new LinkedHashSet<>();

    public void addPart(PartDetails part) {
        if (isUnavailable())
            throw new InventoryBusinessException("Storage is unavailable");

        if (!isCanAdd(part.getVolume())) {
            throw new InventoryBusinessException("Can't add part because storage not enough volume");
        }
        // Add part to storage
        this.partDetails.add(part);
        part.setStorage(this);

        // Update this storage
        this.currentVolume += part.getVolume();

        if (isFull()) {
            changeStatus(StorageStatus.FULL);
        }

    }

    public void removePart(PartDetails part) {
        if (status.equals(StorageStatus.UNAVAILABLE))
            throw new InventoryBusinessException("Can't remove part because storage is unavailable");

        if (partDetails.contains(part)) {
            // Remove part detail
            this.partDetails.remove(part);
            part.setStorage(null);

            currentVolume -= part.getVolume();

            if (isFull())
                status = StorageStatus.AVAILABLE;
        }
    }

    // Check storage is can add the part with it volume.
    public boolean isCanAdd(double partVolume) {
        return currentVolume + partVolume <= this.volume * RATE;
    }

    public boolean isFull() {
        return currentVolume == volume * RATE
                || Objects.equals(status, StorageStatus.FULL);
    }

    public boolean isUnavailable() {
        return Objects.equals(status, StorageStatus.UNAVAILABLE);
    }

    // Update storage's status
    public void changeStatus(StorageStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Storage storage = (Storage) obj;

        return this.id != null && Objects.equals(this.id, storage.id)
                && Objects.equals(this.code, storage.getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

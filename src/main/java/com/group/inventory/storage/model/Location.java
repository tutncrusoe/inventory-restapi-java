package com.group.inventory.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.common.exception.InventoryBusinessException;
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
@Table(name = StorageEntity.Location.TABLE_NAME)
public class Location extends BaseEntity {

    @Column(name = StorageEntity.Location.NAME,
            nullable = false,
            unique = true,
            length = 100)
    private String name;

    @Column(name = StorageEntity.Location.DESCRIPTION,
            length = 100)
    private String description;

    @Column(name = StorageEntity.Location.CAPACITY,
            nullable = false)
    private int capacity;

    @Column(name = StorageEntity.Location.CURRENT_STORAGE_QUANTITY,
            nullable = false)
    private int currentStorageQuantity;

    @Column(name = StorageEntity.Location.STATUS,
            nullable = false)
    private StorageStatus status;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = StorageEntity.WarehouseMappedLocation.JOIN_COLUMN)
    @JsonIgnore
    private Warehouse warehouse;

    @OneToMany(mappedBy = StorageEntity.LocationMappedStorage.MAPPED,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JsonIgnore
    private Set<Storage> storages = new LinkedHashSet<>();

    public void addStorage(Storage storage) {
        if (isUnavailable())
            throw new InventoryBusinessException("Can't add storage because location is unavailable");

        if (isFull())
            throw new InventoryBusinessException("Can't add storage because location is full");

        storages.add(storage);
        storage.setLocation(this);

        currentStorageQuantity++;
        if (isFull())
            status = StorageStatus.FULL;

    }

    public void removeStorage(Storage storage) {
        if (isUnavailable())
            throw new InventoryBusinessException("Can't remove storage because location is unavailable");

        storages.remove(storage);
        storage.setLocation(null);

        currentStorageQuantity--;

        if (isFull())
            status = StorageStatus.AVAILABLE;

    }

    public boolean isUnavailable() {
        return Objects.equals(status, StorageStatus.UNAVAILABLE);
    }

    public boolean isFull() {
        return currentStorageQuantity == capacity
                || Objects.equals(status, StorageStatus.FULL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Location location = (Location) obj;

        return this.id != null && Objects.equals(this.id, location.id)
                && Objects.equals(this.name, location.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}

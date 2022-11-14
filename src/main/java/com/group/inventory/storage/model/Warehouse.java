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
@Table(name = StorageEntity.Warehouse.TABLE_NAME)
public class Warehouse extends BaseEntity {

    @Column(name = StorageEntity.Warehouse.NAME,
            nullable = false,
            unique = true,
            length = 100)
    private String name;

    @Column(name = StorageEntity.Warehouse.CAPACITY,
            nullable = false)
    private int capacity;

    @Column(name = StorageEntity.Warehouse.CURRENT_LOCATION_QUANTITY,
            nullable = false)
    private int currentLocationQuantity;

    @Column(name = StorageEntity.Warehouse.STATUS,
            nullable = false)
    @Enumerated(EnumType.STRING)
    private StorageStatus status;

    @Column(name = StorageEntity.Warehouse.ADDRESS,
            nullable = false,
            unique = true)
    private String address;

    @Column(name = StorageEntity.Warehouse.DESCRIPTION,
            length = 100)
    private String description;

    @OneToMany(mappedBy = StorageEntity.WarehouseMappedLocation.MAPPED,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JsonIgnore
    private Set<Location> locations = new LinkedHashSet<>();

    public void addLocation(Location location) {
        if (isUnavailable())
            throw new InventoryBusinessException("Can't add location because warehouse is unavailable");

        if (isFull())
            throw new InventoryBusinessException("Can't add location because warehouse is full");

        locations.add(location);
        location.setWarehouse(this);

        currentLocationQuantity ++;

        if (isFull())
            status = StorageStatus.FULL;
    }

    private boolean isUnavailable() {
        return Objects.equals(status, StorageStatus.UNAVAILABLE);
    }

    public void removeLocation(Location location) {
        if (isUnavailable())
            throw new InventoryBusinessException("Can't add location because warehouse is unavailable");

        if (locations.contains(location)){

            locations.remove(location);
            location.setWarehouse(null);

            currentLocationQuantity --;

            if (isFull())
                status = StorageStatus.AVAILABLE;
        }
    }
    private boolean isFull() {
        return currentLocationQuantity == capacity
                || Objects.equals(status, StorageStatus.FULL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Warehouse warehouse = (Warehouse) obj;

        return this.id != null && Objects.equals(this.id, warehouse.id)
                && Objects.equals(this.name, warehouse.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

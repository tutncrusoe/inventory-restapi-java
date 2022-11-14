package com.group.inventory.storage.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageEntity {

    @UtilityClass
    public class Warehouse {
        public static final String TABLE_NAME = "I_WAREHOUSE";
        public static final String NAME = "I_NAME";
        public static final String STATUS = "I_STATUS";

        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String CAPACITY = "I_CAPACITY";
        public static final String CURRENT_LOCATION_QUANTITY = "I_CURRENT_LOCATION_QUANTITY";
        public static final String ADDRESS = "I_ADDRESS";
    }

    @UtilityClass
    public class Location {
        public static final String TABLE_NAME = "I_LOCATION";
        public static final String NAME = "I_NAME";
        public static final String STATUS = "I_STATUS";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String CAPACITY = "I_CAPACITY";
        public static final String CURRENT_STORAGE_QUANTITY = "I_CURRENT_STORAGE_QUANTITY";
    }

    @UtilityClass
    public class Storage {
        public static final String TABLE_NAME = "I_STORAGE";
        public static final String NAME = "I_NAME";
        public static final String CODE = "I_CODE";
        public static final String STATUS = "I_STATUS";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String CAPACITY = "I_CAPACITY";
        public static final String CURRENT_VOLUME = "I_CURRENT_VOLUME";
    }

    @UtilityClass
    public class WarehouseMappedLocation {
        public static final String MAPPED = "warehouse";
        public static final String JOIN_COLUMN = "I_WAREHOUSE_ID";
    }

    @UtilityClass
    public class LocationMappedStorage {
        public static final String MAPPED = "location";
        public static final String JOIN_COLUMN = "I_LOCATION_ID";
    }

    @UtilityClass
    public class StorageMappedPartDetail {
        public static final String MAPPED = "storage";
        public static final String JOIN_COLUMN = "I_STORAGE_ID";
    }

}

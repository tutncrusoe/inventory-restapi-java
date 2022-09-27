package com.group.inventory.department.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DepartmentEntity {
    @UtilityClass
    public class Department {
        public static final String TABLE_NAME = "I_DEPARTMENT";
        public static final String NAME = "I_NAME";
        public static final String DESCRIPTION = "I_DESCRIPTION";
    }
}

package com.group.inventory.user.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserEntity {
    @UtilityClass
    public class User {
        public static final String TABLE_NAME = "I_USER";
        public static final String NAME = "I_NAME";
        public static final String EMAIL = "I_EMAIL";
        public static final String PASSWORD = "I_PASSWORD";
        public static final String STATUS = "I_STATUS";

        public static final String DEPARTMENT = "I_DEPARTMENT";
        public static final String AVATAR = "I_AVATAR";
    }

    @UtilityClass
    public class UserRole {
        public static final String USER_ROLE_TABLE_NAME = "I_USER_ROLE";

        public static final String USER_ID = "I_USER_ID";

        public static final String ROLE_ID = "I_ROLE_ID";
    }

    @UtilityClass
    public class UserDepartment {
        public static final String USER_DEPARTMENT_TABLE_NAME = "I_USER_DEPARTMENT";

        public static final String USER_ID = "I_USER_ID";

        public static final String DEPARTMENT_ID = "I_DEPARTMENT_ID";
    }
}

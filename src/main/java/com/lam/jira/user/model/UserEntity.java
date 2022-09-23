package com.lam.jira.user.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserEntity {
    @UtilityClass
    public class User {
        public static final String TABLE_NAME = "G_USER";
        public static final String EMAIL = "G_EMAIL";
        public static final String PASSWORD = "G_PASSWORD";

        public static final String USERNAME = "G_USERNAME";

        public static final String DISPLAY_NAME = "G_DISPLAY_NAME";

        public static final String FIRST_NAME = "G_FIRST_NAME";

        public static final String LAST_NAME = "G_LAST_NAME";

        public static final String STATUS = "G_STATUS";

        public static final String DEPARTMENT = "G_DEPARTMENT";

        public static final String MAJOR = "G_MAJOR";

        public static final String HOBBIES = "G_HOBBIES";

        public static final String FACEBOOK = "G_FACEBOOK";

        public static final String AVATAR = "G_AVATAR";

        public static final String USER_ID = "G_USER_ID";

        public static final String ROLE_ID = "G_ROLE_ID";
    }
}

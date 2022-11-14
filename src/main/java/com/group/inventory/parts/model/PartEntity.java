package com.group.inventory.parts.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PartEntity {
    @UtilityClass
    public class PartCategory {
        public static final String TABLE_NAME = "I_PART_CATEGORY";
        public static final String NAME = "I_NAME";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String PART_SESSION_MAPPED_BY = "partCategory";

    }

    @UtilityClass
    public class PartSession {
        public static final String TABLE_NAME = "I_PART_SESSION";
        public static final String NAME = "I_NAME";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String PART_NUMBER= "I_PART_NUMBER";
        public static final String PART_CATEGORY_ID= "I_PART_CATEGORY_ID";
        public static final String PART_DETAILS_MAPPED_BY = "partSession";
    }

    @UtilityClass
    public class PartDetails {
        public static final String TABLE_NAME = "I_PART_DETAILS";
        public static final String NAME = "I_NAME";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String PHOTO = "I_PHOTO";
        public static final String MADE_BY = "I_MADE_BY";
        public static final String PART_NUMBER= "I_PART_NUMBER";
        public static final String PART_IS_SPECIAL= "I_PART_IS_SPECIAL";
        public static final String PART_QUANTITY= "I_PART_QUANTITY";
        public static final String PART_VOLUME= "I_PART_VOLUME";

        public static final String PART_SESSION_ID= "I_PART_SESSION_ID";

        public static final String ACTION_MAPPED_BY= "partDetails";

    }

    @UtilityClass
    public class PartStatus {
        public static final String TABLE_NAME = "I_PART_DETAILS_STATUS";
        public static final String PART_ID = "I_PART_DETAILS_ID";
        public static final String NAME = "I_PART_STATUS";
        public static final String ACTION_STATUS = "I_ACTION_STATUS";
    }
}

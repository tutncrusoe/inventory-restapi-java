package com.group.inventory.action.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ActionEntity {
    @UtilityClass
    public static class Action {
        public static final String TABLE_NAME = "I_ACTION";
        public static final String NAME = "I_NAME";
        public static final String DESCRIPTION = "I_DESCRIPTION";
        public static final String PART_DETAILS_ID = "I_PART_DETAILS_ID";
        public static final String USER_ID = "I_USER_ID";
        public static final String CUR_PART_STATUS = "I_CUR_PART_STATUS";
        public static final String QUANTITY_USED = "I_QUANTITY_USED";
        public static final String IS_SPECIAL_PART = "I_IS_SPECIAL_PART";

    }
}

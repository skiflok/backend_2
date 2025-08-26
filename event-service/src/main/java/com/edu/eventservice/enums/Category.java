package com.edu.eventservice.enums;

import lombok.Getter;

@Getter
public enum Category {
    ELECTRONICS("Электроника"),
    CLOTHING("Одежда и обувь"),
    COSMETICS("Косметика и парфюмерия"),
    HOME_AND_GARDEN("Дом и сад"),
    SPORTS_AND_OUTDOORS("Спорт и активный отдых");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

}

package com.interswitch.bookstore.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre {
    FICTION, THRILLER, MYSTERY, POETRY, HORROR, SATIRE;



//    @JsonCreator
//    public static Genre validateGen(String value) {
//        for (Genre genre : Genre.values()) {
//            if (genre.name().equalsIgnoreCase(value)) {
//                return genre;
//            }
//        }
//        throw new IllegalArgumentException("Invalid genre. Allowed values: FICTION, THRILLER, MYSTERY, POETRY, HORROR, SATIRE.");
//    }

    @JsonValue
    public String toString() {
        return name();
    }
}


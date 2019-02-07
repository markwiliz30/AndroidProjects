package com.example.user.androidfire;

import java.lang.reflect.Type;

public class Types {
    private String typeId;
    private String typeName;
    private int typeRating;

    public Types(){

    }

    public Types(String typeId, String typeName, int typeRating) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeRating = typeRating;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getTypeRating() {
        return typeRating;
    }
}

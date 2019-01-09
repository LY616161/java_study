package com.so.mybatis.commom.Enum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MovieType {
    NA("",""),
    ACTOIN("action","动作片"),
    SCIENCE("science","科幻片");
    private final String en;
    private final String ch;

    MovieType(String en, String ch) {
        this.en = en;
        this.ch = ch;
    }
//    @JsonValue
    public String getEn() {
        return en;
    }
    @JsonValue
    public String getCh() {
        return ch;
    }

    public static MovieType str2Enum(String movieType) {
        MovieType[] types = MovieType.values();
        for (MovieType type : types) {
            if (type.getEn().equals(movieType)) {
                return type;
            }
        }
        return MovieType.NA;
    }
}

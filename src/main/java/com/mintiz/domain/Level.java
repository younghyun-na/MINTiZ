package com.mintiz.domain;

public enum Level {

    반민초단("0"), 민초입문단("1"), 민초린이("2"), 민초른이("3"), 민초러버("4"), 민초마스터("5");

    private final String value;

    Level(String value) {
        this.value = value;
    }

    public static Level findByLevel(String value) {
        for(Level level : Level.values()) {
            if(level.equals(value)) {
                return level;
            }
        }
        throw new RuntimeException();
    }
    /*
    public int getLevel() {
        return level;
    }
    */

    /*
    public static Level valueOf(int level) {
        switch(level) {
            case 0: return 반민초단;
            case 1: return 민초입문단;
            case 2: return 민초린이;
            case 3: return 민초른이;
            case 4: return 민초러버;
            case 5: return 민초마스터;
            default: throw new AssertionError("Unknown value: " + level);
        }
    }
    */

}

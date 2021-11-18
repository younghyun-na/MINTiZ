package com.mintiz.domain;

public enum Level {

    반민초단(0), 민초입문단(1), 민초린이(2), 민초른이(3), 민초러버(4), 민초마스터(5);
    private final int level;

    Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}

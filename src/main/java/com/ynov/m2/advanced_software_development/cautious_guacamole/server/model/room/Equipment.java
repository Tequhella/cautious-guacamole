package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room;

public enum Equipment {

    VIDEO_PROJECTOR(1),
    BOARD(2),
    PRINTER(4),
    TV(8);

    private final int value;

    Equipment(int value) {
        this.value = value;
    }

    public static boolean hasFlag(int flags, Equipment equipment) {
        return (flags & equipment.getValue()) == equipment.getValue();
    }

    private int getValue() {
        return this.value;
    }

}

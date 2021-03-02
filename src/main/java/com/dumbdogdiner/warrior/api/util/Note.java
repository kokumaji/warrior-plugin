package com.dumbdogdiner.warrior.api.util;

import lombok.Getter;

public enum Note {
    F1_SHARP(0.5),
    G1(0.529732),
    G1_SHARP(0.561231),
    A1(0.594604),
    A1_SHARP(0.629961),
    B1(0.667420),
    C2(0.707107),
    C2_SHARP(0.749154),
    D2(0.793701),
    D2_SHARP(0.840896),
    E2(0.890899),
    F2(0.943874),
    F2_SHARP(1),
    G2(1.059463),
    G2_SHARP(1.122462),
    A2(1.189207),
    A2_SHARP(1.259921),
    B2(1.334840),
    C3(1.414214),
    C3_SHARP(1.498307),
    D3(1.587401),
    D3_SHARP(1.681793),
    E3(1.781797),
    F3(1.887749),
    F3_SHARP(2);

    @Getter
    private final double pitch;

    Note(double pitch) {
        this.pitch = pitch;
    }
}

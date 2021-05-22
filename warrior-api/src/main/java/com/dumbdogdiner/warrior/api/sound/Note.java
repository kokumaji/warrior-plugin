package com.dumbdogdiner.warrior.api.sound;

import lombok.Getter;

public enum Note {
    F1_SHARP(0.5, 0),
    G1(0.529732, 0.042),
    G1_SHARP(0.561231, 0.083),
    A1(0.594604, 0.125),
    A1_SHARP(0.629961, 0.167),
    B1(0.667420, 0.208),
    C2(0.707107, 0.250),
    C2_SHARP(0.749154, 0.292),
    D2(0.793701, 0.333),
    D2_SHARP(0.840896, 0.375),
    E2(0.890899, 0.417),
    F2(0.943874, 0.458),
    F2_SHARP(1, 0.5),
    G2(1.059463, 0.542),
    G2_SHARP(1.122462, 0.583),
    A2(1.189207, 0.625),
    A2_SHARP(1.259921, 0.667),
    B2(1.334840, 0.708),
    C3(1.414214, 0.750),
    C3_SHARP(1.498307, 0.792),
    D3(1.587401, 0.833),
    D3_SHARP(1.681793, 0.875),
    E3(1.781797, 0.917),
    F3(1.887749, 0.958),
    F3_SHARP(2, 1);

    @Getter
    private final double pitch;

    @Getter
    private final double colorDelta;

    Note(double pitch, double colorDelta) {
        this.pitch = pitch;
        this.colorDelta = colorDelta;
    }
}

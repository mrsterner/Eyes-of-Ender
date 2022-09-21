package dev.mrsterner.eyesofender.api.enums;

public enum HamonAbilityType {
    ACTIVE, //On key press
    PASSIVE, //On Server tick
    CHARGED, //TODO On Hit after use
    JUMP, //TODO On Jump
    HIT, //TODO On multiple Hits
    TIMED, //On removed after timer
    CROUCH; //On removed after timer while crouching
}

package dev.mrsterner.eyesofender.api.enums;

import java.util.Locale;


public enum BodyPart {
    HEAD(),
    TORSO(),
    LEFTARM(),
    RIGHTARM(),
    LEFTLEG(),
    RIGHTLEG(),
    EYES();


    BodyPart(){

    }

    @Override
    public String toString() {
        return "body."+name().toLowerCase(Locale.ROOT);
    }

    public BodyPart fromString(String string){
        return string.equals("head") ? HEAD :
                        string.equals("torso") ? TORSO :
                                string.equals("leftarm") ? LEFTARM :
                                        string.equals("rightarm") ? RIGHTARM :
                                                string.equals("leftleg") ? LEFTLEG :
                                                        string.equals("rightleg") ? RIGHTLEG :
                                                                        EYES;
    }

}
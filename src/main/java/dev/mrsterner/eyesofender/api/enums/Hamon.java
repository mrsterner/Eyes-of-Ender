package dev.mrsterner.eyesofender.api.enums;

public enum Hamon {
    EMPTY(0),
    BASIC(1),
    INTERMEDIATE(2),
    ADVANCED(3),
    MASTER(4);

    Hamon(int value){
        this.value = value;
    }

    private int value;

    public Hamon getNext(Hamon h){
        int index = h.ordinal();
        int nextIndex = index + 1;
        Hamon[] hamon = Hamon.values();
        nextIndex %= hamon.length;
        return hamon[nextIndex];
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

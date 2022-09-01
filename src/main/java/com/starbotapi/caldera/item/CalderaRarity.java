package com.starbotapi.caldera.item;

public enum CalderaRarity {

    COMMON,UNCOMMON,RARE,EPIC,LEGENDARY,FABLED,MYTHIC,SPECIAL;

    public String color() {
        switch (this) {
            case COMMON:
                return "\2477";
            case UNCOMMON:
                return "\2472";
            case RARE:
                return "\2479";
            case EPIC:
                return "\2475";
            case LEGENDARY:
                return "\2476";
            case FABLED:
                return "\247c";
            case MYTHIC:
                return "\247b";
            case SPECIAL:
                return "\2473";
            default:
                return "\247f";
        }
    }

    public String display() {
        switch (this) {
            case COMMON:
                return "\2477Common";
            case UNCOMMON:
                return "\2472Uncommon";
            case RARE:
                return "\2479Rare";
            case EPIC:
                return "\2475Epic";
            case LEGENDARY:
                return "\2476Legendary";
            case FABLED:
                return "\247cFabled";
            case MYTHIC:
                return "\247bMythic";
            case SPECIAL:
                return "\2473Special";
            default:
                return "\247fUnknown";
        }
    }

}

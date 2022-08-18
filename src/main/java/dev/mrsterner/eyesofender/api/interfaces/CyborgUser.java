package dev.mrsterner.eyesofender.api.interfaces;

import java.util.Optional;

public interface CyborgUser {
    static Optional<CyborgUser> of(Object context) {
        if (context instanceof CyborgUser) {
            return Optional.of(((CyborgUser) context));
        }
        return Optional.empty();
    }

    int getEnergy();

    void setEnergy(int amount);

}

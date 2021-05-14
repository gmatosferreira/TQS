package pt.tqsua.homework.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AwarenessLevel {

    @JsonProperty("green")  GREEN("Verde"),
    @JsonProperty("yellow") YELLOW("Amarelo"),
    @JsonProperty("orange") ORANGE("Laranja"),
    @JsonProperty("red") RED("Vermelho"),
    VERDE("Verde"),
    AMARELO("Amarelo"),
    LARANJA("Laranja"),
    VERMELHO("Vermelho");

    private final String name;

    AwarenessLevel(final String name) {
        this.name = name;
    }

    public String getName() {
        return name.toUpperCase();
    }

    @Override
    public String toString() {
        return name.toUpperCase();
    }
}

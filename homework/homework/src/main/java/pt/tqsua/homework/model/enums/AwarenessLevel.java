package pt.tqsua.homework.model.enums;

public enum AwarenessLevel {

    green("Verde"),
    yellow("Amarelo"),
    orange("Laranja"),
    red("Vermelho");

    private final String name;

    AwarenessLevel(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

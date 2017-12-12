package httpserver;

public class Parameter {
    private final String key;
    private final String value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Parameter) {
            Parameter other = (Parameter)object;
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
        return false;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

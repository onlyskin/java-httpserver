package httpserver;

public class Header {
    private String key;
    private String value;

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Header) {
            Header other = (Header)object;
            return this.key == other.key && this.value == other.value;
        }
        return false;
    }
}

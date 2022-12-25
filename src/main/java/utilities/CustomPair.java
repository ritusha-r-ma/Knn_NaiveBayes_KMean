package utilities;

/**
 * Custom pair class to store String key and value as a pair
 */
public class CustomPair {
    String key;
    String value;

    public CustomPair(String firstValue, String secondValue) {
        key = firstValue;
        value = secondValue;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

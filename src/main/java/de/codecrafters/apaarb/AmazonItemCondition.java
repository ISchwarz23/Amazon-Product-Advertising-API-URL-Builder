package de.codecrafters.apaarb;

/**
 * Created by Ingo on 08.10.2016.
 */
public enum AmazonItemCondition {

    ALL("All"),
    NEW("New"),
    USED("Used"),
    REFURBISHED("Refurbished"),
    COLLECTIBLE("Collectible");

    private final String requestValue;

    AmazonItemCondition(final String requestValue) {
        this.requestValue = requestValue;
    }

    public String getRequestValue() {
        return requestValue;
    }

    public static AmazonItemCondition parse(final String textContent) {
        if (ALL.toString().equalsIgnoreCase(textContent)) {
            return ALL;
        } else if (NEW.toString().equalsIgnoreCase(textContent)) {
            return NEW;
        } else if (USED.toString().equalsIgnoreCase(textContent)) {
            return USED;
        } else if (REFURBISHED.toString().equalsIgnoreCase(textContent)) {
            return REFURBISHED;
        } else if (COLLECTIBLE.toString().equalsIgnoreCase(textContent)) {
            return COLLECTIBLE;
        }
        return null;
    }

}

package de.codecrafters.apaarb;

/**
 * Created by Ingo on 08.10.2016.
 */
public final class AmazonItemId {

    public enum Type {

        ASIN("ASIN"),
        ISBN("ISBN"),
        UPC("UPC"),
        EAN("EAN");

        private final String requestValue;

        Type(final String requestValue) {
            this.requestValue = requestValue;
        }

        public String getRequestValue() {
            return requestValue;
        }
    }

    private final String value;
    private final Type type;

    public static AmazonItemId createAsin(final String asinValue) {
        return new AmazonItemId(asinValue, Type.ASIN);
    }

    public static AmazonItemId createEan(final String eanValue) {
        return new AmazonItemId(eanValue, Type.EAN);
    }

    public static AmazonItemId createIsbn(final String isbnValue) {
        return new AmazonItemId(isbnValue, Type.ISBN);
    }

    public static AmazonItemId createUpc(final String upcValue) {
        return new AmazonItemId(upcValue, Type.UPC);
    }

    public static AmazonItemId create(final String idValue, final Type idType) {
        return new AmazonItemId(idValue, idType);
    }

    private AmazonItemId(final String value, final Type type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AmazonItemId id = (AmazonItemId) o;
        return value.equals(id.value) && type == id.type;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Id{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }


}

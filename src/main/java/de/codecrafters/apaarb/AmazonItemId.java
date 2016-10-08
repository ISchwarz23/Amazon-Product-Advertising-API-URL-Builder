package de.codecrafters.apaarb;

/**
 * Value-based data class representing the ID of an Amazon item. It supports all ID-types available at Amazon.
 *
 * @author ISchwarz
 */
public final class AmazonItemId {

    /**
     * The {@link AmazonItemId.Type} contains all available IDs managed by amazon.
     *
     * @author ISchwarz
     */
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

    private AmazonItemId(final String value, final Type type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Creates an {@link AmazonItemId} of type ASIN with the given value.
     *
     * @param asinValue The ASIN value of the {@link AmazonItemId}.
     * @return The created {@link AmazonItemId} of type ASIN.
     */
    public static AmazonItemId createAsin(final String asinValue) {
        return create(asinValue, Type.ASIN);
    }

    /**
     * Creates an {@link AmazonItemId} of type EAN with the given value.
     *
     * @param eanValue The EAN value of the {@link AmazonItemId}.
     * @return The created {@link AmazonItemId} of type EAN.
     */
    public static AmazonItemId createEan(final String eanValue) {
        return create(eanValue, Type.EAN);
    }

    /**
     * Creates an {@link AmazonItemId} of type ISBN with the given value.
     *
     * @param isbnValue The ISBN value of the {@link AmazonItemId}.
     * @return The created {@link AmazonItemId} of type ISBN.
     */
    public static AmazonItemId createIsbn(final String isbnValue) {
        return create(isbnValue, Type.ISBN);
    }

    /**
     * Creates an {@link AmazonItemId} of type UPC with the given value.
     *
     * @param upcValue The UPC value of the {@link AmazonItemId}.
     * @return The created {@link AmazonItemId} of type UPC.
     */
    public static AmazonItemId createUpc(final String upcValue) {
        return create(upcValue, Type.UPC);
    }

    /**
     * Creates an {@link AmazonItemId} of given type with the given value.
     *
     * @param idValue The value of the {@link AmazonItemId}.
     * @param idType  The type of the {@link AmazonItemId}.
     * @return The created {@link AmazonItemId} of the given type.
     */
    static AmazonItemId create(final String idValue, final Type idType) {
        return new AmazonItemId(idValue, idType);
    }

    /**
     * Gives the value of this {@link AmazonItemId}.
     *
     * @return The value of this {@link AmazonItemId}.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gives the type of the {@link AmazonItemId}.
     *
     * @return The type of the {@link AmazonItemId}.
     */
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
        return "AmazonItemId{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }


}

package de.codecrafters.apaarb;

/**
 * A value-based data class representing the ID of an Amazon item. It supports all ID-types available at Amazon.
 *
 * @author ISchwarz
 */
public final class ItemId {

    /**
     * The {@link ItemId.Type} contains all available IDs managed by amazon.
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

        /**
         * Gives the value used in the URL request that represents this ID type.
         *
         * @return The value used in the URL request that represents this ID type.
         */
        String getRequestValue() {
            return requestValue;
        }
    }

    private final String value;
    private final Type type;

    private ItemId(final String value, final Type type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Creates an {@link ItemId} of type ASIN with the given value.
     *
     * @param asinValue The ASIN value of the {@link ItemId}.
     * @return The created {@link ItemId} of type ASIN.
     */
    public static ItemId createAsin(final String asinValue) {
        return create(asinValue, Type.ASIN);
    }

    /**
     * Creates an {@link ItemId} of type EAN with the given value.
     *
     * @param eanValue The EAN value of the {@link ItemId}.
     * @return The created {@link ItemId} of type EAN.
     */
    public static ItemId createEan(final String eanValue) {
        return create(eanValue, Type.EAN);
    }

    /**
     * Creates an {@link ItemId} of type ISBN with the given value.
     *
     * @param isbnValue The ISBN value of the {@link ItemId}.
     * @return The created {@link ItemId} of type ISBN.
     */
    public static ItemId createIsbn(final String isbnValue) {
        return create(isbnValue, Type.ISBN);
    }

    /**
     * Creates an {@link ItemId} of type UPC with the given value.
     *
     * @param upcValue The UPC value of the {@link ItemId}.
     * @return The created {@link ItemId} of type UPC.
     */
    public static ItemId createUpc(final String upcValue) {
        return create(upcValue, Type.UPC);
    }

    /**
     * Creates an {@link ItemId} of given type with the given value.
     *
     * @param idValue The value of the {@link ItemId}.
     * @param idType  The type of the {@link ItemId}.
     * @return The created {@link ItemId} of the given type.
     */
    static ItemId create(final String idValue, final Type idType) {
        return new ItemId(idValue, idType);
    }

    /**
     * Gives the value of this {@link ItemId}.
     *
     * @return The value of this {@link ItemId}.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gives the type of the {@link ItemId}.
     *
     * @return The type of the {@link ItemId}.
     */
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemId id = (ItemId) o;
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

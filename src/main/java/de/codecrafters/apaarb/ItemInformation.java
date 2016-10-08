package de.codecrafters.apaarb;

/**
 * A listing that contains all item information available at Amazon.
 *
 * @author ISchwarz
 */
public enum ItemInformation {

    IMAGES("Images"),
    ITEM_ATTRIBUTES("ItemAttributes"),
    OFFERS("Offers"),
    ACCESSORIES("Accessories"),
    ALTERNATIVE_VERSIONS("AlternativeVersions"),
    BROWSE_NODES("BrowseNodes"),
    EDITORIAL_REVIEW("EditorialReview"),
    OFFER_FULL("OfferFull"),
    OFFER_SUMMARY("OfferSummary"),
    REVIEWS("Reviews"),
    SALES_RANK("SalesRank"),
    SIMILARITIES("Similarities"),
    TRACKS("Tracks"),
    VARIATION_IMAGES("VariationImages"),
    VARIATION_MATRIX("VariationMatrix"),
    VARIATION_SUMMARY("VariationSummary"),
    VARIATIONS("Variations");

    private final String requestValue;

    ItemInformation(final String requestValue) {
        this.requestValue = requestValue;
    }

    /**
     * Gives the value used in the URL request that represents this item information.
     *
     * @return The value used in the URL request that represents this item information.
     */
    String getRequestValue() {
        return requestValue;
    }
}

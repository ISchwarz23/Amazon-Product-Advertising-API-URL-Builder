package de.codecrafters.apaarb;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A builder that simplifies the creation of URLs for requesting the Amazon Product Advertising API.
 *
 * @author ISchwarz
 */
public final class AmazonProductAdvertisingApiRequestBuilder {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String DATE_FORMATTER_TIME_ZONE = "GMT";
    private static final String SERVICE = "AWSECommerceService";
    private static final String VERSION = "2011-08-01";

    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String ROUTE = "/onca/xml";

    static {
        DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(DATE_FORMATTER_TIME_ZONE));
    }


    private AmazonProductAdvertisingApiRequestBuilder() {
        //no instance
    }

    /**
     * Creates an {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     *
     * @param itemId     The ID of the item to get the information for.
     * @param itemIdType The ID type of the given ID.
     * @return A new {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     */
    public static AdvertisingApiItemLookupRequestBuilder forItemLookup(final String itemId, final ItemId.Type itemIdType) {
        return forItemLookup(ItemId.create(itemId, itemIdType));
    }

    /**
     * Creates an {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     *
     * @param itemId The ID of the item to get the information for.
     * @return A new {@link AdvertisingApiItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     */
    public static AdvertisingApiItemLookupRequestBuilder forItemLookup(final ItemId itemId) {
        return new AdvertisingApiItemLookupRequestBuilder(itemId);
    }

    /**
     * Creates an {@link AdvertisingApiItemSearchRequestBuilder} for creating an ItemSearch request for the item matching the given keywords.
     *
     * @param keywords The keywords that shall be used to search for items.
     * @return A new {@link AdvertisingApiItemSearchRequestBuilder} for creating an ItemSearch request for the item matching the given keywords.
     */
    public static AdvertisingApiItemSearchRequestBuilder forItemSearch(final String keywords) {
        return new AdvertisingApiItemSearchRequestBuilder(keywords);
    }

    /**
     * A builder that simplifies the creation of URLs for ItemSearch requests.
     *
     * @author ISchwarz
     */
    public static final class AdvertisingApiItemSearchRequestBuilder {

        private static final String OPERATION = "ItemSearch";

        private final List<ItemInformation> responseGroup = new ArrayList<ItemInformation>();
        private final String keywords;

        private ItemCondition itemCondition = ItemCondition.ALL;
        private ItemCategory itemCategory = ItemCategory.ALL;
        private int maximumPrice = -1;
        private int minimumPrice = -1;


        private AdvertisingApiItemSearchRequestBuilder(final String keywords) {
            this.keywords = keywords;
        }

        /**
         * Sets the {@link ItemCondition} to filter the result of the returned items.
         *
         * @param itemCondition The {@link ItemInformation} to filter the result of the returned items.
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}.
         */
        public AdvertisingApiItemSearchRequestBuilder withConditionFilter(final ItemCondition itemCondition) {
            this.itemCondition = itemCondition;
            return this;
        }

        /**
         * Adds the given {@link ItemInformation} to the response group.
         *
         * @param itemInformation The {@link ItemInformation} that shall be added to the response group.
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}.
         */
        public AdvertisingApiItemSearchRequestBuilder withInfoAbout(final ItemInformation itemInformation) {
            responseGroup.add(itemInformation);
            return this;
        }

        /**
         * Specifies the {@link ItemCategory} that will be searched.
         *
         * @param itemCategory The {@link ItemCategory} that will be searched.
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}.
         */
        public AdvertisingApiItemSearchRequestBuilder withCategoryFilter(final ItemCategory itemCategory) {
            this.itemCategory = itemCategory;
            return this;
        }

        /**
         * Specifies the maximum item price in the response. Prices appear in the lowest currency denomination.
         * For example, 3241 is $32.41.
         *
         * @param maximumPrice The maximum item price in the lowest currency denomination.
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}.
         */
        public AdvertisingApiItemSearchRequestBuilder withMaximumPrice(final int maximumPrice) {
            this.maximumPrice = maximumPrice;
            return this;
        }

        /**
         * Specifies the minimum item price in the response. Prices appear in the lowest currency denomination.
         * For example, 3241 is $32.41.
         *
         * @param minimumPrice The minimum item price in the lowest currency denomination.
         * @return The current {@link AdvertisingApiItemSearchRequestBuilder}.
         */
        public AdvertisingApiItemSearchRequestBuilder withMinimumPrice(final int minimumPrice) {
            this.minimumPrice = minimumPrice;
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonWebServiceAuthentication}.
         *
         * @param serviceLocation The location of the Amazon service that shall be used.
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used.
         * @return The created signed request url.
         */
        public String createRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                       final AmazonWebServiceAuthentication authentication) {

            return createRequestUrl(serviceLocation, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}.
         *
         * @param serviceLocation The location of the Amazon service that shall be used.
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used.
         * @return The created signed request url.
         */
        public String createSecureRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                             final AmazonWebServiceAuthentication authentication) {

            return createRequestUrl(serviceLocation, authentication, HTTPS_PROTOCOL);
        }

        private String createRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                        final AmazonWebServiceAuthentication authentication, final String protocol) {

            final Map<String, String> requestParams = new LinkedHashMap<String, String>();
            requestParams.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestParams.put("AssociateTag", authentication.getAssociateTag());
            requestParams.put("Condition", itemCondition.getRequestValue());
            requestParams.put("Keywords", keywords);
            requestParams.put("Operation", OPERATION);
            requestParams.put("ResponseGroup", createResponseGroupRequestValue(responseGroup));
            requestParams.put("SearchIndex", itemCategory.getRequestValue());
            requestParams.put("Service", SERVICE);
            requestParams.put("Timestamp", DATE_FORMATTER.format(new Date()));
            requestParams.put("Version", VERSION);
            if (maximumPrice != -1) {
                requestParams.put("MaximumPrice", "" + maximumPrice);
            }
            if (minimumPrice != -1) {
                requestParams.put("MinimumPrice", "" + minimumPrice);
            }

            return RequestUrlUtils.createSignature(protocol, serviceLocation.getWebServiceUrl(), ROUTE, requestParams,
                    authentication.getAwsSecretKey());
        }
    }

    /**
     * A builder that simplifies the creation of URLs for ItemLookup requests.
     *
     * @author ISchwarz
     */
    public static final class AdvertisingApiItemLookupRequestBuilder {

        private static final String OPERATION = "ItemLookup";

        private final List<ItemInformation> responseGroup = new ArrayList<ItemInformation>();
        private final ItemId itemId;

        private ItemCondition itemCondition = ItemCondition.ALL;


        private AdvertisingApiItemLookupRequestBuilder(final ItemId itemId) {
            this.itemId = itemId;
        }

        /**
         * Adds the given {@link ItemInformation} to the response group.
         *
         * @param itemInformation The {@link ItemInformation} that shall be added to the response group.
         * @return The current {@link AdvertisingApiItemLookupRequestBuilder}.
         */
        public AdvertisingApiItemLookupRequestBuilder withInfoAbout(final ItemInformation itemInformation) {
            responseGroup.add(itemInformation);
            return this;
        }

        /**
         * Sets the {@link ItemCondition} to filter the result of the returned items.
         *
         * @param itemCondition The {@link ItemInformation} to filter the result of the returned items.
         * @return The current {@link AdvertisingApiItemLookupRequestBuilder}.
         */
        public AdvertisingApiItemLookupRequestBuilder withConditionFilter(final ItemCondition itemCondition) {
            this.itemCondition = itemCondition;
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonWebServiceAuthentication}.
         *
         * @param serviceLocation The location of the Amazon service that shall be used.
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used.
         * @return The created signed request url.
         */
        public String createRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                       final AmazonWebServiceAuthentication authentication) {

            return createRequestUrl(serviceLocation, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonWebServiceAuthentication}.
         *
         * @param serviceLocation The location of the Amazon service that shall be used.
         * @param authentication  The {@link AmazonWebServiceAuthentication} that shall be used.
         * @return The created signed request url.
         */
        public String createSecureRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                             final AmazonWebServiceAuthentication authentication) {

            return createRequestUrl(serviceLocation, authentication, HTTPS_PROTOCOL);
        }

        private String createRequestUrl(final AmazonWebServiceLocation serviceLocation,
                                        final AmazonWebServiceAuthentication authentication, final String protocol) {

            final Map<String, String> requestParams = new LinkedHashMap<String, String>();
            requestParams.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestParams.put("AssociateTag", authentication.getAssociateTag());
            requestParams.put("Condition", itemCondition.getRequestValue());
            requestParams.put("IdType", itemId.getType().getRequestValue());
            requestParams.put("ItemId", itemId.getValue());
            requestParams.put("Operation", OPERATION);
            requestParams.put("ResponseGroup", createResponseGroupRequestValue(responseGroup));
            requestParams.put("Service", SERVICE);
            requestParams.put("Timestamp", DATE_FORMATTER.format(new Date()));
            requestParams.put("Version", VERSION);

            return RequestUrlUtils.createSignature(protocol, serviceLocation.getWebServiceUrl(), ROUTE, requestParams,
                    authentication.getAwsSecretKey());
        }
    }

    private static String createResponseGroupRequestValue(final List<ItemInformation> responseGroup) {
        // add item attributes to response group if none was selected
        if (responseGroup.size() == 0) {
            responseGroup.add(ItemInformation.ATTRIBUTES);
        }

        String responseGroupRequestValue = "";
        for (int i = 0; i < responseGroup.size(); i++) {
            if (i != 0) {
                responseGroupRequestValue += ",";
            }
            responseGroupRequestValue += responseGroup.get(i).getRequestValue();
        }
        return responseGroupRequestValue;
    }
}

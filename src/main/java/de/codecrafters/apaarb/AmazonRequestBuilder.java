package de.codecrafters.apaarb;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A builder that simplifies the creation of URLs for requesting the Amazon Product Advertising API.
 *
 * @author ISchwarz
 */
public final class AmazonRequestBuilder {

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

        private String getRequestValue() {
            return requestValue;
        }
    }

    /**
     * Creates an {@link AmazonItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     *
     * @param itemId     The ID of the item to get the information for.
     * @param itemIdType The ID type of the given ID.
     * @return A new {@link AmazonItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     */
    public static AmazonItemLookupRequestBuilder forItemLookup(final String itemId, final AmazonItemId.Type itemIdType) {
        return forItemLookup(AmazonItemId.create(itemId, itemIdType));
    }

    /**
     * Creates an {@link AmazonItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     *
     * @param itemId The ID of the item to get the information for.
     * @return A new {@link AmazonItemLookupRequestBuilder} for creating an ItemLookup request for the item with the given ID.
     */
    public static AmazonItemLookupRequestBuilder forItemLookup(final AmazonItemId itemId) {
        return new AmazonItemLookupRequestBuilder(itemId);
    }

    /**
     * A builder that simplifies the creation of URLs for ItemLookup requests.
     *
     * @author ISchwarz
     */
    public static final class AmazonItemLookupRequestBuilder {

        private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private static final String DATE_FORMATTER_TIME_ZONE = "GMT";

        private static final String HTTP_PROTOCOL = "http://";
        private static final String HTTPS_PROTOCOL = "https://";
        private static final String ROUTE = "/onca/xml";
        private static final String VERSION = "2011-08-01";
        private static final String SERVICE = "AWSECommerceService";
        private static final String OPERATION = "ItemLookup";

        private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
        private static final String UTF8_CHARSET = "UTF-8";

        private final List<ItemInformation> responseGroup = new ArrayList<>();
        private final AmazonItemId itemId;

        private AmazonItemCondition itemCondition = AmazonItemCondition.ALL;

        private AmazonItemLookupRequestBuilder(final AmazonItemId itemId) {
            this.itemId = itemId;
            DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(DATE_FORMATTER_TIME_ZONE));
        }

        /**
         * Adds the given {@link ItemInformation} to the response group.
         *
         * @param itemInformation The {@link ItemInformation} that shall be added to the response group.
         * @return The current {@link AmazonItemLookupRequestBuilder}.
         */
        public AmazonItemLookupRequestBuilder withInfoAbout(final ItemInformation itemInformation) {
            responseGroup.add(itemInformation);
            return this;
        }

        /**
         * Sets the {@link AmazonItemCondition} to filter the result of the returned items.
         *
         * @param itemCondition The {@link ItemInformation} to filter the result of the returned items.
         * @return The current {@link AmazonItemLookupRequestBuilder}.
         */
        public AmazonItemLookupRequestBuilder withCondition(final AmazonItemCondition itemCondition) {
            this.itemCondition = itemCondition;
            return this;
        }

        /**
         * Creates the signed request http-url for the given service using the given {@link AmazonAuthenticationInformation}.
         *
         * @param amazonServiceUrl The url of the Amazon service that shall be used.
         * @param authentication   The {@link AmazonAuthenticationInformation} that shall be used.
         * @return The created signed request url.
         */
        public String createRequestUrl(final String amazonServiceUrl, final AmazonAuthenticationInformation authentication) {
            return createRequestUrl(amazonServiceUrl, authentication, HTTP_PROTOCOL);
        }

        /**
         * Creates the signed request https-url for the given service using the given {@link AmazonAuthenticationInformation}.
         *
         * @param amazonServiceUrl The url of the Amazon service that shall be used.
         * @param authentication   The {@link AmazonAuthenticationInformation} that shall be used.
         * @return The created signed request url.
         */
        public String createSecureRequestUrl(final String amazonServiceUrl, final AmazonAuthenticationInformation authentication) {
            return createRequestUrl(amazonServiceUrl, authentication, HTTPS_PROTOCOL);
        }

        private String createRequestUrl(final String amazonServiceUrl, final AmazonAuthenticationInformation authentication, final String protocol) {
            final Map<String, String> requestPairs = new LinkedHashMap<>();
            requestPairs.put("AWSAccessKeyId", authentication.getAwsAccessKey());
            requestPairs.put("AssociateTag", authentication.getAssociateTag());
            requestPairs.put("Condition", itemCondition.getRequestValue());
            requestPairs.put("IdType", itemId.getType().getRequestValue());
            requestPairs.put("ItemId", itemId.getValue());
            requestPairs.put("Operation", OPERATION);
            requestPairs.put("ResponseGroup", createResponseGroupRequestValue());
            requestPairs.put("Service", SERVICE);
            requestPairs.put("Timestamp", DATE_FORMATTER.format(new Date()));
            requestPairs.put("Version", VERSION);
            return createSignedUrl(amazonServiceUrl, requestPairs, authentication.getAwsSecretKey(), protocol);
        }

        private String createResponseGroupRequestValue() {
            // add item attributes to response group if none was selected
            if (responseGroup.size() == 0) {
                responseGroup.add(ItemInformation.ITEM_ATTRIBUTES);
            }

            // build the response group request value from the list
            String responseGroupRequestValue = "";
            for (int i = 0; i < responseGroup.size(); i++) {
                if (i != 0) {
                    responseGroupRequestValue += ",";
                }
                responseGroupRequestValue += responseGroup.get(i).getRequestValue();
            }
            return responseGroupRequestValue;
        }

        private String createSignedUrl(final String amazonServiceUrl, final Map<String, String> requestPairs, final String key, final String protocol) {
            // The parameters need to be processed in lexicographical order, so we'll
            // use a TreeMap implementation for that.
            final SortedMap<String, String> sortedParamMap = new TreeMap<>(requestPairs);

            // get the canonical form the query string
            final String canonicalQS = canonicalizeParameters(sortedParamMap);

            // create the string upon which the signature is calculated
            final String toSign = "GET" + "\n"
                    + amazonServiceUrl + "\n"
                    + ROUTE + "\n"
                    + canonicalQS;

            // get the signature
            final String hmac = hmac(toSign, key);
            final String sig = percentEncodeRfc3986(hmac);

            // construct the URL
            return protocol + amazonServiceUrl + ROUTE + "?" + canonicalQS + "&Signature=" + sig;
        }

        private String hmac(final String stringToSign, final String awsSecretKey) {
            String signature = null;
            final byte[] data;
            final byte[] rawHmac;
            try {
                data = stringToSign.getBytes(UTF8_CHARSET);
                final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
                final byte[] secretKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
                final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, HMAC_SHA256_ALGORITHM);
                mac.init(secretKeySpec);
                rawHmac = mac.doFinal(data);
                final Base64.Encoder encoder = Base64.getEncoder();
                signature = new String(encoder.encode(rawHmac));
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException(UTF8_CHARSET + " is not supported!", e);
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
            }
            return signature;
        }

        private String canonicalizeParameters(final SortedMap<String, String> sortedParamMap) {
            if (sortedParamMap.isEmpty()) {
                return "";
            }

            final StringBuilder buffer = new StringBuilder();
            final Iterator<Map.Entry<String, String>> paramIterator = sortedParamMap.entrySet().iterator();

            while (paramIterator.hasNext()) {
                final Map.Entry<String, String> keyValuePair = paramIterator.next();
                buffer.append(percentEncodeRfc3986(keyValuePair.getKey()));
                buffer.append("=");
                buffer.append(percentEncodeRfc3986(keyValuePair.getValue()));
                if (paramIterator.hasNext()) {
                    buffer.append("&");
                }
            }
            return buffer.toString();
        }

        private String percentEncodeRfc3986(final String s) {
            String out;
            try {
                out = URLEncoder.encode(s, UTF8_CHARSET)
                        .replace("+", "%20")
                        .replace("*", "%2A")
                        .replace("%7E", "~");
            } catch (final UnsupportedEncodingException e) {
                out = s;
            }
            return out;
        }

    }


}

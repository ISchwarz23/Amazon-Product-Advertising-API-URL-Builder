package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Test and usage example of the ItemLookup request url builder using the {@link AmazonProductAdvertisingApiRequestBuilder}.
 *
 * @author ISchwarz
 */
public class ItemLookupTest {

    private static final String ASSOCIATE_TAG = "AssociateTag";
    private static final String AWS_ACCESS_KEY = "AccessKey";
    private static final String AWS_SECRET_KEY = "SecretKey";

    private static final ItemId ITEM_ID = ItemId.createAsin("B01BKISLYC");
    private AmazonWebServiceAuthentication authentication;

    @Before
    public void setUp() throws Exception {
        authentication = AmazonWebServiceAuthentication.create(ASSOCIATE_TAG, AWS_ACCESS_KEY, AWS_SECRET_KEY);
    }

    @Test
    public void shouldCreateRequestUrl() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes");
    }

    @Test
    public void shouldCreateRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "New", "ItemAttributes");
    }

    @Test
    public void shouldCreateRequestUrlWithSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes%2COffers");
    }

    @Test
    public void shouldCreateRequestUrlWithConditionFilterAndSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "New", "ItemAttributes%2COffers");
    }

    @Test
    public void shouldCreateSecureRequestUrl() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "New", "ItemAttributes");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes%2COffers");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithConditionFilterAndSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "New", "ItemAttributes%2COffers");
    }

    private static void checkRequestUrl(final String requestUrl, final String protocol, final String domain,
                                        final String condition, final String responseGroup) {

        assertThat(requestUrl, startsWith(protocol + "://"));
        assertThat(requestUrl, containsString(domain));
        assertThat(requestUrl, containsString("AWSAccessKeyId=" + AWS_ACCESS_KEY));
        assertThat(requestUrl, containsString("AssociateTag=" + ASSOCIATE_TAG));
        assertThat(requestUrl, containsString("Condition=" + condition));
        assertThat(requestUrl, containsString("ItemId=" + ITEM_ID.getValue()));
        assertThat(requestUrl, containsString("IdType=" + ITEM_ID.getType()));
        assertThat(requestUrl, containsString("Operation=ItemLookup"));
        assertThat(requestUrl, containsString("ResponseGroup=" + responseGroup));
        assertThat(requestUrl, containsString("Service=AWSECommerceService"));
        assertThat(requestUrl, containsString("Timestamp="));
        assertThat(requestUrl, containsString("Version=2011-08-01"));
        assertThat(requestUrl, containsString("Signature="));
    }
}

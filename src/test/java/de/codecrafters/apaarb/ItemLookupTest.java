package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Usage example of the ItemLookup request url builder using the {@link AmazonProductAdvertisingApiRequestBuilder}.
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
    public void testRequestUrlCreation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.COM, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }
}

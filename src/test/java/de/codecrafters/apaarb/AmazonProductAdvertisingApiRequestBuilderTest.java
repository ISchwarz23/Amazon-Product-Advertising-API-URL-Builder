package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Usage example of the {@link AmazonProductAdvertisingApiRequestBuilder}.
 *
 * @author ISchwarz
 */
public class AmazonProductAdvertisingApiRequestBuilderTest {

    private AmazonWebServiceAuthentication authentication;
    private ItemId itemId;

    @Before
    public void setUp() throws Exception {
        itemId = ItemId.createAsin("B01BKISLYC");
        authentication = AmazonWebServiceAuthentication.create("sdfsdf", "sdfsdf", "sdfsdf");
    }

    @Test
    public void testRequestUrlCreation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
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
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AmazonWebServiceLocation.COM, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }
}

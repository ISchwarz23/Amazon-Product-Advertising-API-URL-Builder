package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Usage example of the {@link AdvertisingApiRequestBuilder}.
 *
 * @author ISchwarz
 */
public class AdvertisingApiRequestBuilderTest {

    private static final String AMAZON_SERVICE_URL = "webservices.amazon.de";

    private AwsAuthentication authentication;
    private ItemId itemId;

    @Before
    public void setUp() throws Exception {
        itemId = ItemId.createAsin("B01BKISLYC");
        authentication = AwsAuthentication.create("sdfsdf", "sdfsdf", "sdfsdf");
    }

    @Test
    public void testRequestUrlCreation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .createRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .createRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilter() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithSpecialInformation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

    @Test
    public void testSecureRequestUrlCreationWithConditionFilterAndSpecialInformation() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withConditionFilter(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }
}

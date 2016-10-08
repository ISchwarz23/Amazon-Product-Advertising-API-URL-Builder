package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ingo on 08.10.2016.
 */
public class AdvertisingApiRequestBuilderTest {

    private static final String AMAZON_SERVICE_URL = "webservices.amazon.de";

    private AwsAuthentication authentication;
    private ItemId itemId;

    @Before
    public void setUp() throws Exception {
        itemId = ItemId.createAsin("B01BKISLYC");
        authentication = AwsAuthentication.create("sdfg", "sdfsdf", "sdfsdf");
    }

    @Test
    public void testSimpleRequest() throws Exception {
        final String requestUrl = AdvertisingApiRequestBuilder.forItemLookup(itemId)
                .withCondition(ItemCondition.NEW)
                .withInfoAbout(ItemInformation.ITEM_ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .withInfoAbout(ItemInformation.OFFERS)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

}

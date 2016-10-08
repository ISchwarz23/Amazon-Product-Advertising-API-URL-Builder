package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ingo on 08.10.2016.
 */
public class AmazonRequestBuilderTest {

    private static final String AMAZON_SERVICE_URL = "webservices.amazon.de";

    private AmazonAuthenticationInformation authentication;
    private AmazonItemId itemId;

    @Before
    public void setUp() throws Exception {
        itemId = AmazonItemId.createAsin("B01BKISLYC");
        authentication = AmazonAuthenticationInformation.create("sdfg", "sdfsdf", "sdfsdf");
    }

    @Test
    public void testSimpleRequest() throws Exception {
        final String requestUrl = AmazonRequestBuilder.forItemLookup(itemId)
                .withCondition(AmazonItemCondition.NEW)
                .withInfoAbout(AmazonRequestBuilder.ItemInformation.ITEM_ATTRIBUTES)
                .withInfoAbout(AmazonRequestBuilder.ItemInformation.IMAGES)
                .withInfoAbout(AmazonRequestBuilder.ItemInformation.OFFERS)
                .createSecureRequestUrl(AMAZON_SERVICE_URL, authentication);

        System.out.println(requestUrl);
        assertNotNull(requestUrl);
    }

}

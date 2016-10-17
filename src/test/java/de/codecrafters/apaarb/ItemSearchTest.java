package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Usage example of the ItemSearch request url builder using the {@link AmazonProductAdvertisingApiRequestBuilder}.
 *
 * @author ISchwarz
 */
public class ItemSearchTest {

    private static final String ASSOCIATE_TAG = "AssociateTag";
    private static final String AWS_ACCESS_KEY = "AccessKey";
    private static final String AWS_SECRET_KEY = "SecretKey";

    private static final String KEYWORDS = "Dead Pool";
    private AmazonWebServiceAuthentication authentication;

    @Before
    public void setUp() throws Exception {
        authentication = AmazonWebServiceAuthentication.create(ASSOCIATE_TAG, AWS_ACCESS_KEY, AWS_SECRET_KEY);
    }

    @Test
    public void shouldCreateRequestUrl() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withConditionFilter(ItemCondition.USED)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "Used", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithIndexFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withCategoryFilter(ItemCategory.DVD)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "DVD");
    }

    @Test
    public void shouldCreateRequestUrlWithInformationAboutImages() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes%2CImages", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithMinimumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withMinimumPrice(10000)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MinimumPrice=10000"));
    }

    @Test
    public void shouldCreateRequestUrlWithMaximumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withMaximumPrice(10000)
                .createRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MaximumPrice=10000"));
    }

    @Test
    public void shouldCreateSecureRequestUrl() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withConditionFilter(ItemCondition.USED)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "Used", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithIndexFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withCategoryFilter(ItemCategory.DVD)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "DVD");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithInformationAboutImages() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withInfoAbout(ItemInformation.ATTRIBUTES)
                .withInfoAbout(ItemInformation.IMAGES)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes%2CImages", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithMinimumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withMinimumPrice(10000)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MinimumPrice=10000"));
    }

    @Test
    public void shouldCreateSecureRequestUrlWithMaximumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .withMaximumPrice(10000)
                .createSecureRequestUrl(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MaximumPrice=10000"));
    }


    private static void checkRequestUrl(final String requestUrl, final String protocol, final String domain,
                                        final String condition, final String responseGroup, final String searchIndex) {

        assertThat(requestUrl, startsWith(protocol + "://"));
        assertThat(requestUrl, containsString(domain));
        assertThat(requestUrl, containsString("AWSAccessKeyId=" + AWS_ACCESS_KEY));
        assertThat(requestUrl, containsString("AssociateTag=" + ASSOCIATE_TAG));
        assertThat(requestUrl, containsString("Condition=" + condition));
        assertThat(requestUrl, containsString("Keywords=" + KEYWORDS.replaceAll(" ", "%20")));
        assertThat(requestUrl, containsString("Operation=ItemSearch"));
        assertThat(requestUrl, containsString("ResponseGroup=" + responseGroup));
        assertThat(requestUrl, containsString("SearchIndex=" + searchIndex));
        assertThat(requestUrl, containsString("Service=AWSECommerceService"));
        assertThat(requestUrl, containsString("Timestamp="));
        assertThat(requestUrl, containsString("Version=2011-08-01"));
        assertThat(requestUrl, containsString("Signature="));
    }
}

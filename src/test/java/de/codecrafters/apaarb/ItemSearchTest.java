package de.codecrafters.apaarb;

import org.junit.Before;
import org.junit.Test;

import static de.codecrafters.apaarb.ItemCategory.HEALTH_AND_PERSONAL_CARE;
import static de.codecrafters.apaarb.ItemCondition.USED;
import static de.codecrafters.apaarb.ItemInformation.ATTRIBUTES;
import static de.codecrafters.apaarb.ItemInformation.OFFERS;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Test and usage example of the ItemSearch request url builder using the {@link AmazonProductAdvertisingApiRequestBuilder}.
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
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByCondition(USED)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "Used", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithIndexFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByCategroy(HEALTH_AND_PERSONAL_CARE)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "HealthPersonalCare");
    }

    @Test
    public void shouldCreateRequestUrlWithSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes%2COffers", "All");
    }

    @Test
    public void shouldCreateRequestUrlWithMinimumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByMinimumPrice(10000)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MinimumPrice=10000"));
    }

    @Test
    public void shouldCreateRequestUrlWithMaximumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByMaximumPrice(10000)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "http", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MaximumPrice=10000"));
    }

    @Test
    public void shouldCreateSecureRequestUrl() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithConditionFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByCondition(USED)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "Used", "ItemAttributes", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithIndexFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByCategroy(HEALTH_AND_PERSONAL_CARE)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "HealthPersonalCare");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithSpecificInformation() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes%2COffers", "All");
    }

    @Test
    public void shouldCreateSecureRequestUrlWithMinimumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByMinimumPrice(10000)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

        checkRequestUrl(requestUrl, "https", "amazon.de", "All", "ItemAttributes", "All");
        assertThat(requestUrl, containsString("MinimumPrice=10000"));
    }

    @Test
    public void shouldCreateSecureRequestUrlWithMaximumPriceFilter() throws Exception {
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(KEYWORDS)
                .filterByMaximumPrice(10000)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);

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

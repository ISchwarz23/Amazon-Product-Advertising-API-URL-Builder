package de.codecrafters.apaarb;

/**
 * A value-base data object containing the user information needed for authentication at Amazon services.
 *
 * @author ISchwarz
 */
public final class AmazonWebServiceAuthentication {

    private final String associateTag;
    private final String awsAccessKey;
    private final String awsSecretKey;

    private AmazonWebServiceAuthentication(final String associateTag, final String awsAccessKey, final String awsSecretKey) {
        this.associateTag = associateTag;
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
    }

    /**
     * Creates a new {@link AmazonWebServiceAuthentication} with the given values.
     *
     * @param associateTag The associate tag identifying the user of the Amazon service.
     * @param awsAccessKey The access key needed to access a Amazon service.
     * @param awsSecretKey The secret key needed to access a Amazon service.
     * @return The created {@link AmazonWebServiceAuthentication} containing the given values.
     */
    public static AmazonWebServiceAuthentication create(final String associateTag, final String awsAccessKey, final String awsSecretKey) {
        return new AmazonWebServiceAuthentication(associateTag, awsAccessKey, awsSecretKey);
    }

    /**
     * Gives the associate tag identifying the user of the Amazon service.
     *
     * @return The associate tag identifying the user of the Amazon service.
     */
    public String getAssociateTag() {
        return associateTag;
    }

    /**
     * Gives the access key needed to access a Amazon service.
     *
     * @return The access key needed to access a Amazon service.
     */
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    /**
     * Gives the secret key needed to access a Amazon service.
     *
     * @return The secret key needed to access a Amazon service.
     */
    public String getAwsSecretKey() {
        return awsSecretKey;
    }
}

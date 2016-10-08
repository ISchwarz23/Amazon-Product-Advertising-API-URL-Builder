package de.codecrafters.apaarb;

/**
 * Created by Ingo on 15.06.2016.
 */
public final class AmazonAuthentication {

    private final String associateTag;
    private final String awsAccessKeyId;
    private final String awsSecretKey;

    public static AmazonAuthentication create(final String associateTag, final String awsAccessKeyId, final String awsSecretKey) {
        return new AmazonAuthentication(associateTag, awsAccessKeyId, awsSecretKey);
    }

    private AmazonAuthentication(final String associateTag, final String awsAccessKeyId, final String awsSecretKey) {
        this.associateTag = associateTag;
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretKey = awsSecretKey;
    }

    public String getAssociateTag() {
        return associateTag;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }
}

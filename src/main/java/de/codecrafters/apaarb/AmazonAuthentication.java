package de.codecrafters.apaarb;

/**
 * Created by Ingo on 15.06.2016.
 */
public final class AmazonAuthentication {

    private final String associateTag;
    private final String awsAccessKeyId;
    private final String awsSecretKey;

    public AmazonAuthentication(String associateTag, String awsAccessKeyId, String awsSecretKey) {
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

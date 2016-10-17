package de.codecrafters.apaarb;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Ingo on 16.10.2016.
 */
final class HashUtils {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String UTF8_CHARSET = "UTF-8";

    static String createSignedUrl(final String protocol, final String amazonServiceUrl, final String route,
                                  final Map<String, String> requestPairs, final String secretKey) {

        // The parameters need to be processed in lexicographical order, so we'll
        // use a TreeMap implementation for that.
        final SortedMap<String, String> sortedRequestPairs = new TreeMap<>(requestPairs);

        // get the canonical form the query string
        final String canonicalQS = canonicalizeParameters(sortedRequestPairs);

        // create the string upon which the signature is calculated
        final String toSign = "GET" + "\n"
                + amazonServiceUrl + "\n"
                + route + "\n"
                + canonicalQS;

        // get the signature
        final String hmac = hmac(toSign, secretKey);
        final String sig = percentEncodeRfc3986(hmac);

        // construct the URL
        return protocol + amazonServiceUrl + route + "?" + canonicalQS + "&Signature=" + sig;
    }

    static String hmac(final String stringToSign, final String awsSecretKey) {
        String signature = null;
        final byte[] data;
        final byte[] rawHmac;
        try {
            data = stringToSign.getBytes(UTF8_CHARSET);
            final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            final byte[] secretKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            rawHmac = mac.doFinal(data);
            final Base64.Encoder encoder = Base64.getEncoder();
            signature = new String(encoder.encode(rawHmac));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(UTF8_CHARSET + " is not supported!", e);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return signature;
    }

    static String canonicalizeParameters(final SortedMap<String, String> sortedParamMap) {
        if (sortedParamMap.isEmpty()) {
            return "";
        }

        final StringBuilder buffer = new StringBuilder();
        final Iterator<Map.Entry<String, String>> paramIterator = sortedParamMap.entrySet().iterator();

        while (paramIterator.hasNext()) {
            final Map.Entry<String, String> keyValuePair = paramIterator.next();
            buffer.append(percentEncodeRfc3986(keyValuePair.getKey()));
            buffer.append("=");
            buffer.append(percentEncodeRfc3986(keyValuePair.getValue()));
            if (paramIterator.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    static String percentEncodeRfc3986(final String s) {
        String out;
        try {
            out = URLEncoder.encode(s, UTF8_CHARSET)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (final UnsupportedEncodingException e) {
            out = s;
        }
        return out;
    }

}

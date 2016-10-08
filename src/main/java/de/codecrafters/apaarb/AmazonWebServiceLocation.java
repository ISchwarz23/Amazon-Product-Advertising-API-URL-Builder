package de.codecrafters.apaarb;

/**
 * Listing of the available Amazon web services for the different locations.
 *
 * @author ISchwarz
 */
public enum AmazonWebServiceLocation {

    CA("webservices.amazon.ca"),
    CN("webservices.amazon.cn"),
    CO_JP("webservices.amazon.co.jp"),
    CO_UK("webservices.amazon.co.uk"),
    COM("webservices.amazon.com"),
    COM_BR("webservices.amazon.com.br"),
    COM_MX("webservices.amazon.com.mx"),
    DE("webservices.amazon.de"),
    ES("webservices.amazon.es"),
    FR("webservices.amazon.fr"),
    IT("webservices.amazon.it");

    private final String webServiceUrl;

    AmazonWebServiceLocation(final String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    String getWebServiceUrl() {
        return webServiceUrl;
    }

}

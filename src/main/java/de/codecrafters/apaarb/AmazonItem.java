package de.codecrafters.apaarb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ingo on 15.06.2016.
 */
public final class AmazonItem {

    public static final class Id {

        public enum Type {
            ASIN("ASIN"),
            ISBN("ISBN"),
            UPC("UPC"),
            EAN("EAN");

            private String requestValue;

            Type(String requestValue) {
                this.requestValue = requestValue;
            }

            public String getRequestValue() {
                return requestValue;
            }
        }

        private String value;
        private Type type;

        public static Id createAsin(String asinValue) {
            return new Id(asinValue, Type.ASIN);
        }

        public static Id createEan(String eanValue) {
            return new Id(eanValue, Type.EAN);
        }

        public static Id createIsbn(String isbnValue) {
            return new Id(isbnValue, Type.ISBN);
        }

        public static Id createUpc(String upcValue) {
            return new Id(upcValue, Type.UPC);
        }

        private Id() {
            //no instance
        }

        Id(String value, Type type) {
            this.value = value;
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (!value.equals(id.value)) return false;
            return type == id.type;

        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "value='" + value + '\'' +
                    ", type=" + type +
                    '}';
        }
    }


    public static class Attributes {

        private String title;
        private String publisher;
        private String productGroup;
        private List<String> features;

        private Attributes() {
            //no instance
        }

        public String getTitle() {
            return title;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getProductGroup() {
            return productGroup;
        }

        public List<String> getFeatures() {
            return features;
        }

        @Override
        public String toString() {
            return "Attributes{" +
                    "title='" + title + '\'' +
                    ", publisher='" + publisher + '\'' +
                    ", productGroup='" + productGroup + '\'' +
                    ", features=" + features +
                    '}';
        }
    }

    public enum Condition {
        ALL("All"),
        NEW("New"),
        USED("Used"),
        REFURBISHED("Refurbished"),
        COLLECTIBLE("Collectible");

        private String requestValue;

        Condition(String requestValue) {
            this.requestValue = requestValue;
        }

        public String getRequestValue() {
            return requestValue;
        }

        public static Condition parse(String textContent) {
            if (ALL.toString().equalsIgnoreCase(textContent)) {
                return ALL;
            } else if (NEW.toString().equalsIgnoreCase(textContent)) {
                return NEW;
            } else if (USED.toString().equalsIgnoreCase(textContent)) {
                return USED;
            } else if (REFURBISHED.toString().equalsIgnoreCase(textContent)) {
                return REFURBISHED;
            } else if (COLLECTIBLE.toString().equalsIgnoreCase(textContent)) {
                return COLLECTIBLE;
            }
            return null;
        }
    }


    public static final class Offer {

        private String availability;
        private Price price;
        private Condition condition;
        private boolean isPrimeAvailable;
        private boolean isSuperSaverShippingAvailable;

        private Offer() {
            //no instance
        }

        public Offer(String availability, Price price, Condition condition, boolean isPrimeAvailable, boolean isSuperSaverShippingAvailable) {
            this.availability = availability;
            this.price = price;
            this.condition = condition;
            this.isPrimeAvailable = isPrimeAvailable;
            this.isSuperSaverShippingAvailable = isSuperSaverShippingAvailable;
        }

        public String getAvailability() {
            return availability;
        }

        public Price getPrice() {
            return price;
        }

        public Condition getCondition() {
            return condition;
        }

        public boolean isPrimeAvailable() {
            return isPrimeAvailable;
        }

        public boolean isSuperSaverShippingAvailable() {
            return isSuperSaverShippingAvailable;
        }

        @Override
        public String toString() {
            return "Offer{" +
                    "availability='" + availability + '\'' +
                    ", price=" + price +
                    ", condition=" + condition +
                    ", isPrimeAvailable=" + isPrimeAvailable +
                    ", isSuperSaverShippingAvailable=" + isSuperSaverShippingAvailable +
                    '}';
        }
    }

    public static final class OfferSummary {

        private Price lowestNewPrice;
        private Price lowestUsedPrice;
        private int totalNew;
        private int totalUsed;

        private List<Offer> offersNew = new ArrayList<>();
        private List<Offer> offersUsed = new ArrayList<>();

        private OfferSummary() {
            //no instance
        }

        public Price getLowestNewPrice() {
            return lowestNewPrice;
        }

        public List<Offer> getOffersNew() {
            return offersNew;
        }

        public List<Offer> getOffersUsed() {
            return offersUsed;
        }

        public Price getLowestUsedPrice() {
            return lowestUsedPrice;
        }

        public int getTotalNew() {
            return totalNew;
        }

        public int getTotalUsed() {
            return totalUsed;
        }

        @Override
        public String toString() {
            return "OfferSummary{" +
                    "lowestNewPrice=" + lowestNewPrice +
                    ", lowestUsedPrice=" + lowestUsedPrice +
                    ", totalNew=" + totalNew +
                    ", totalUsed=" + totalUsed +
                    ", offersNew=" + offersNew +
                    ", offersUsed=" + offersUsed +
                    '}';
        }
    }

    public static final class Price {

        private int amountInCents;
        private String currencyCode;

        private Price() {
            //no instance
        }

        public Price(int amountInCents, String currencyCode) {
            this.amountInCents = amountInCents;
            this.currencyCode = currencyCode;
        }

        public int getAmountInCents() {
            return amountInCents;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        @Override
        public String toString() {
            return "Price { " +
                    "  amountInCents=" + amountInCents +
                    "  currencyCode=" + currencyCode +
                    " }";
        }
    }

    public static final class Images {

        private List<String> swatchImageUrls;
        private List<String> smallImageUrls;
        private List<String> tinyImageUrls;
        private List<String> thumbnailImageUrls;
        private List<String> mediumImageUrls;
        private List<String> largeImageUrls;

        private Images() {
            //no instance
        }

        public List<String> getSwatchImageUrls() {
            return swatchImageUrls;
        }

        public List<String> getSmallImageUrls() {
            return smallImageUrls;
        }

        public List<String> getTinyImageUrls() {
            return tinyImageUrls;
        }

        public List<String> getThumbnailImageUrls() {
            return thumbnailImageUrls;
        }

        public List<String> getMediumImageUrls() {
            return mediumImageUrls;
        }

        public List<String> getLargeImageUrls() {
            return largeImageUrls;
        }

        @Override
        public String toString() {
            return "Images{" +
                    "swatchImageUrls=" + swatchImageUrls +
                    ", smallImageUrls=" + smallImageUrls +
                    ", tinyImageUrls=" + tinyImageUrls +
                    ", thumbnailImageUrls=" + thumbnailImageUrls +
                    ", mediumImageUrls=" + mediumImageUrls +
                    ", largeImageUrls=" + largeImageUrls +
                    '}';
        }
    }


    private final Id id;
    private String detailPageUrl;
    private Attributes attributes;
    private OfferSummary offerSummary;
    private Images images;

    private AmazonItem(String id, Id.Type idType, final String detailPageUrl) {
        this(new Id(id, idType), detailPageUrl);
    }

    private AmazonItem(Id id, String detailPageUrl) {
        this.id = id;
        this.detailPageUrl = detailPageUrl;
    }

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public Id getId() {
        return id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public OfferSummary getOfferSummary() {
        return offerSummary;
    }

    public Images getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "AmazonItem{" +
                "id=" + id +
                ", attributes=" + attributes +
                ", offerSummary=" + offerSummary +
                ", images=" + images +
                '}';
    }
}

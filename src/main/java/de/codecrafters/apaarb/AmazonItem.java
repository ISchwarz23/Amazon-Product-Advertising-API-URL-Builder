package de.codecrafters.apaarb;

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

            Type(final String requestValue) {
                this.requestValue = requestValue;
            }

            public String getRequestValue() {
                return requestValue;
            }
        }

        private String value;
        private Type type;

        public static Id createAsin(final String asinValue) {
            return new Id(asinValue, Type.ASIN);
        }

        public static Id createEan(final String eanValue) {
            return new Id(eanValue, Type.EAN);
        }

        public static Id createIsbn(final String isbnValue) {
            return new Id(isbnValue, Type.ISBN);
        }

        public static Id createUpc(final String upcValue) {
            return new Id(upcValue, Type.UPC);
        }

        public static Id create(final String idValue, final Type idType) {
            return new Id(idValue, idType);
        }

        private Id() {
            //no instance
        }

        private Id(final String value, final Type type) {
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
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Id id = (Id) o;
            return value.equals(id.value) && type == id.type;
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


    public enum Condition {
        ALL("All"),
        NEW("New"),
        USED("Used"),
        REFURBISHED("Refurbished"),
        COLLECTIBLE("Collectible");

        private final String requestValue;

        Condition(final String requestValue) {
            this.requestValue = requestValue;
        }

        public String getRequestValue() {
            return requestValue;
        }

        public static Condition parse(final String textContent) {
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


    private final Id id;

    private AmazonItem(final String id, final Id.Type idType) {
        this(new Id(id, idType));
    }

    private AmazonItem(final Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AmazonItem{" +
                "id=" + id +
                '}';
    }
}

package de.codecrafters.apaarb;

/**
 * Listing of all Item Categories managed by Amazon.
 *
 * @author ISchwarz
 */
public enum ItemCategory {

    ALL("All"),
    APPAREL("Apparel"),
    AUTOMOTIVE("Automotive"),
    BABY("Baby"),
    BEAUTY("Beauty"),
    BOOKS("Books"),
    CLASSICAL("Classical"),
    DVD("DVD"),
    ELECTRONICS("Electronics"),
    FOREIGN_BOOKS("ForeignBooks"),
    GROCERY("Grocery"),
    HEALTH_AND_PERSONAL_CARE("HealthPersonalCare"),
    HOME_AND_GARDEN("HomeGarden"),
    JEWELRY("Jewelry"),
    KINDLE_STORE("KindleStore"),
    KITCHEN("Kitchen"),
    LIGHTING("Lighting"),
    MAGAZINES("Magazines"),
    MARKETPLACE("Marketplace"),
    MP3_DOWNLOADS("MP3Downloads"),
    MUSIC("Music"),
    MUSICAL_INSTRUMENTS("MusicalInstruments"),
    MUSIC_TRACKS("MusicTracks"),
    OFFICE_PRODUCTS("OfficeProducts"),
    OUTDOOR_LIVING("OutdoorLiving"),
    OUTLET("Outlet"),
    PC_HARDWARE("PCHardware"),
    PHOTO("Photo"),
    SHOES("Shoes"),
    SOFTWARE("Software"),
    SOFTWARE_VIDEO_GAMES("SoftwareVideoGames"),
    SPORTING_GOODS("SportingGoods"),
    TOOLS("Tools"),
    TOYS("Toys"),
    VHS("VHS"),
    VIDEO("Video"),
    VIDEO_GAMES("VideoGames"),
    WATCHES("Watches");

    private final String requestValue;

    ItemCategory(final String requestValue) {
        this.requestValue = requestValue;
    }

    public String getRequestValue() {
        return requestValue;
    }
}

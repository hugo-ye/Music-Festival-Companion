package interface_adapter.search_event;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import use_case.search_event.SearchEventInputBoundary;
import use_case.search_event.SearchEventInputData;

/**
 * Controller for the SearchEvent use case.
 */
public final class SearchEventController {

    private final SearchEventInputBoundary searchInteractor;
    private Map<String, String> countriesMap;
    private Map<String, String> genreMap;

    /**
     * Constructs a SearchEventController.
     *
     * @param searchInteractor The input boundary for the search use case.
     */
    public SearchEventController(final SearchEventInputBoundary searchInteractor) {
        this.initializeCountriesMap();
        this.initializeGenreMap();
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the search event Use Case by processing raw search parameters.
     *
     * @param rawKeyword     The keyword string from the UI.
     * @param rawArtist      The artist name string from the UI.
     * @param rawCountryName The raw name of the country.
     * @param rawCityName    The raw name of the city.
     * @param rawStartDate   The raw start date string (e.g., "YYYY-MM-DD").
     * @param rawEndDate     The raw end date string (e.g., "YYYY-MM-DD").
     * @param genre          The list of selected genres.
     */
    public void execute(final String rawKeyword, final String rawArtist,
                        final String rawCountryName, final String rawCityName,
                        final String rawStartDate, final String rawEndDate,
                        final List<String> genre) {

        // Clean and validate input
        final String keyword = this.formatKeyword(rawKeyword, rawArtist);
        final String country = this.formatCountry(rawCountryName);
        final String city = this.formatCity(rawCityName);
        final String startDateTime = this.formatDateTime(rawStartDate, true);
        final String endDateTime = this.formatDateTime(rawEndDate, false);
        final String genreIds = this.formatGenre(genre);
        // Create the input data
        final SearchEventInputData searchEventInputData = new SearchEventInputData(keyword,
                country, city, genreIds, startDateTime, endDateTime);

        // Call interactor
        this.searchInteractor.execute(searchEventInputData);
    }

    /**
     * Initializes the map of country names to their ISO codes.
     */
    private void initializeCountriesMap() {
        final String[] isoCountries = Locale.getISOCountries();
        this.countriesMap = new HashMap<>();
        for (final String country : isoCountries) {
            final Locale locale = new Locale("en", country);
            final String code = locale.getCountry();
            final String name = locale.getDisplayCountry();
            this.countriesMap.put(name.toLowerCase(), code);
        }
    }

    /**
     * Populates the genre map in the format of <genreName, genreId>.
     */
    private void initializeGenreMap() {
        this.genreMap = new HashMap<>();
        this.genreMap.put("alternative", "KnvZfZ7vAvv");
        this.genreMap.put("ballads/romantic", "KnvZfZ7vAve");
        this.genreMap.put("blues", "KnvZfZ7vAvd");
        this.genreMap.put("chanson francaise", "KnvZfZ7vAvA");
        this.genreMap.put("children's music", "KnvZfZ7vAvk");
        this.genreMap.put("classical", "KnvZfZ7vAeJ");
        this.genreMap.put("country", "KnvZfZ7vAv6");
        this.genreMap.put("dance/electronic", "KnvZfZ7vAvF");
        this.genreMap.put("folk", "KnvZfZ7vAva");
        this.genreMap.put("hip-hop/rap", "KnvZfZ7vAv1");
        this.genreMap.put("holiday", "KnvZfZ7vAvJ");
        this.genreMap.put("jazz", "KnvZfZ7vAvE");
        this.genreMap.put("medieval/renaissance", "KnvZfZ7vAvI");
        this.genreMap.put("metal", "KnvZfZ7vAvt");
        this.genreMap.put("new age", "KnvZfZ7vAvn");
        this.genreMap.put("other", "KnvZfZ7vAvl");
        this.genreMap.put("pop", "KnvZfZ7vAev");
        this.genreMap.put("r&b", "KnvZfZ7vAee");
        this.genreMap.put("reggae", "KnvZfZ7vAed");
        this.genreMap.put("religious", "KnvZfZ7vAe7");
        this.genreMap.put("rock", "KnvZfZ7vAeA");
        this.genreMap.put("world", "KnvZfZ7vAeF");
    }

    /**
     * Helper to convert a simple YYYY-MM-DD date string into the required ISO 8601 UTC format.
     * The TicketMaster api uses the ISO format.
     *
     * @param rawDate The date string (e.g., "2025-12-15").
     * @param isStart If true, uses the start of the day (00:00:00Z). If false, uses the end of the day (23:59:59Z).
     * @return Formatted ISO 8601 string or null if input is invalid/empty.
     */
    private String formatDateTime(final String rawDate, final boolean isStart) {
        // Return "" if its empty
        if (rawDate == null || rawDate.trim().isEmpty()) {
            return "";
        }

        try {
            final ZonedDateTime zonedDateTime;

            if (isStart) {
                // For start date, use the very beginning of the day in UTC
                zonedDateTime = ZonedDateTime.parse(rawDate + "T00:00:00Z",
                        DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC));
            }
            else {
                // For end date, use the very end of the day in UTC
                zonedDateTime = ZonedDateTime.parse(rawDate + "T23:59:59Z",
                        DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC));
            }

            return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);

        }
        catch (final DateTimeParseException dateTimeParseException) {
            System.err.println("Controller error: Could not parse date " + rawDate);
            return "";
        }
    }

    /**
     * Formats the keyword and artist strings.
     * @param rawKeyword The raw keyword.
     * @param rawArtist The raw artist.
     * @return The formatted keyword.
     */
    private String formatKeyword(final String rawKeyword, final String rawArtist) {
        final String keyword = rawKeyword == null ? "" : rawKeyword.trim();
        final String artist = rawArtist == null ? "" : rawArtist.trim();

        if (keyword.isEmpty()) {
            return artist;
        }
        if (artist.isEmpty()) {
            return keyword;
        }
        return keyword + " " + artist;
    }

    /**
     * Formats the city string.
     * @param location The raw city.
     * @return The formatted city.
     */
    private String formatCity(final String location) {
        return location == null ? "" : location.trim();
    }

    /**
     * Returns the ISO code for the given country as specified by TicketMaster API.
     *
     * @param countryName is the full name of the country.
     * @return The two digit ISO code of the country.
     */
    private String formatCountry(final String countryName) {
        final String cleanedCountryName = countryName == null ? "" : countryName.trim().toLowerCase();
        return this.countriesMap.getOrDefault(cleanedCountryName, "");

    }

    /**
     * Formats the list of genre names into a comma-separated string of genre IDs.
     * @param genreNames The list of genre names.
     * @return A comma-separated string of genre IDs.
     */
    private String formatGenre(final List<String> genreNames) {
        if (genreNames == null) {
            return "";
        }
        final StringBuilder genreIds = new StringBuilder();
        for (final String genreName : genreNames) {
            final String genreId = this.genreMap.getOrDefault(genreName, "");
            if (!genreId.isEmpty()) {
                if (genreIds.length() > 0) {
                    genreIds.append(",");
                }
                genreIds.append(genreId);
            }
        }
        return genreIds.toString();
    }

}

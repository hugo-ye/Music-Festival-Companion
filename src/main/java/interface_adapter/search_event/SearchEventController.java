package interface_adapter.search_event;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import use_case.search_event.SearchEventInputBoundary;
import use_case.search_event.SearchEventInputData;

/**
 * Controller for the SearchEvent use case.
 */
public class SearchEventController {

    private final SearchEventInputBoundary searchInteractor;
    private Map<String, String> countriesMap;
    private Map<String, String> genreMap;

    public SearchEventController(SearchEventInputBoundary searchInteractor) {
        initializeCountriesMap();
        initializeGenreMap();
        this.searchInteractor = searchInteractor;
    }

    private void initializeCountriesMap() {
        final String[] isoCountries = Locale.getISOCountries();
        countriesMap = new HashMap<>();
        for (String country : isoCountries) {
            final Locale locale = new Locale("en", country);
            final String code = locale.getCountry();
            final String name = locale.getDisplayCountry();
            countriesMap.put(name.toLowerCase(), code);
        }
    }

    /**
     * Populates the genre map in the format of {genre name, genreId}.
     */
    private void initializeGenreMap() {
        genreMap = new HashMap<>();
        genreMap.put("alternative", "KnvZfZ7vAvv");
        genreMap.put("ballads/romantic", "KnvZfZ7vAve");
        genreMap.put("blues", "KnvZfZ7vAvd");
        genreMap.put("chanson francaise", "KnvZfZ7vAvA");
        genreMap.put("children's music", "KnvZfZ7vAvk");
        genreMap.put("classical", "KnvZfZ7vAeJ");
        genreMap.put("country", "KnvZfZ7vAv6");
        genreMap.put("dance/electronic", "KnvZfZ7vAvF");
        genreMap.put("folk", "KnvZfZ7vAva");
        genreMap.put("hip-hop/rap", "KnvZfZ7vAv1");
        genreMap.put("holiday", "KnvZfZ7vAvJ");
        genreMap.put("jazz", "KnvZfZ7vAvE");
        genreMap.put("medieval/renaissance", "KnvZfZ7vAvI");
        genreMap.put("metal", "KnvZfZ7vAvt");
        genreMap.put("new age", "KnvZfZ7vAvn");
        genreMap.put("other", "KnvZfZ7vAvl");
        genreMap.put("pop", "KnvZfZ7vAev");
        genreMap.put("r&b", "KnvZfZ7vAee");
        genreMap.put("reggae", "KnvZfZ7vAed");
        genreMap.put("religious", "KnvZfZ7vAe7");
        genreMap.put("rock", "KnvZfZ7vAeA");
        genreMap.put("world", "KnvZfZ7vAeF");
    }

    /**
     * Executes the search event Use Case by processing raw search parameters.
     * @param rawKeyword     The keyword or artist name string from the UI.
     * @param rawArtist      The raw artist string (e.g, the exact name of artist).
     * @param rawStartDate   The raw start date string (e.g., "YYYY-MM-DD").
     * @param rawEndDate     The raw end date string (e.g., "YYYY-MM-DD").
     * @param rawCountryName The raw name of the country with proper spelling.
     * @param rawCityName    The raw name of the city (e.g, Toronto).
     * @param genre          The genre of event.
     */
    public void execute(String rawKeyword, String rawArtist, String rawCountryName, String rawCityName,
                        String rawStartDate, String rawEndDate, List<String> genre) {

        // Clean and validate input
        final String keyword = formatKeyword(rawKeyword, rawArtist);
        final String country = formatCountry(rawCountryName);
        final String city = formatCity(rawCityName);
        final String startDateTime = formatDateTime(rawStartDate, true);
        final String endDateTime = formatDateTime(rawEndDate, false);
        final String genreIds = formatGenre(genre);
        // Create the input data
        final SearchEventInputData searchEventInputData = new SearchEventInputData(keyword, country, city, genreIds,
                startDateTime, endDateTime);

        // Call interactor
        searchInteractor.execute(searchEventInputData);
    }

    /**
     * Helper to convert a simple YYYY-MM-DD date string into the required ISO 8601 UTC format.
     * The TicketMaster api uses the ISO format.
     * @param rawDate The date string (e.g., "2025-12-15").
     * @param isStart If true, uses the start of the day (00:00:00Z). If false, uses the end of the day (23:59:59Z).
     * @return Formatted ISO 8601 string or null if input is invalid/empty.
     */
    private String formatDateTime(String rawDate, boolean isStart) {
        // Return null if its empty
        String result = null;
        if (rawDate == null || rawDate.trim().isEmpty()) {
            result = "";
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

            result = zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);

        }
        catch (DateTimeParseException dateTimeParseException) {
            System.err.println("Controller error: Could not parse date " + rawDate);

        }
        return result;
    }

    private String formatKeyword(String rawKeyword, String rawArtist) {
        String keyword = getKeywordHelper(rawKeyword);
        final String artist = getKeywordHelper(rawArtist);

        if (!artist.isEmpty()) {
            keyword = keyword + " " + artist;
            keyword = keyword.trim();
        }
        else {
            keyword = keyword.trim();
        }

        return keyword;
    }

    @NotNull
    private static String getKeywordHelper(String rawString) {
        String result = "";
        if (rawString != null) {
            result = rawString.trim();
        }
        return result;
    }

    private String formatCity(String location) {
        return formatCityHelper(location);
    }

    @NotNull
    private static String formatCityHelper(String location) {
        String result = "";
        if (location != null) {
            result = location.trim();
        }
        return result;
    }

    /**
     * Returns the ISO code for the given country as specified by TicketMaster API.
     *
     * @param countryName is the full name of the country
     * @return The two digit ISO code of the country
     */
    private String formatCountry(String countryName) {
        final String lowCaseCountryName = formatCountryHelper(countryName);
        return countriesMap.getOrDefault(lowCaseCountryName, "");

    }

    @NotNull
    private static String formatCountryHelper(String countryName) {
        String result = "";
        if (countryName != null) {
            result = countryName.trim().toLowerCase();
        }
        return result;
    }

    private String formatGenre(List<String> genreNames) {
        String genreIds = "";
        for (String genreName : genreNames) {
            genreIds = genreIds.concat(genreMap.getOrDefault(genreName, "")) + ",";
        }
        if (!genreIds.isEmpty()) {
            genreIds = genreIds.substring(0, genreIds.length() - 1);
        }
        return genreIds;
    }
}

package interface_adapter.search_event;

import use_case.search_event.SearchEventInputBoundary;
import use_case.search_event.SearchEventInputData;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
        String[] isoCountries = Locale.getISOCountries();
        countriesMap = new HashMap<>();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();
            countriesMap.put(name.toLowerCase(), code);
        }
    }

    /**
     * populates the genre map in the format of <genreName, genreId>
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
     * * @param rawKeyword The keyword or artist name string from the UI.
     *
     * @param rawStartDate   The raw start date string (e.g., "YYYY-MM-DD").
     * @param rawEndDate     The raw end date string (e.g., "YYYY-MM-DD").
     * @param rawCountryName The raw name of the country with proper spelling
     */
    public void execute(String rawKeyword, String rawArtist, String rawCountryName, String rawCityName, String rawStartDate, String rawEndDate, List<String> genre) {

        // Clean and validate input
        String keyword = formatKeyword(rawKeyword, rawArtist);
        String country = formatCountry(rawCountryName);
        String city = formatCity(rawCityName);
        String startDateTime = formatDateTime(rawStartDate, true);
        String endDateTime = formatDateTime(rawEndDate, false);
        String genreIds = formatGenre(genre);
        // Create the input data
        final SearchEventInputData searchEventInputData = new SearchEventInputData(keyword, country, city, genreIds, startDateTime, endDateTime);

        //Call interactor
        searchInteractor.execute(searchEventInputData);
    }

    /**
     * Helper to convert a simple YYYY-MM-DD date string into the required ISO 8601 UTC format.
     * The TicketMaster api uses the ISO format.
     * * @param rawDate The date string (e.g., "2025-12-15").
     *
     * @param isStart If true, uses the start of the day (00:00:00Z). If false, uses the end of the day (23:59:59Z).
     * @return Formatted ISO 8601 string or null if input is invalid/empty.
     */
    private String formatDateTime(String rawDate, boolean isStart) {
        // Return null if its empty
        if (rawDate == null || rawDate.trim().isEmpty()) {
            return "";
        }

        try {
            ZonedDateTime zonedDateTime;

            if (isStart) {
                // For start date, use the very beginning of the day in UTC
                zonedDateTime = ZonedDateTime.parse(rawDate + "T00:00:00Z", DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC));
            } else {
                // For end date, use the very end of the day in UTC
                zonedDateTime = ZonedDateTime.parse(rawDate + "T23:59:59Z", DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC));
            }

            return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);

        } catch (DateTimeParseException e) {
            System.err.println("Controller error: Could not parse date " + rawDate);
            return null;
        }
    }


    private String formatKeyword(String rawKeyword, String rawArtist) {
        String keyword = rawKeyword == null ? "" : rawKeyword.trim();
        String artist = rawArtist == null ? "" : rawArtist.trim();

        if (!artist.isEmpty()) {
            keyword = keyword + " " + artist;
            keyword = keyword.trim();
        } else {
            keyword = keyword.trim();
        }

        return keyword;
    }

    private String formatCity(String location) {
        return location == null ? "" : location.trim();
    }

    /**
     * Returns the ISO code for the given country as specified by TicketMaster API
     *
     * @param countryName is the full name of the country
     * @return The two digit ISO code of the country
     */
    private String formatCountry(String countryName) {
        countryName = countryName == null ? "" : countryName.trim().toLowerCase();
        return countriesMap.getOrDefault(countryName, "");

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
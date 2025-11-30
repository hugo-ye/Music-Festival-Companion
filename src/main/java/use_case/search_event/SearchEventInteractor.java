package use_case.search_event;

import entity.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Interactor for the SearchEvent use case
 *
 * The class implements {@link SearchEventInputBoundary} and is responsible to retrieve event data from
 * {@link SearchEventDataAccessInterface} and parse it into domain entities {@link Event}.
 *
 */
public class SearchEventInteractor implements SearchEventInputBoundary{
    private final SearchEventDataAccessInterface dataAccess;
    private final SearchEventOutputBoundary presenter;

    public SearchEventInteractor(SearchEventDataAccessInterface dataAccess,
                                 SearchEventOutputBoundary presenter){
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Retrieves event data from the DAO, parse it into {@link Event} object.
     *
     * @param inputData the input data containing the search criteria.
     */
    @Override
    public void execute(SearchEventInputData inputData) {
        String events = dataAccess.search(
                inputData.getKeyword(),
                inputData.getCountry(),
                inputData.getCity(),
                inputData.getStartDate(),
                inputData.getEndDate(),
                inputData.getGenre()
        );
        SearchEventOutputData outputData = new SearchEventOutputData(createEventsFromJson(events));
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Parse raw JSON string into a list of {@link Event} objects.
     *
     * @param dataJson the raw JSON string.
     * @return a list of Events. An empty list if the dataJson is null or empty.
     */
    public List<Event> createEventsFromJson(String dataJson) {
        List<Event> events = new ArrayList<>();

        if (dataJson == null || dataJson.isEmpty()) {
            return events;
        }

        try {
            // Root of json
            JSONObject base = new JSONObject(dataJson);

            if (base.has("_embedded") && base.getJSONObject("_embedded").has("events")) {
                JSONArray eventsArray = base.getJSONObject("_embedded").getJSONArray("events");
                // Each event in the events
                for (int i = 0; i < eventsArray.length(); i++) {
                    JSONObject jsonEvent = eventsArray.getJSONObject(i);

                    Event event = eventFromJson(jsonEvent);
                    events.add(event);
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    /**
     * Converts a single JSON object into an {@link Event}.
     * @param jsonEvent the JSON representation of the event
     * @return an {@link Event}.
     */
    public static Event eventFromJson(JSONObject jsonEvent) {
        String id = jsonEvent.optString("id", "N/A");
        String name = jsonEvent.optString("name", "Unnamed Event");
        String ticketUrl = jsonEvent.optString("url", "#");

        LocalDate date = extractDateFromJson(jsonEvent);
        List<Integer> priceRange = extractPriceFromJson(jsonEvent);
        int priceMin = priceRange.get(0);
        int priceMax = priceRange.get(1);

        List<String> artists = extractArtists(jsonEvent);
        List<String> genres = extractGenres(jsonEvent);
        String venueName = extractVenueName(jsonEvent);
        String cityName = extractCity(jsonEvent);
        String countryName = extractCountry(jsonEvent);
        String imageUrl = extractImageUrl(jsonEvent);

        return new Event(id, name, artists, venueName, cityName, countryName, date, priceMin, priceMax, ticketUrl, genres, imageUrl);
    }

    private static JSONObject getEmbedded(JSONObject jsonEvent) {
        return jsonEvent.has("_embedded") ? jsonEvent.getJSONObject("_embedded") : null;
    }

    private static JSONObject getVenue(JSONObject embedded) {
        if (embedded != null && embedded.has("venues")) {
            // Safely return the first venue object
            JSONArray venues = embedded.getJSONArray("venues");
            if (!venues.isEmpty()) {
                return venues.getJSONObject(0);
            }
        }
        return null;
    }

    private static JSONArray getAttractions(JSONObject embedded) {
        if (embedded != null && embedded.has("attractions")) {
            return embedded.getJSONArray("attractions");
        }
        return null;
    }

    public static LocalDate extractDateFromJson(JSONObject jsonEvent) {
        LocalDate date = null;
        try {
            if (jsonEvent.has("dates")) {
                JSONObject start = jsonEvent.getJSONObject("dates").getJSONObject("start");
                String startDateTimeStr = start.optString("dateTime");
                if (!startDateTimeStr.isEmpty()) {
                    date = LocalDate.parse(startDateTimeStr.split("T")[0]);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing date:" + e.getMessage());
        }
        return date;
    }


    public static List<Integer> extractPriceFromJson(JSONObject jsonEvent) {
        int priceMin = -1;
        int priceMax = -1;

        try {
            if (jsonEvent.has("priceRanges")) {
                JSONArray prices = jsonEvent.getJSONArray("priceRanges");
                if (!prices.isEmpty()) {
                    JSONObject priceRange = prices.getJSONObject(0);
                    System.out.println("price range is: " + priceRange);
                    priceMin = priceRange.optInt("min", -1);
                    priceMax = priceRange.optInt("max", -1);
                    System.out.println("prices is: " + priceMin + ", " + priceMax );
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing price" + e.getMessage());
        }

        return List.of(priceMin, priceMax);

    }

    /**
     * Extracts the artists names from an event Json object.
     *
     * @param jsonEvent the event JSON object
     * @return a list of artist names, empty list if not available.
     */
    public static List<String> extractArtists(JSONObject jsonEvent) {
        List<String> artists = new ArrayList<>();
        JSONObject embedded = getEmbedded(jsonEvent);
        if (embedded != null && embedded.has("attractions")) {
            JSONArray artistArray = embedded.getJSONArray("attractions");
            for (int i = 0; i < artistArray.length(); i++) {
                JSONObject artist = artistArray.getJSONObject(i);
                artists.add(artist.getString("name"));
            }

        }
        return artists;
    }

    /**
     * Extracts the venue name from an event JSON Object.
     *
     * @param jsonEvent the event JSON object.
     * @return the venue name, or N/A if not available.
     */
    public static String extractVenueName(JSONObject jsonEvent) {
        JSONObject embedded = getEmbedded(jsonEvent);
        JSONObject venue = getVenue(embedded);

        return venue != null ? venue.optString("name", "unknown") : "N/A";
    }

    /**
     * Extracts the city name from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the city name, N/A if not available.
     */
    public static String extractCity(JSONObject jsonEvent) {
        JSONObject embedded = getEmbedded(jsonEvent);
        JSONObject venue = getVenue(embedded);

        if (venue != null && venue.has("city")) {
            return venue.getJSONObject("city").optString("name", "unknown city");
        }

        return "N/A";
    }

    /**
     * Extracts the country from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the country name, return N/A if not available.
     */
    public static String extractCountry(JSONObject jsonEvent) {
        JSONObject embedded = getEmbedded(jsonEvent);
        JSONObject venue = getVenue(embedded);

        if (venue != null && venue.has("country")) {
            return venue.getJSONObject("country").optString("name", "unknown country");
        }

        return "N/A";
    }

    /**
     * Extracts the genres and subgenres from an event JSON object.
     *
     * @param jsonEvent the event JSON object
     * @return a list of genres, returns an empty list if none are found.
     */
    public static List<String> extractGenres(JSONObject jsonEvent) {
        Set<String> genres = new LinkedHashSet<>();

        if (jsonEvent.has("classifications")) {
            JSONArray classifications = jsonEvent.getJSONArray("classifications");

            for (int i = 0; i < classifications.length(); i++) {
                JSONObject classification = classifications.getJSONObject(i);

                if (classification.has("genre")) {
                    String genreName = classification.getJSONObject("genre").optString("name");
                    genres.add(genreName.trim());
                }

                if (classification.has("subGenre")) {
                    String subGenreName = classification.getJSONObject("subGenre").optString("name");
                    genres.add(subGenreName.trim());
                }
            }
        }
        return new ArrayList<>(genres);

    }

    /**
     * Extracts the URL of the first image from an event JSON object
     * @param jsonEvent the event JSON object.
     * @return the URL of the first image, if available, otherwise an empty string
     */
    public static String extractImageUrl(JSONObject jsonEvent) {
        if (jsonEvent.has("images")) {
            JSONArray images = jsonEvent.getJSONArray("images");
            if (!images.isEmpty()) {
                return  images.getJSONObject(0).getString("url");
            }
        }
        return "";
    }
}

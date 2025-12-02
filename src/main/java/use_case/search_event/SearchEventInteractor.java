package use_case.search_event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Event;
import entity.EventBuilder;

/**
 * Interactor for the SearchEvent use case
 * The class implements SearchEventInputBoundary and is responsible to retrieve event data from
 * SearchEventDataAccessInterface and parse it into Event entities.
 *
 */
public class SearchEventInteractor implements SearchEventInputBoundary {
    private final SearchEventDataAccessInterface dataAccess;
    private final SearchEventOutputBoundary presenter;

    public SearchEventInteractor(SearchEventDataAccessInterface dataAccess,
                                 SearchEventOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Retrieves event data from the DAO, parse it into an Event object.
     *
     * @param input the input data containing the search criteria.
     */
    @Override
    public void execute(SearchEventInputData input) {
        final String events = dataAccess.search(
                input.getKeyword(),
                input.getCountry(),
                input.getCity(),
                input.getStartDate(),
                input.getEndDate(),
                input.getGenre()
        );
        final SearchEventOutputData outputData = new SearchEventOutputData(createEventsFromJson(events));
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Parse raw JSON string into a list of {@link Event} objects.
     *
     * @param dataJson the raw JSON string.
     * @return a list of Events. An empty list if the dataJson is null or empty.
     */
    public List<Event> createEventsFromJson(String dataJson) {
        final List<Event> events = new ArrayList<>();

        if (dataJson == null || dataJson.isEmpty()) {
            return events;
        }

        try {
            // Root of json
            final JSONObject base = new JSONObject(dataJson);

            if (base.has("_embedded") && base.getJSONObject("_embedded").has("events")) {
                final JSONArray eventsArray = base.getJSONObject("_embedded").getJSONArray("events");
                // Each event in the events
                for (int i = 0; i < eventsArray.length(); i++) {
                    final JSONObject jsonEvent = eventsArray.getJSONObject(i);

                    final Event event = eventFromJson(jsonEvent);
                    events.add(event);
                }
            }
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return events;
    }

    /**
     * Converts a single JSON object into an {@link Event}.
     *
     * @param jsonEvent the JSON representation of the event
     * @return an {@link Event}.
     */
    public static Event eventFromJson(JSONObject jsonEvent) {
        final String id = jsonEvent.optString("id", "N/A");
        final String name = jsonEvent.optString("name", "Unnamed Event");
        final String ticketUrl = jsonEvent.optString("url", "#");

        final LocalDate date = extractDateFromJson(jsonEvent);
        final List<Integer> priceRange = extractPriceFromJson(jsonEvent);
        final int priceMin = priceRange.get(0);
        final int priceMax = priceRange.get(1);

        final List<String> artists = extractArtists(jsonEvent);
        final List<String> genres = extractGenres(jsonEvent);
        final String venueName = extractVenueName(jsonEvent);
        final String cityName = extractCity(jsonEvent);
        final String countryName = extractCountry(jsonEvent);
        final String imageUrl = extractImageUrl(jsonEvent);

        return new EventBuilder()
                .id(id)
                .name(name)
                .artists(artists)
                .venue(venueName)
                .city(cityName)
                .country(countryName)
                .date(date)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .ticketUrl(ticketUrl)
                .genres(genres)
                .imageURL(imageUrl)
                .build();
    }

    private static JSONObject getEmbedded(JSONObject jsonEvent) {
        return jsonEvent.has("_embedded") ? jsonEvent.getJSONObject("_embedded") : null;
    }

    private static JSONObject getVenue(JSONObject embedded) {
        if (embedded != null && embedded.has("venues")) {
            // Safely return the first venue object
            final JSONArray venues = embedded.getJSONArray("venues");
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
                final JSONObject start = jsonEvent.getJSONObject("dates").getJSONObject("start");
                final String startDateTimeStr = start.optString("dateTime");
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
                final JSONArray prices = jsonEvent.getJSONArray("priceRanges");
                if (!prices.isEmpty()) {
                    JSONObject priceRange = prices.getJSONObject(0);
                    System.out.println("price range is: " + priceRange);
                    priceMin = priceRange.optInt("min", -1);
                    priceMax = priceRange.optInt("max", -1);
                    System.out.println("prices is: " + priceMin + ", " + priceMax);
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
        final List<String> artists = new ArrayList<>();
        JSONObject embedded = getEmbedded(jsonEvent);
        if (embedded != null && embedded.has("attractions")) {
            final JSONArray artistArray = embedded.getJSONArray("attractions");
            for (int i = 0; i < artistArray.length(); i++) {
                final JSONObject artist = artistArray.getJSONObject(i);
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
        final JSONObject embedded = getEmbedded(jsonEvent);
        final JSONObject venue = getVenue(embedded);

        return venue != null ? venue.optString("name", "unknown") : "N/A";
    }

    /**
     * Extracts the city name from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the city name, N/A if not available.
     */
    public static String extractCity(JSONObject jsonEvent) {
        final JSONObject embedded = getEmbedded(jsonEvent);
        final JSONObject venue = getVenue(embedded);

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
        final JSONObject embedded = getEmbedded(jsonEvent);
        final JSONObject venue = getVenue(embedded);

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
        final Set<String> genres = new LinkedHashSet<>();

        if (jsonEvent.has("classifications")) {
            final JSONArray classifications = jsonEvent.getJSONArray("classifications");

            for (int i = 0; i < classifications.length(); i++) {
                final JSONObject classification = classifications.getJSONObject(i);

                if (classification.has("genre")) {
                    final String genreName = classification.getJSONObject("genre").optString("name");
                    genres.add(genreName.trim());
                }

                if (classification.has("subGenre")) {
                    final String subGenreName = classification.getJSONObject("subGenre").optString("name");
                    genres.add(subGenreName.trim());
                }
            }
        }
        return new ArrayList<>(genres);

    }

    /**
     * Extracts the URL of the first image from an event JSON object
     *
     * @param jsonEvent the event JSON object.
     * @return the URL of the first image, if available, otherwise an empty string
     */
    public static String extractImageUrl(JSONObject jsonEvent) {
        if (jsonEvent.has("images")) {
            final JSONArray images = jsonEvent.getJSONArray("images");
            if (!images.isEmpty()) {
                return images.getJSONObject(0).getString("url");
            }
        }
        return "";
    }
}

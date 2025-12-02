package use_case.search_event;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Event;
import entity.EventBuilder;

/**
 * Interactor for the SearchEvent use case.
 *
 * <p>
 * The class implements {@link SearchEventInputBoundary} and is responsible for retrieving event data from
 * {@link SearchEventDataAccessInterface} and parsing it into domain entities {@link Event}.
 * </p>
 */
public class SearchEventInteractor implements SearchEventInputBoundary {

    private static final int DEFAULT_PRICE = -1;
    private static final String NOT_AVAILABLE = "N/A";
    private static final String UNNAMED_EVENT = "Unnamed Event";
    private static final String UNDEFINED_GENRE = "Undefined";
    private static final String NAME_KEY = "name";
    private static final String EMBEDDED_KEY = "_embedded";
    private final SearchEventDataAccessInterface dataAccess;
    private final SearchEventOutputBoundary presenter;

    /**
     * Constructs a SearchEventInteractor.
     *
     * @param dataAccess The data access object for searching events.
     * @param presenter  The presenter for handling the output.
     */
    public SearchEventInteractor(final SearchEventDataAccessInterface dataAccess,
                                 final SearchEventOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Retrieves event data from the DAO, parses it into {@link Event} objects,
     * and passes it to the presenter.
     *
     * @param inputData the input data containing the search criteria.
     */
    @Override
    public void execute(final SearchEventInputData inputData) {
        final String eventsJson = this.dataAccess.search(
                inputData.getKeyword(),
                inputData.getCountry(),
                inputData.getCity(),
                inputData.getStartDate(),
                inputData.getEndDate(),
                inputData.getGenre()
        );
        final List<Event> events = this.createEventsFromJson(eventsJson);
        final SearchEventOutputData outputData = new SearchEventOutputData(events);
        this.presenter.prepareSuccessView(outputData);

    }

    /**
     * Parses a raw JSON string into a list of {@link Event} objects.
     *
     * @param dataJson the raw JSON string.
     * @return a list of Events. An empty list if the dataJson is null or empty.
     * @throws RuntimeException if the JSON parsing fails.
     */
    public List<Event> createEventsFromJson(final String dataJson) {
        final List<Event> events;
        if (dataJson == null || dataJson.isEmpty()) {
            events = Collections.emptyList();
        }
        else {
            events = new ArrayList<>();
            try {
                final JSONObject base = new JSONObject(dataJson);

                if (base.has(EMBEDDED_KEY) && base.getJSONObject(EMBEDDED_KEY).has("events")) {
                    final JSONArray eventsArray = base.getJSONObject(EMBEDDED_KEY).getJSONArray("events");
                    for (int i = 0; i < eventsArray.length(); i++) {
                        final JSONObject jsonEvent = eventsArray.getJSONObject(i);
                        final Event event = this.eventFromJson(jsonEvent);
                        events.add(event);
                    }
                }
            }
            catch (final JSONException jsonException) {
                throw new RuntimeException("Failed to parse JSON data: " + jsonException.getMessage(), jsonException);
            }
        }

        return events;
    }

    /**
     * Converts a single JSON object into an {@link Event}.
     *
     * @param jsonEvent the JSON representation of the event.
     * @return an {@link Event}.
     */
    public Event eventFromJson(final JSONObject jsonEvent) {
        final List<Integer> priceRange = this.extractPriceFromJson(jsonEvent);

        return new EventBuilder()
                .id(jsonEvent.optString("id", NOT_AVAILABLE))
                .name(jsonEvent.optString(NAME_KEY, UNNAMED_EVENT))
                .artists(this.extractArtists(jsonEvent))
                .venue(this.extractVenueName(jsonEvent))
                .city(this.extractCity(jsonEvent))
                .country(this.extractCountry(jsonEvent))
                .date(this.extractDateFromJson(jsonEvent))
                .priceMin(priceRange.get(0))
                .priceMax(priceRange.get(1))
                .ticketUrl(jsonEvent.optString("url", "#"))
                .genres(this.extractGenres(jsonEvent))
                .imageURL(this.extractImageUrl(jsonEvent))
                .build();
    }

    private JSONObject getEmbedded(final JSONObject jsonEvent) {
        JSONObject result = null;
        if (jsonEvent != null && jsonEvent.has(EMBEDDED_KEY)) {
            result = jsonEvent.getJSONObject(EMBEDDED_KEY);
        }
        return result;
    }

    private JSONObject getVenue(final JSONObject embedded) {
        JSONObject result = null;
        if (embedded != null && embedded.has("venues")) {
            final JSONArray venues = embedded.getJSONArray("venues");
            if (!venues.isEmpty()) {
                result = venues.getJSONObject(0);
            }
        }
        return result;
    }

    /**
     * Extracts the date from an event JSON object.
     *
     * @param jsonEvent The event JSON object.
     * @return The date of the event, or null if not available or parsing fails.
     */
    public LocalDate extractDateFromJson(final JSONObject jsonEvent) {
        LocalDate date = null;
        try {
            if (jsonEvent.has("dates")) {
                final JSONObject start = jsonEvent.getJSONObject("dates").getJSONObject("start");
                final String startDateTimeStr = start.optString("dateTime");
                if (startDateTimeStr != null && !startDateTimeStr.isEmpty()) {
                    date = LocalDate.parse(startDateTimeStr.split("T")[0]);
                }
            }
        }
        catch (final JSONException | DateTimeParseException runtimeException) {
            // Parsing failed, return default null
        }
        return date;
    }

    /**
     * Extracts the price range from an event JSON object.
     *
     * @param jsonEvent The event JSON object.
     * @return A list containing the minimum and maximum price. Returns DEFAULT_PRICE if not available.
     */
    public List<Integer> extractPriceFromJson(final JSONObject jsonEvent) {
        int priceMin = DEFAULT_PRICE;
        int priceMax = DEFAULT_PRICE;

        try {
            if (jsonEvent.has("priceRanges")) {
                final JSONArray prices = jsonEvent.getJSONArray("priceRanges");
                if (!prices.isEmpty()) {
                    final JSONObject priceRange = prices.getJSONObject(0);
                    priceMin = priceRange.optInt("min", DEFAULT_PRICE);
                    priceMax = priceRange.optInt("max", DEFAULT_PRICE);
                }
            }
        }
        catch (final JSONException jsonException) {
            // Parsing failed, return defaults
        }

        return List.of(priceMin, priceMax);
    }

    /**
     * Extracts the artists names from an event Json object.
     *
     * @param jsonEvent the event JSON object
     * @return a list of artist names, empty list if not available.
     */
    public List<String> extractArtists(final JSONObject jsonEvent) {
        final List<String> artists = new ArrayList<>();
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        if (embedded != null && embedded.has("attractions")) {
            final JSONArray artistArray = embedded.getJSONArray("attractions");
            for (int i = 0; i < artistArray.length(); i++) {
                final JSONObject artist = artistArray.getJSONObject(i);
                artists.add(artist.getString(NAME_KEY));
            }
        }
        return artists;
    }

    /**
     * Extracts the venue name from an event JSON Object.
     *
     * @param jsonEvent the event JSON object.
     * @return the venue name, or "N/A" if not available.
     */
    public String extractVenueName(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        String venueName = NOT_AVAILABLE;

        if (venue != null) {
            venueName = venue.optString(NAME_KEY, NOT_AVAILABLE);
        }

        return venueName;
    }

    /**
     * Extracts the city name from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the city name, "N/A" if not available.
     */
    public String extractCity(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        String city = NOT_AVAILABLE;

        if (venue != null && venue.has("city")) {
            city = venue.getJSONObject("city").optString(NAME_KEY, NOT_AVAILABLE);
        }

        return city;
    }

    /**
     * Extracts the country from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the country name, return "N/A" if not available.
     */
    public String extractCountry(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        String country = NOT_AVAILABLE;

        if (venue != null && venue.has("country")) {
            country = venue.getJSONObject("country").optString(NAME_KEY, NOT_AVAILABLE);
        }

        return country;
    }

    /**
     * Extracts the genres and subgenres from an event JSON object.
     *
     * @param jsonEvent the event JSON object
     * @return a list of genres, returns an empty list if none are found.
     */
    public List<String> extractGenres(final JSONObject jsonEvent) {
        final Set<String> genres = new LinkedHashSet<>();

        if (jsonEvent.has("classifications")) {
            final JSONArray classifications = jsonEvent.getJSONArray("classifications");

            for (int i = 0; i < classifications.length(); i++) {
                final JSONObject classification = classifications.getJSONObject(i);
                this.addGenreIfValid(genres, classification, "genre");
                this.addGenreIfValid(genres, classification, "subGenre");
            }
        }
        return new ArrayList<>(genres);
    }

    private void addGenreIfValid(final Set<String> genres, final JSONObject classification, final String key) {
        if (classification.has(key)) {
            final String name = classification.getJSONObject(key).optString(NAME_KEY);
            if (name != null && !name.isEmpty() && !UNDEFINED_GENRE.equalsIgnoreCase(name)) {
                genres.add(name.trim());
            }
        }
    }

    /**
     * Extracts the URL of the first image from an event JSON object.
     *
     * @param jsonEvent the event JSON object.
     * @return the URL of the first image, if available, otherwise an empty string.
     */
    public String extractImageUrl(final JSONObject jsonEvent) {
        String url = "";
        if (jsonEvent.has("images")) {
            final JSONArray images = jsonEvent.getJSONArray("images");
            if (!images.isEmpty()) {
                url = images.getJSONObject(0).getString("url");
            }
        }
        return url;
    }
}
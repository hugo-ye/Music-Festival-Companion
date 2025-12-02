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

public class SearchEventInteractor implements SearchEventInputBoundary {

    private static final int DEFAULT_PRICE = -1;
    private static final String NOT_AVAILABLE = "N/A";
    private static final String UNNAMED_EVENT = "Unnamed Event";
    private static final String UNDEFINED_GENRE = "Undefined";
    private static final String NAME_KEY = "name";
    private static final String EMBEDDED_KEY = "_embedded";
    private final SearchEventDataAccessInterface dataAccess;
    private final SearchEventOutputBoundary presenter;

    public SearchEventInteractor(final SearchEventDataAccessInterface dataAccess,
                                 final SearchEventOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

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

    public List<Event> createEventsFromJson(final String dataJson) {
        final JSONObject base = new JSONObject(dataJson);
        final List<Event> events = new ArrayList<>();
        final JSONObject embedded = base.optJSONObject(EMBEDDED_KEY);

        if (embedded != null) {
            final JSONArray eventsArray = embedded.getJSONArray("events");
            for (int i = 0; i < eventsArray.length(); i++) {
                final JSONObject jsonEvent = eventsArray.getJSONObject(i);
                final Event event = this.eventFromJson(jsonEvent);
                events.add(event);
            }
        }
        return events;
    }

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
        return jsonEvent.getJSONObject(EMBEDDED_KEY);
    }

    private JSONObject getVenue(final JSONObject embedded) {
        final JSONArray venues = embedded.getJSONArray("venues");
        return venues.getJSONObject(0);
    }

    public LocalDate extractDateFromJson(final JSONObject jsonEvent) {
        final JSONObject dates = jsonEvent.getJSONObject("dates");
        final JSONObject start = dates.getJSONObject("start");
        final String startDateTimeStr = start.optString("dateTime", "");

        if (!startDateTimeStr.isEmpty()) {
            return LocalDate.parse(startDateTimeStr.split("T")[0]);
        } else {
            return null;
        }
    }

    public List<Integer> extractPriceFromJson(final JSONObject jsonEvent) {
        int priceMin = DEFAULT_PRICE;
        int priceMax = DEFAULT_PRICE;

        final JSONArray prices = jsonEvent.optJSONArray("priceRanges");
        if (prices != null) {
            final JSONObject priceRange = prices.getJSONObject(0);
            priceMin = priceRange.optInt("min", DEFAULT_PRICE);
            priceMax = priceRange.optInt("max", DEFAULT_PRICE);
        }

        return List.of(priceMin, priceMax);
    }

    public List<String> extractArtists(final JSONObject jsonEvent) {
        final List<String> artists = new ArrayList<>();
        List<String> result = new ArrayList<>();
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONArray artistArray = embedded.optJSONArray("attractions");

        if (artistArray != null) {
            for (int i = 0; i < artistArray.length(); i++) {
                final JSONObject artist = artistArray.getJSONObject(i);
                artists.add(artist.optString(NAME_KEY, ""));
            }
            result = artists;
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    public String extractVenueName(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        return venue.optString(NAME_KEY, NOT_AVAILABLE);
    }

    public String extractCity(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        final JSONObject cityObj = venue.getJSONObject("city");
        return cityObj.optString(NAME_KEY, NOT_AVAILABLE);
    }

    public String extractCountry(final JSONObject jsonEvent) {
        final JSONObject embedded = this.getEmbedded(jsonEvent);
        final JSONObject venue = this.getVenue(embedded);
        final JSONObject countryObj = venue.getJSONObject("country");
        return countryObj.optString(NAME_KEY, NOT_AVAILABLE);
    }

    public List<String> extractGenres(final JSONObject jsonEvent) {
        final Set<String> genres = new LinkedHashSet<>();
        final JSONArray classifications = jsonEvent.optJSONArray("classifications");

        for (int i = 0; i < classifications.length(); i++) {
            final JSONObject classification = classifications.getJSONObject(i);
            this.addGenreIfValid(genres, classification, "genre");
            this.addGenreIfValid(genres, classification, "subGenre");
        }

        return new ArrayList<>(genres);
    }

    private void addGenreIfValid(final Set<String> genres, final JSONObject classification, final String key) {
        final JSONObject genreObj = classification.optJSONObject(key);
        if (genreObj != null) {
            final String name = genreObj.optString(NAME_KEY, "");
            genres.add(name.trim());
        }
    }

    public String extractImageUrl(final JSONObject jsonEvent) {
        String result = "";
        final JSONArray images = jsonEvent.optJSONArray("images");
        final JSONObject firstImage = images.getJSONObject(0);
        result = firstImage.optString("url", "");
        return result;
    }
}
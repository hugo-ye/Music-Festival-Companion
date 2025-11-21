package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.*;
import entity.Event;
import okhttp3.*;

import use_case.search_event.SearchEventDataAccessInterface;

/**
 * The DAO for fetching events from the TicketMaster API.
 */
public class DBDataAccessObject implements SearchEventDataAccessInterface {
    private static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String API_KEY = System.getenv("DB_KEY");

    @Override
    public String search(String keyword, String countryCode, String city, String startDateTime, String endDateTime, String genreIds) {
        // Getting music events from TicketMaster
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder();
        urlBuilder.addQueryParameter("apikey", API_KEY);
        // Get only music events
        urlBuilder.addQueryParameter("classificationName", "music");
        urlBuilder.addQueryParameter("size", "100");

        urlBuilder.addQueryParameter("countryCode", countryCode);
        urlBuilder.addQueryParameter("city", city);
        urlBuilder.addQueryParameter("startDateTime", startDateTime);
        urlBuilder.addQueryParameter("endDateTime", endDateTime);
        urlBuilder.addQueryParameter("genreId", genreIds);
        urlBuilder.addQueryParameter("keyword", keyword);

        final Request request = new Request.Builder().url(urlBuilder.build()).build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Checking if body is not null
                assert response.body() != null;
                return response.body().string();
            } else {
                throw new IOException("Something went wrong when fetching" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

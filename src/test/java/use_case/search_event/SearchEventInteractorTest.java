package use_case.search_event;

import entity.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SearchEventInteractorTest {
    @Test
    public void successTest() {
        final String keyword = "test";
        final String country = "US";
        final String city = "New York";
        final String genreIds = "KnvZfZ7vAve";
        final String startDate = "2023-01-01";
        final String endDate = "2023-01-02";

        final String mockJson = "{" +
                "\"_embedded\": {" +
                "\"events\": [{" +
                "\"id\": \"123\"," +
                "\"name\": \"Test Event\"," +
                "\"url\": \"[http://example.com](http://example.com)\"," +
                "\"dates\": { \"start\": { \"dateTime\": \"2023-01-01T10:00:00Z\" } }," +
                "\"priceRanges\": [{ \"min\": 10, \"max\": 50 }]," +
                "\"_embedded\": {" +
                "\"attractions\": [{ \"name\": \"Test Artist\" }]," +
                "\"venues\": [{" +
                "\"name\": \"Test Venue\"," +
                "\"city\": { \"name\": \"New York\" }," +
                "\"country\": { \"name\": \"United States Of America\" }" +
                "}]" +
                "}," +
                "\"classifications\": [{ \"genre\": { \"name\": \"Pop\" } }]," +
                "\"images\": [{ \"url\": \"[http://image.com](http://image.com)\" }]" +
                "}]" +
                "}" +
                "}";
        SearchEventInputData inputData = new SearchEventInputData(keyword, country, city, genreIds, startDate, endDate);
        SearchEventDataAccessInterface dataAccess = new SearchEventDataAccessInterface() {
            @Override
            public String search(String k, String c, String ci, String s, String e, String g) {
                assertEquals(keyword, k);
                assertEquals(country, c);
                assertEquals(city, ci);
                return mockJson;
            }
        };
        SearchEventOutputBoundary presenter = new SearchEventOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchEventOutputData outputData) {
                List<Event> events = outputData.getEvents();
                assertEquals(1, events.size());
                Event event = events.get(0);
                assertEquals("Test Event", event.getName());
                assertEquals("123", event.getId());
                assertEquals("Test Venue", event.getVenue());
                assertEquals("New York", event.getCity());
                assertEquals(LocalDate.of(2023, 1, 1), event.getDate());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail for valid json");
            }
        };
        SearchEventInteractor interactor = new SearchEventInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    public void emptyTest() {
        SearchEventInputData inputData = new SearchEventInputData("", "", "", "", "", "");
        SearchEventDataAccessInterface dataAccess = (String k, String c, String ci, String s, String e, String g) -> "";
        SearchEventOutputBoundary presenter = new SearchEventOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchEventOutputData outputData) {
                assertNotNull(outputData.getEvents());
                assertTrue(outputData.getEvents().isEmpty());
            }

            @Override
            public void prepareFailView(String error) {
                fail("should succeed with empty list");
            }
        };
        SearchEventInteractor interactor = new SearchEventInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void jsonParsingExceptionTest() {
        SearchEventDataAccessInterface dataAccess = (k, c, ci, s, e, g) -> "{ invalid_json }";
        SearchEventInputData inputData = new SearchEventInputData("", "", "", "", "", "");
        SearchEventOutputBoundary presenter = new SearchEventOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchEventOutputData outputData) {
                fail("Should not succeed for invalid json");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Failed to parse JSON response", error);
            }
        };
        SearchEventInteractor interactor = new SearchEventInteractor(dataAccess, presenter);
        assertThrows(RuntimeException.class, () -> interactor.execute(inputData));
    }


}

package use_case.search_event;

public class SearchEventInputData {
    private final String keyword;
    private final String country;
    private final String city;
    private final String genreIds;
    private final String startDate;
    private final String endDate;

    public SearchEventInputData(String keyword, String country,
                                String city, String genreIds, String startDate, String endDate) {
        // Artist is included in keyword
        this.keyword = keyword;
        this.country = country;
        this.city = city;
        this.genreIds = genreIds;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genreIds;
    }

    public String getCity() {
        return city;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}

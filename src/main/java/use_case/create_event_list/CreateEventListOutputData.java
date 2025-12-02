package use_case.create_event_list;

/**
 * Output data for the Create Event List use case.
 * This data transfer object contains the id and name of the
 * new created event list, which the presenter passes to the UI layer
 * through the view model.
 */
public class CreateEventListOutputData {

    private final String listId;
    private final String listName;

    /**
     * Constructs output data for a newly created event list.
     *
     * @param listId   the unique id of the created list
     * @param listName the display name of the created list
     */
    public CreateEventListOutputData(String listId, String listName) {
        this.listId = listId;
        this.listName = listName;
    }

    /**
     * Returns the id of the created event list.
     *
     * @return the list id
     */
    public String getListId() {
        return listId;
    }

    /**
     * Returns the name of the created event list.
     *
     * @return the list name
     */
    public String getListName() {
        return listName;
    }
}

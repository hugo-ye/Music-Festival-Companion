package use_case.delete_event_list;

/**
 * Output data for the Delete Event List use case.
 * This model contains the ID of the list that was successfully deleted.
 */
public class DeleteEventListOutputData {

    private final String listId;

    /**
     * Constructs a new output data object containing the ID of the deleted list.
     *
     * @param listId the id of the list that was deleted
     */
    public DeleteEventListOutputData(String listId) {
        this.listId = listId;
    }

    /**
     * Returns the ID of the deleted list.
     *
     * @return the deleted list's ID
     */
    public String getListId() {
        return listId;
    }
}

package use_case.delete_event_list;

/**
 * Data access interface for the Delete Event List use case.
 * Provides methods for checking list existence and deleting lists.
 */
public interface DeleteEventListDataAccessInterface {

    /**
     * Returns whether a list with the given ID exists for the current user.
     *
     * @param listId the ID of the list to check
     * @return true if a list with this ID exists, false otherwise
     */
    boolean existsById(String listId);

    /**
     * Returns whether the given ID corresponds to the master list, which cannot be deleted.
     *
     * @param listId the list ID to check
     * @return true if the list is the master list, false otherwise
     */
    boolean isMasterList(String listId);

    /**
     * Deletes the list associated with the given ID.
     *
     * @param listId the ID of the list to delete
     */
    void deleteById(String listId);
}

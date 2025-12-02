package use_case.delete_event_list;

public interface DeleteEventListDataAccessInterface {
    /**
     * To determine whether the MasterList or eventList is existing by using id of the user.
     * @param listId the id of target eventList/MasterList
     * @return whether the MasterList/EventList existing
     */
    boolean existsById(String listId);

    /**
     * To check whether the MasterList's id is same as provided.
     * @param listId target id of MasterList
     * @return whether the id of MasterList has same id as provided.
     */
    boolean isMasterList(String listId);

    /**
     * Delete a eventList by given listId.
     * @param listId target eventList id.
     */
    void deleteById(String listId);
}

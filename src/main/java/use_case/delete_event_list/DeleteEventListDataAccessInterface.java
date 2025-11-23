package use_case.delete_event_list;

public interface DeleteEventListDataAccessInterface {
    boolean existsById(String listId);
    boolean isMasterList(String listId);
    void deleteById(String listId);
}
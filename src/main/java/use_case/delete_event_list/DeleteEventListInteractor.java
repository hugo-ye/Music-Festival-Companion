package use_case.delete_event_list;

/**
 * The {@code DeleteEventListInteractor} Handles the logic for deleting an existing event list.
 * Ensures the list exists, is not the Master List, and then deletes it.
 */
public class DeleteEventListInteractor implements DeleteEventListInputBoundary {

    private final DeleteEventListDataAccessInterface dataAccess;
    private final DeleteEventListOutputBoundary presenter;

    public DeleteEventListInteractor(DeleteEventListDataAccessInterface dataAccess,
                                     DeleteEventListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     *
     * Executes the delete event list use case.
     * The method performs the following steps:
     * Trims and validates the input data (list id), Fails if the list does not
     * exist or is the Master List, and Deletes the list and reports success otherwise.
     *
     * @param input contains the ID of the list to delete.
     */
    @Override
    public void execute(DeleteEventListInputData input) {

        final String rawId = input.getListId();
        final String listId = rawId.trim();

        if (!dataAccess.existsById(listId)) {
            presenter.prepareFailView("List does not exist.");
            return;
        }
        if (dataAccess.isMasterList(listId)) {
            presenter.prepareFailView("Master List cannot be deleted.");
            return;
        }
        dataAccess.deleteById(listId);

        final DeleteEventListOutputData outputData =
                new DeleteEventListOutputData(listId);
        presenter.prepareSuccessView(outputData);
    }
}

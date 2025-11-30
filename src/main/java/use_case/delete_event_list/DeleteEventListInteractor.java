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
     * The method performs the following steps: Trims and validates the input data(list id), Fails if the list does not
     * exist or is the Master List, and Deletes the list and reports success otherwise.
     *
     * @param inputData contains the ID of the list to delete.
     */
    @Override
    public void execute(DeleteEventListInputData inputData) {

        String rawId = inputData.getListId();
        String listId = rawId.trim(); // Fixed

        // Check list exists
        if (!dataAccess.existsById(listId)) {
            presenter.prepareFailView("List does not exist.");
            return; // Safety Check, won't actually be used unless UI is bugged
        }

        // Master List cannot be deleted
        if (dataAccess.isMasterList(listId)) {
            presenter.prepareFailView("Master List cannot be deleted.");
            return;
        }
        // Delete
        dataAccess.deleteById(listId);

        // call presenter
        DeleteEventListOutputData outputData =
                new DeleteEventListOutputData(listId);
        presenter.prepareSuccessView(outputData);
    }
}
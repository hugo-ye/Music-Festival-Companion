package use_case.delete_event_list;

public class DeleteEventListInteractor implements DeleteEventListInputBoundary {

    private final DeleteEventListDataAccessInterface dataAccess;
    private final DeleteEventListOutputBoundary presenter;

    public DeleteEventListInteractor(DeleteEventListDataAccessInterface dataAccess,
                                     DeleteEventListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteEventListInputData inputData) {
        String listId = inputData.getListId(); //

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

        // Addtional condition to check logged in state? to be implmeneted
    }
}
package use_case.display_event_list;

import entity.User;
import entity.EventList;
import use_case.login.LoginSessionDataAccessInterface;

public class DisplayEventListInteractor implements DisplayEventListInputBoundary {

    private final LoginSessionDataAccessInterface sessionDataAccess;
    private final DisplayEventListOutputBoundary presenter;
    private final String MASTER_LIST_ID = "master_list";

    public DisplayEventListInteractor(LoginSessionDataAccessInterface sessionDataAccess, DisplayEventListOutputBoundary presenter) {
        this.sessionDataAccess = sessionDataAccess;
        this.presenter = presenter;
    }

    public void execute(DisplayEventListInputData inputData) {
        String listId = inputData.getListId();
        User currentUser = sessionDataAccess.getCurrentUser();

        if (currentUser == null) {
            presenter.prepareFailView("User is not logged in.");
            return;
        }

        EventList targetList = null;

        // Check if the requested ID matches the Master List
        if (currentUser.getMasterList() != null &&
                (currentUser.getMasterList().getId().equals(listId) || MASTER_LIST_ID.equals(listId))) {
            targetList = currentUser.getMasterList();
        }
        // Check the user's custom lists
        else {
            targetList = currentUser.getListById(listId);
        }

        // Prepare View
        if (targetList != null) {
            DisplayEventListOutputData outputData = new DisplayEventListOutputData(targetList);
            presenter.prepareSuccessView(outputData);
        } else {
            presenter.prepareFailView("Event list with ID '" + listId + "' not found.");
        }
    }
}
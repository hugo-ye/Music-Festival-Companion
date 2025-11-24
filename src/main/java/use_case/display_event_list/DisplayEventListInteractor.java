package use_case.display_event_list;

import entity.User;
import entity.EventList;
import use_case.login.LoginSessionDataAccessInterface;

public class DisplayEventListInteractor {

    private final LoginSessionDataAccessInterface sessionDataAccess;
    private final DisplayEventListOutputBoundary presenter;

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

        EventList eventList = currentUser.getListById(listId);

        if (eventList == null) {
            if (currentUser.getMasterList().getId().equals(listId)) {
                eventList = currentUser.getMasterList();
            } else {
                presenter.prepareFailView("Event list not found for current user.");
                return;
            }
        }

        DisplayEventListOutputData outputData = new DisplayEventListOutputData(eventList);
        presenter.prepareSuccessView(outputData);
    }
}
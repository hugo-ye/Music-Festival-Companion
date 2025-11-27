package use_case.display_event_list;

import entity.User;
import entity.EventList;
import use_case.login.LoginSessionDataAccessInterface;

/**
 * Interactor for the DisplayEventList use case.
 * The {@code DisplayEventListInteractor} has the responsibility to retrieve the currently logged in user,
 * gets the eventList given the id of the list and pass the result to the output boundary. If the user is not logged
 * in or the event list can not be found then the Interactor calls the preparefailview method
 */
public class DisplayEventListInteractor {

    private final LoginSessionDataAccessInterface sessionDataAccess;
    private final DisplayEventListOutputBoundary presenter;
    private final String MASTER_LIST_ID = "master_list";

    public DisplayEventListInteractor(LoginSessionDataAccessInterface sessionDataAccess, DisplayEventListOutputBoundary presenter) {
        this.sessionDataAccess = sessionDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the Display Event List use case using the provided input data.
     * <p>
     *     This method performs the following steps: Retrieves the current user, checks whether the user is logged in,
     *     Retrieves the Eventlist given an id, if no such list exists then checks if the list is the masterlist,
     *     Returns a success view with the event list if found, or a failure view otherwise.
     * </p>
     * @param inputData the input containing the ID of the event list to display
     */
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
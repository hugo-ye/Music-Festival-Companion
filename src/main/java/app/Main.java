package app;

import javax.swing.JFrame;

/**
 * The entry point of the application.
 *
 */
public class Main {
    /**
     * Launches the application by building and displaying the main window.
     *
     * @param args unused command-line parameters
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addSearchView()
                .addDisplayEventView()
                .addDisplayAllEventListsView()
                .addDisplayEventView()
                .addDisplaySearchResultsView()
                .addDisplayNotificationView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addSearchUseCase()
                .addLogoutUseCase()
                .addSortUseCase()
                .addDisplayEventUseCase()
                .addCreateEventListUseCase()
                .addDisplayEventListUseCase()
                .addDeleteEventListUseCase()
                .addDisplayEventListsUseCase()
                .addSaveEventToListUseCase()
                .addDisplayNotificationUseCase()
                .addAttendEventUseCase()
                .addRemoveEventFromListUseCase()
                .build();

        application.pack();
        application.setVisible(true);

    }
}

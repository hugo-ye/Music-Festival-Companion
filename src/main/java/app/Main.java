package app;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

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
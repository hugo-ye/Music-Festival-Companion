package use_case.reminder_event;

import entity.Event;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/* Application business logic for showing upcoming attending events. */
public final class ShowUpcomingInteractor implements ShowUpcomingInputBoundary {

    private final EventReadOnlyGateway gateway;
    private final ShowUpcomingOutputBoundary presenter;

    public ShowUpcomingInteractor(EventReadOnlyGateway gateway,
                                  ShowUpcomingOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(ShowUpcomingInputData input) {
        LocalDate start = input.getNow();
        LocalDate end   = start.plusDays(input.getWindowDays());

        // Pull entities from the gateway (DB/File/API behind the interface).
        List<Event> events = gateway.findAttendingBetween(start, end);

        // Sort then format to UI-friendly lines.
        List<String> lines = events.stream()
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(Event::getName))
                .map(e -> e.getName() + " — " + e.getDate())
                .collect(Collectors.toList());

        presenter.present(new ShowUpcomingOutputData(lines));
    }
}


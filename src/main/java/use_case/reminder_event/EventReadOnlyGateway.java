package use_case.reminder_event;

import entity.Event;
import java.time.LocalDate;
import java.util.List;

/* Read-only gateway used by the use case; implemented in data_access. */
public interface EventReadOnlyGateway {
    List<Event> findAttendingBetween(LocalDate startInclusive, LocalDate endInclusive);
}


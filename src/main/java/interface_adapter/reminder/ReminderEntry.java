package interface_adapter.reminder;

import java.time.LocalDate;
import java.util.Objects;

/*
 Lightweight UI DTO: just the data the view needs (name + date).
 This is NOT a domain entity and contains no business logic.
 */
public final class ReminderEntry {
    private final String name;
    private final LocalDate date;

    public ReminderEntry(String name, LocalDate date) {
        this.name = Objects.requireNonNull(name, "name");
        this.date = Objects.requireNonNull(date, "date");
    }

    /* Event title to display in the popup. */
    public String getName() { return name; }

    /* Event date to display in the popup. */
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return "ReminderEntry{name='" + name + "', date=" + date + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReminderEntry)) return false;
        ReminderEntry that = (ReminderEntry) o;
        return name.equals(that.name) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}

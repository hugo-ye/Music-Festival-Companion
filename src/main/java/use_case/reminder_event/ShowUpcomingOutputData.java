package use_case.reminder_event;

import java.util.List;

/* Response model: UI-ready lines to render. */
public final class ShowUpcomingOutputData {
    private final List<String> lines;

    public ShowUpcomingOutputData(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() { return lines; }
    public boolean hasItems()      { return lines != null && !lines.isEmpty(); }
}


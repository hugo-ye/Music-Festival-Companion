package interface_adapter.reminder_event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

/* View-facing state: the view only reads these lines to render. */
public final class ReminderViewModel {
    public static final String PROP_LINES = "lines";

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private List<String> lines = Collections.emptyList();

    public List<String> getLines() { return lines; }
    public boolean hasItems()      { return lines != null && !lines.isEmpty(); }

    public void setLines(List<String> newLines) {
        List<String> old = this.lines;
        this.lines = newLines;
        pcs.firePropertyChange(PROP_LINES, old, newLines);
    }

    public void addPropertyChangeListener(PropertyChangeListener l)    { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }
}


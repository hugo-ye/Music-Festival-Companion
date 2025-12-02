package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_event_list.CreateEventListController;
import interface_adapter.create_event_list.CreateEventListState;
import interface_adapter.create_event_list.CreateEventListViewModel;
import interface_adapter.create_event_list.EventListSummary;
import interface_adapter.delete_event_list.DeleteEventListController;
import interface_adapter.display_event_list.DisplayEventListController;

/**
 * The View for the Display Event Lists Use Case.
 */
public class AllEventListsView extends JPanel implements PropertyChangeListener {

    private final String viewName = "event lists";

    private final CreateEventListViewModel createEventListViewModel;
    private final ViewManagerModel viewManagerModel;

    // Controllers
    private CreateEventListController createEventListController;
    private DeleteEventListController deleteEventListController;
    private DisplayEventListController displayEventListController;

    // Swing components
    private final JButton createListButton;
    private final JPanel eventsPanel;
    private final JButton backButton;

    public AllEventListsView(CreateEventListViewModel createEventListViewModel,
                             ViewManagerModel viewManagerModel) {

        this.createEventListViewModel = createEventListViewModel;
        this.viewManagerModel = viewManagerModel;

        this.createEventListViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);

        // Top panel
        final JPanel topPanel = ViewStyle.createSectionPanel(new BorderLayout());

        final JLabel titleLabel = new JLabel("My Lists");
        ViewStyle.applyTitleStyle(titleLabel);

        createListButton = new JButton("Create New List");
        ViewStyle.applyPrimaryButtonStyle(createListButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(createListButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        eventsPanel.setBorder(new EmptyBorder(0, 20, 0, 20)); // Add side padding

        final JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        final JPanel bottomPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back to Search");
        ViewStyle.applyButtonStyle(backButton);

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners

        backButton.addActionListener(e -> {
            viewManagerModel.setState("search event");
            viewManagerModel.firePropertyChanged();
        });

        createListButton.addActionListener(e -> {
            if (createEventListController == null) {
                return;
            }

            final Window window = SwingUtilities.getWindowAncestor(this);
            final Frame parent = window instanceof Frame ? (Frame) window : null;

            final CreateListDialog dialog = new CreateListDialog(parent);
            dialog.setVisible(true);

            final String name = dialog.getEnteredName();
            if (name != null && !name.isBlank()) {
                createEventListController.execute(name);
            }
        });

        rebuildListRows(createEventListViewModel.getState());
    }

    public void setCreateEventListController(CreateEventListController createEventListController) {
        this.createEventListController = createEventListController;
    }

    public void setDeleteEventListController(DeleteEventListController deleteEventListController) {
        this.deleteEventListController = deleteEventListController;
    }

    public void setDisplayEventListController(DisplayEventListController displayEventListController) {
        this.displayEventListController = displayEventListController;
    }

    private void rebuildListRows(CreateEventListState state) {
        eventsPanel.removeAll();

        // Add top spacing
        eventsPanel.add(Box.createVerticalStrut(10));

        // Add master list
        final JPanel masterCard = createListCard("Master List", "master_list", false);
        eventsPanel.add(masterCard);
        eventsPanel.add(Box.createVerticalStrut(15)); // Spacing between cards

        // Add user created lists
        for (EventListSummary summary : state.getLists()) {
            final JPanel listCard = createListCard(summary.getName(), summary.getId(), true);
            eventsPanel.add(listCard);
            eventsPanel.add(Box.createVerticalStrut(15));
        }

        eventsPanel.add(Box.createVerticalGlue());

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private JPanel createListCard(String listName, String listId, boolean isDeleteable) {
        final JPanel card = ViewStyle.createCardPanel();
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Left: list name
        final JLabel nameLabel = new JLabel(listName);
        ViewStyle.applyHeaderStyle(nameLabel);
        card.add(nameLabel, BorderLayout.WEST);

        // Right: buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        final JButton viewButton = new JButton("View");
        ViewStyle.applyButtonStyle(viewButton);

        viewButton.addActionListener(e -> {
            if (displayEventListController != null) {
                displayEventListController.execute(listId);
            }
        });
        buttonPanel.add(viewButton);

        if (isDeleteable) {
            final JButton deleteButton = new JButton("Delete");
            ViewStyle.applyButtonStyle(deleteButton);
            deleteButton.setForeground(ViewStyle.ERROR_COLOR);

            if (deleteEventListController == null) {
                deleteButton.setEnabled(false);
            }
            else {
                deleteButton.addActionListener(e -> {
                    final int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete '" + listName + "'?",
                            "Delete List",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteEventListController.execute(listId);
                    }
                });
            }
            buttonPanel.add(deleteButton);
        }

        card.add(buttonPanel, BorderLayout.EAST);
        return card;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }

        final CreateEventListState state = (CreateEventListState) evt.getNewValue();

        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage()
            );
        }
        rebuildListRows(state);
    }

    public String getViewName() {
        return viewName;
    }
}

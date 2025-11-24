package view;

import entity.EventList;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_event_list.CreateEventListController;
import interface_adapter.create_event_list.CreateEventListState;
import interface_adapter.create_event_list.CreateEventListViewModel;
import interface_adapter.create_event_list.EventListSummary;
import interface_adapter.delete_event_list.DeleteEventListController;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListState;
import interface_adapter.display_event_list.DisplayEventListViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AllEventListsView extends JPanel implements PropertyChangeListener {

    private final String viewName = "event lists";
    // Implement View Models and Controllers here
    private final CreateEventListViewModel createEventListViewModel;
    private final CreateEventListController createEventListController;


    private DeleteEventListController deleteEventListController;

    private final DisplayEventListController displayEventListController;
    private final ViewManagerModel viewManagerModel;

    // Swing components
    private final JButton createListButton;
    private final JPanel eventsPanel;
    private final JButton masterViewButton;
    private final JButton backButton;
    private final JLabel errorLabel;

    public AllEventListsView(CreateEventListViewModel createEventListViewModel,
                             ViewManagerModel viewManagerModel,
                             CreateEventListController eventListController,
                             DeleteEventListController deleteEventListController,
                             DisplayEventListController displayEventListController) {
        this.viewManagerModel = viewManagerModel;

        this.createEventListController = eventListController;
        this.createEventListViewModel = createEventListViewModel;
        this.createEventListViewModel.addPropertyChangeListener(this);

        this.displayEventListController = displayEventListController;

        this.deleteEventListController = deleteEventListController;
        // Instantiate the View Models and Controllers here
        this.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        createListButton = new JButton("Create List");
        topPanel.add(createListButton);
        add(topPanel, BorderLayout.NORTH);


        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        // Master List row
        JPanel masterListPanel = new JPanel();
        masterListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel masterLabel = new JLabel("Master List");
        masterViewButton = new JButton("View");

        masterListPanel.add(masterLabel);
        masterListPanel.add(masterViewButton);
        masterListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        eventsPanel.add(masterListPanel);

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Error Label + Back Button
        JPanel bottomPanel = new JPanel(new BorderLayout());

        errorLabel = new JLabel(""); //Error messages e.g duplicate name
        bottomPanel.add(errorLabel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        bottomPanel.add(backPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        masterViewButton.addActionListener(e -> {
            viewManagerModel.setState("event list");
            viewManagerModel.firePropertyChanged();
        });


        backButton.addActionListener(e -> {
            viewManagerModel.setState("search event");
            viewManagerModel.firePropertyChanged();
        });

        createListButton.addActionListener(e -> {
            if (createEventListController == null) {
                return;
            }
            Window window = SwingUtilities.getWindowAncestor(this);
            Frame parent = window instanceof Frame ? (Frame) window : null;

            // open dialog
            CreateListDialog dialog = new CreateListDialog(parent);
            dialog.setVisible(true);  // blocks until user closes

            // read entered name
            String name = dialog.getEnteredName();

            if (name != null && !name.isBlank()) {
                createEventListController.create(name);
            }
        });
    }

    public void addEventListRow(String listId, String listName) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel(listName);
        JButton viewButton = new JButton("View");
        JButton deleteButton = new JButton("Delete");

        row.add(nameLabel);
        row.add(viewButton);
        row.add(deleteButton);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        viewButton.addActionListener(e -> {
            displayEventListController.execute(listId);
        });

        // Wire delete button -> DeleteEventListController
        deleteButton.addActionListener(e -> {
            if (deleteEventListController != null) {
                deleteEventListController.delete(listId);
            }
        });

        eventsPanel.add(row);
    }


    private void rebuildListRows(CreateEventListState state) {
        // Clear everything
        eventsPanel.removeAll();

        // Re-add Master List row
        JPanel masterListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel masterLabel = new JLabel("Master List");
        JButton masterViewButtonLocal = new JButton("View");
        masterListPanel.add(masterLabel);
        masterListPanel.add(masterViewButtonLocal);
        masterListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        eventsPanel.add(masterListPanel);

        // Add one row per user-created list
        for (EventListSummary summary : state.getLists()) {
            addEventListRow(summary.getId(), summary.getName());
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }

        CreateEventListState state = (CreateEventListState) evt.getNewValue();
        errorLabel.setText(state.getErrorMessage());
        rebuildListRows(state);
    }

    public String getViewName() {
        return viewName;
    }
}
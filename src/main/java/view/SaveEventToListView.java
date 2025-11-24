package view;

import entity.Event;
import entity.EventList;
import interface_adapter.save_event_to_list.SaveEventToListController;
import interface_adapter.save_event_to_list.SaveEventToListState;
import interface_adapter.save_event_to_list.SaveEventToListViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SaveEventToListView extends JDialog implements PropertyChangeListener {
    private final SaveEventToListController controller;
    private final SaveEventToListViewModel viewModel;

    // We pass 'Frame owner' so the popup is centered over the main app
    public SaveEventToListView(Frame owner, SaveEventToListController controller, SaveEventToListViewModel viewModel) {
        super(owner, "Save Event to List", true); // true = modal (blocks background)
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        createUI();
    }

    public void createUI() {
        // 1. FIX: Get data from STATE, not ViewModel directly
        SaveEventToListState state = viewModel.getState();
        EventList[] eventLists = state.getEventLists();
        Event event = state.getEvent();

        // Safety check
        if (eventLists == null || event == null) {
            JOptionPane.showMessageDialog(this, "Error: Data missing.");
            return;
        }

        this.setSize(350, 450);
        this.setLayout(new BorderLayout());

        // Header
        JLabel viewQuestion = new JLabel("Which list(s) do you want to save to?");
        viewQuestion.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewStyle.applyHeaderStyle(viewQuestion); // Apply style if you have it
        this.add(viewQuestion, BorderLayout.NORTH);

        // Checkbox Panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        List<JCheckBox> checkBoxes = new ArrayList<>();

        if (eventLists.length == 0) {
            listPanel.add(new JLabel("No lists created yet."));
        } else {
            for (EventList list : eventLists) {
                // 2. FIX: Access the name directly from the Entity object
                JCheckBox checkBox = new JCheckBox(list.getName());

                // Store the list object in the checkbox for easy retrieval later
                checkBox.putClientProperty("listObject", list);

                checkBoxes.add(checkBox);
                listPanel.add(checkBox);
                listPanel.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmButton = new JButton("Confirm Saving");

        confirmButton.addActionListener(e -> {
            List<EventList> selectedEventsList = new ArrayList<>();

            // Iterate over checkboxes to see which are selected
            for (JCheckBox box : checkBoxes) {
                if (box.isSelected()) {
                    // Retrieve the object we stored earlier
                    EventList list = (EventList) box.getClientProperty("listObject");
                    selectedEventsList.add(list);
                }
            }

            // Convert to Array for Controller
            EventList[] finalSelection = selectedEventsList.toArray(new EventList[0]);

            // Execute Controller
            controller.SaveEventToList(event, finalSelection);

            // We don't close immediately; we wait for the PropertyChange listener
            // to tell us it succeeded (see propertyChange below).
        });

        buttonPanel.add(confirmButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Center relative to parent
        this.setLocationRelativeTo(getParent());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("message".equals(evt.getPropertyName())) {
            SaveEventToListState state = (SaveEventToListState) evt.getNewValue();

            // Show the success/fail message
            JOptionPane.showMessageDialog(this, state.getMessage());

            // Close the dialog after showing the message
            this.dispose();
        }
    }
}
package view;

import entity.EventList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChooseListDialog extends JDialog {

    private final List<JCheckBox> checkBoxes = new ArrayList<>();
    private boolean saved = false;

    /**
     * Creates a dialog for choosing event lists.
     *
     * @param owner The parent frame (usually the main application window).
     * @param availableLists The list of EventList objects the user can choose from.
     */
    public ChooseListDialog(Frame owner, List<EventList> availableLists) {
        super(owner, "Choose Lists", true); // true = modal (blocks other windows)
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Select lists to save to:");
        ViewStyle.applyHeaderStyle(header);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // List Panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (availableLists == null || availableLists.isEmpty()) {
            JLabel noListLabel = new JLabel("No lists available.");
            ViewStyle.applyLabelStyle(noListLabel);
            listPanel.add(noListLabel);
        } else {
            for (EventList list : availableLists) {
                // Create a checkbox with the List Name
                JCheckBox checkBox = new JCheckBox(list.getName());
                checkBox.setFont(ViewStyle.BODY_FONT);
                checkBox.setBackground(ViewStyle.WINDOW_BACKGROUND);

                // Store the actual EventList object inside the checkbox for easy retrieval
                checkBox.putClientProperty("listObject", list);

                checkBoxes.add(checkBox);
                listPanel.add(checkBox);
                listPanel.add(Box.createVerticalStrut(5)); // Spacing
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);

        JButton cancelButton = new JButton("Cancel");
        ViewStyle.applyButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });

        JButton confirmButton = new JButton("Save");
        ViewStyle.applyPrimaryButtonStyle(confirmButton);
        confirmButton.addActionListener(e -> {
            saved = true;
            setVisible(false);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Final Setup
        setSize(350, 450);
        setLocationRelativeTo(owner);
    }

    /**
     * @return true if the user clicked "Save", false if they clicked "Cancel" or closed the window.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * @return A list of the EventList objects corresponding to the checked boxes.
     */
    public List<EventList> getSelectedLists() {
        List<EventList> selected = new ArrayList<>();
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                // Retrieve the object we stored earlier
                selected.add((EventList) box.getClientProperty("listObject"));
            }
        }
        return selected;
    }
}
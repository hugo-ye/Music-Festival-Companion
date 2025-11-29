package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import entity.EventList;

/**
 * The View for the Save Event to List Use Case.
 */
public class ChooseListDialog extends JDialog {
    private final String viewName = "choose lists";
    private final List<JCheckBox> checkBoxes = new ArrayList<>();
    private boolean saved;

    public ChooseListDialog(Frame owner, List<EventList> availableLists) {
        super(owner, "Choose Lists", true);
        setLayout(new BorderLayout());

        // Header
        final JLabel header = new JLabel("Select lists to save to:");
        ViewStyle.applyHeaderStyle(header);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // List Panel
        final JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (availableLists == null || availableLists.isEmpty()) {
            final JLabel noListLabel = new JLabel("No lists available.");
            ViewStyle.applyLabelStyle(noListLabel);
            listPanel.add(noListLabel);
        }
        else {
            for (EventList list : availableLists) {
                final JCheckBox checkBox = new JCheckBox(list.getName());
                checkBox.setFont(ViewStyle.BODY_FONT);
                checkBox.setBackground(ViewStyle.WINDOW_BACKGROUND);

                checkBox.putClientProperty("listObject", list);

                checkBoxes.add(checkBox);
                listPanel.add(checkBox);
                listPanel.add(Box.createVerticalStrut(5));
            }
        }

        final JScrollPane scrollPane = new JScrollPane(listPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);

        final JButton cancelButton = new JButton("Cancel");
        ViewStyle.applyButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });

        final JButton confirmButton = new JButton("Save");
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

    public boolean isSaved() {
        return saved;
    }

    public List<EventList> getSelectedLists() {
        final List<EventList> selected = new ArrayList<>();
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                selected.add((EventList) box.getClientProperty("listObject"));
            }
        }
        return selected;
    }

    public String getViewName() {
        return viewName;
    }
}
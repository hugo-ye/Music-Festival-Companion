package view;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The View for the Create List Use Case.
 */
public class CreateListDialog extends JDialog {
    private final String viewName = "create list";

    private final JTextField nameField = new JTextField(20);
    private final JButton createButton = new JButton("Create List");
    private final JButton cancelButton = new JButton("Cancel");
    private boolean isConfirmed;

    public CreateListDialog(Frame parent) {
        super(parent, "Create List", true);

        // Set Background
        this.getContentPane().setBackground(ViewStyle.WINDOW_BACKGROUND);

        // Main Container
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        // Title
        final JLabel titleLabel = new JLabel("New Event List");
        ViewStyle.applyHeaderStyle(titleLabel);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);

        // Label
        final JLabel instructionLabel = new JLabel("Enter a name for your list:");
        ViewStyle.applyLabelStyle(instructionLabel);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(instructionLabel, gbc);

        // Input Field
        ViewStyle.applyTextFieldStyle(nameField);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(nameField, gbc);

        // Buttons Panel
        final JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);

        ViewStyle.applyPrimaryButtonStyle(createButton);
        ViewStyle.applyButtonStyle(cancelButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);

        // Listeners
        createButton.addActionListener(e -> {
            isConfirmed = true;
            setVisible(false);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            isConfirmed = false;
            setVisible(false);
            dispose();
        });

        getRootPane().setDefaultButton(createButton);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    public String getEnteredName() {
        if (isConfirmed) {
            return nameField.getText().trim();
        }
        return null;
    }

    public String getViewName() {
        return viewName;
    }
}
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class ViewStyle {

    // =========================
    // COLORS
    // =========================

    // Backgrounds
    public static final Color WINDOW_BACKGROUND   = new Color(244, 245, 249);
    public static final Color CARD_BACKGROUND     = Color.WHITE;
    public static final Color HEADER_BACKGROUND   = new Color(234, 235, 245);

    // Text
    public static final Color TEXT_PRIMARY        = new Color(23, 24, 28);
    public static final Color TEXT_SECONDARY      = new Color(120, 122, 135);
    public static final Color TEXT_INVERTED       = Color.WHITE;

    // Status Colors
    public static final Color TEXT_PRICE          = new Color(34, 197, 94);
    public static final Color ERROR_COLOR         = new Color(239, 68, 68);

    // Accents
    public static final Color ACCENT_PRIMARY      = new Color(88, 86, 214);
    public static final Color ACCENT_PRIMARY_SOFT = new Color(232, 232, 255);
    public static final Color ACCENT_OUTLINE      = new Color(176, 175, 245);

    // Borders
    public static final Color BORDER_SUBTLE       = new Color(220, 220, 230);


    // =========================
    // FONTS
    // =========================

    public static final Font TITLE_FONT           = new Font("SansSerif", Font.BOLD, 22);
    public static final Font HEADER_FONT          = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY_FONT            = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BODY_FONT_BOLD       = new Font("SansSerif", Font.BOLD, 14);
    public static final Font SMALL_FONT           = new Font("SansSerif", Font.PLAIN, 12);


    // =========================
    // SPACING
    // =========================

    public static final Insets STANDARD_PAD       = new Insets(10, 15, 10, 15);
    public static final Insets SECTION_PAD        = new Insets(20, 20, 20, 20);
    public static final Dimension INPUT_FIELD_SIZE = new Dimension(240, 35);


    // =========================
    // FACTORY METHODS
    // =========================

    private ViewStyle() { }

    // LABELS

    public static void applyTitleStyle(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_PRIMARY);
    }

    public static void applyHeaderStyle(JLabel label) {
        label.setFont(HEADER_FONT);
        label.setForeground(TEXT_PRIMARY);
    }

    public static void applyLabelStyle(JLabel label) {
        label.setFont(BODY_FONT_BOLD);
        label.setForeground(TEXT_SECONDARY);
    }

    public static void applySecondaryLabelStyle(JLabel label) {
        label.setFont(BODY_FONT);
        label.setForeground(TEXT_SECONDARY);
    }

    public static void applyMetaLabelStyle(JLabel label) {
        label.setFont(SMALL_FONT);
        label.setForeground(TEXT_SECONDARY);
    }

    // READ-ONLY TEXT AREAS

    public static void applyValueStyle(JTextArea area) {
        area.setFont(BODY_FONT);
        area.setForeground(TEXT_PRIMARY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setOpaque(false);
    }

    public static void applyPriceStyle(JTextArea area) {
        applyValueStyle(area);
        area.setFont(HEADER_FONT);
        area.setForeground(TEXT_PRICE);
    }

    public static JTextArea createReadOnlyTextArea() {
        JTextArea area = new JTextArea();
        applyValueStyle(area);
        return area;
    }

    // INPUTS

    public static void applyTextFieldStyle(JTextField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(Color.WHITE);
        field.setCaretColor(ACCENT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_SUBTLE, 1, false),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    public static void applyComboBoxStyle(JComboBox<?> box) {
        box.setFont(BODY_FONT);
        box.setForeground(TEXT_PRIMARY);
        box.setBackground(HEADER_BACKGROUND);
        box.setBorder(null);
    }

    public static void applyListStyle(JList<?> list) {
        list.setFont(BODY_FONT);
        list.setForeground(TEXT_PRIMARY);
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(ACCENT_PRIMARY_SOFT);
        list.setSelectionForeground(ACCENT_PRIMARY);
        list.setBorder(new LineBorder(BORDER_SUBTLE, 1, false));
    }

    // IMAGES

    public static void applyImageContainerStyle(JLabel imageLabel) {
        imageLabel.setBorder(new LineBorder(BORDER_SUBTLE, 1, false));
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);
    }

    // BUTTONS

    private static void applyCommonButtonSettings(JButton button) {
        button.setFont(BODY_FONT_BOLD);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void applyButtonStyle(JButton button) {
        applyCommonButtonSettings(button);
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT_PRIMARY);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_SUBTLE, 1, false),
                new EmptyBorder(8, 16, 8, 16)
        ));
    }

    public static void applyPrimaryButtonStyle(JButton button) {
        applyCommonButtonSettings(button);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(ACCENT_PRIMARY);
        button.setForeground(TEXT_INVERTED);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    // PANELS

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_SUBTLE, 1, false),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    public static JPanel createSectionPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(HEADER_BACKGROUND);
        panel.setBorder(new EmptyBorder(SECTION_PAD));
        return panel;
    }

    // SCROLL PANE

    public static void applyScrollPaneStyle(JScrollPane scrollPane) {
        scrollPane.getViewport().setBackground(WINDOW_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
}
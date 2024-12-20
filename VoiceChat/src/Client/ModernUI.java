package Client;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ModernUI {
    // Modern color scheme
    public static final Color PRIMARY_DARK = new Color(45, 52, 54);
    public static final Color PRIMARY_LIGHT = new Color(99, 110, 114);
    public static final Color ACCENT_COLOR = new Color(0, 184, 148);
    public static final Color ACCENT_LIGHT = new Color(0, 206, 166);
    public static final Color BACKGROUND_COLOR = new Color(245, 246, 250);
    public static final Color TEXT_COLOR = new Color(47, 53, 66);
    public static final Color ERROR_COLOR = new Color(255, 71, 87);
    public static final Color SUCCESS_COLOR = new Color(46, 213, 115);

    // Modern fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    public static void applyModernUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set global UI properties
        UIManager.put("Panel.background", BACKGROUND_COLOR);
        UIManager.put("OptionPane.background", BACKGROUND_COLOR);
        UIManager.put("TextField.font", REGULAR_FONT);
        UIManager.put("Button.font", REGULAR_FONT);
        UIManager.put("Label.font", REGULAR_FONT);
    }

    // Modern rounded button
    public static class ModernButton extends JButton {
        private Color hoverColor;
        private boolean isAccent;

        public ModernButton(String text, boolean isAccent) {
            super(text);
            this.isAccent = isAccent;
            this.hoverColor = isAccent ? ACCENT_LIGHT : PRIMARY_LIGHT;
            
            setFont(REGULAR_FONT);
            setForeground(Color.WHITE);
            setBackground(isAccent ? ACCENT_COLOR : PRIMARY_DARK);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(isAccent ? ACCENT_COLOR : PRIMARY_DARK);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create gradient paint
            GradientPaint gp = new GradientPaint(0, 0, getBackground(),
                0, getHeight(), adjustBrightness(getBackground(), -0.1f));

            // Create rounded rectangle
            RoundRectangle2D.Float shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            g2.setPaint(gp);
            g2.fill(shape);

            // Add subtle border
            g2.setColor(adjustBrightness(getBackground(), -0.2f));
            g2.draw(shape);

            // Add slight inner shadow
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // Modern text field with floating label
    public static class ModernTextField extends JTextField {
        private String placeholder;
        private Color borderColor = PRIMARY_DARK;
        private boolean focused = false;

        public ModernTextField(String placeholder) {
            this.placeholder = placeholder;
            setFont(REGULAR_FONT);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    focused = true;
                    borderColor = ACCENT_COLOR;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    focused = false;
                    borderColor = PRIMARY_DARK;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw background
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

            // Draw border
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(focused ? 2f : 1f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

            // Draw placeholder
            if (getText().isEmpty() && !focused) {
                g2.setColor(PRIMARY_LIGHT);
                g2.setFont(SMALL_FONT);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(placeholder, 15, getHeight()/2 + fm.getAscent()/2 - 2);
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // Modern list with custom renderer
    public static class ModernList extends JList {
        public ModernList() {
            setFont(REGULAR_FONT);
            setBackground(Color.WHITE);
            setSelectionBackground(ACCENT_COLOR);
            setSelectionForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setCellRenderer(new ModernListCellRenderer());
        }

        private class ModernListCellRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                
                label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                if (!isSelected) {
                    label.setBackground(index % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
                }
                return label;
            }
        }
    }

    // Modern scroll pane
    public static class ModernScrollPane extends JScrollPane {
        public ModernScrollPane(Component view) {
            super(view);
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(Color.WHITE);
            getViewport().setBackground(Color.WHITE);

            // Customize scrollbar
            JScrollBar verticalBar = getVerticalScrollBar();
            verticalBar.setPreferredSize(new Dimension(8, 0));
            verticalBar.setUI(new ModernScrollBarUI());
        }
    }

    // Helper method to adjust color brightness
    private static Color adjustBrightness(Color color, float factor) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], hsb[1], Math.max(0f, Math.min(1f, hsb[2] + factor)));
    }

    // Custom ScrollBarUI for modern look
    private static class ModernScrollBarUI extends BasicScrollBarUI {
        protected void configureScrollBarColors() {
            thumbColor = ACCENT_COLOR;
            thumbDarkShadowColor = ACCENT_COLOR;
            thumbHighlightColor = ACCENT_COLOR;
            thumbLightShadowColor = ACCENT_COLOR;
            trackColor = BACKGROUND_COLOR;
            trackHighlightColor = BACKGROUND_COLOR;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(thumbColor);
            g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 1,
                    thumbBounds.width - 2, thumbBounds.height - 2, 8, 8);

            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(trackColor);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }

    // Helper methods to create components
    public static ModernButton createPrimaryButton(String text) {
        return new ModernButton(text, true);
    }

    public static ModernButton createSecondaryButton(String text) {
        return new ModernButton(text, false);
    }

    public static ModernTextField createTextField(String placeholder) {
        return new ModernTextField(placeholder);
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundBorder(PRIMARY_DARK, 1, 10),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADER_FONT);
        label.setForeground(PRIMARY_DARK);
        return label;
    }

    public static ModernList createList() {
        return new ModernList();
    }

    public static ModernScrollPane createScrollPane(Component view) {
        return new ModernScrollPane(view);
    }

    // Custom round border
    private static class RoundBorder extends AbstractBorder {
        private Color color;
        private int thickness;
        private int radius;
        private Insets insets;

        public RoundBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
            this.insets = new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness/2, y + thickness/2,
                    width - thickness, height - thickness, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return insets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            return getBorderInsets(c);
        }
    }
}
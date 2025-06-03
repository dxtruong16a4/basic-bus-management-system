package utility;
import java.awt.Component;
import java.awt.Point;
import java.util.List;
import javax.swing.*;

/**
 * JTextField tf = new JTextField(); <br>
 * List<String> suggestions = Arrays.asList(); <br>
 * TextFieldSuggestionPopup suggestionPopup = new TextFieldSuggestionPopup(tf, suggestions); <br>
 * // To show the popup, call: <br>
 * suggestionPopup.showPopup();
 */
public class SuggestionPopUp {
    private final JPopupMenu popup;
    private final JList<String> suggestionList;
    private final JTextField textField;
    private List<String> suggestions;

    /**
     * Constructor for TextFieldSuggestionPopup.
     *
     * @param textField   The JTextField to attach the suggestion popup to.
     * @param suggestions A list of suggestions to display in the popup.
     */
    public SuggestionPopUp(JTextField textField, List<String> suggestions) {
        this.textField = textField;
        this.suggestions = suggestions;
        popup = new JPopupMenu();
        suggestionList = new JList<>();
        popup.add(new JScrollPane(suggestionList));

        // Set events
        suggestionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && suggestionList.getSelectedValue() != null) {
                textField.setText(suggestionList.getSelectedValue());
                popup.setVisible(false);
            }
        });
        suggestionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && suggestionList.getSelectedValue() != null) {
                    textField.setText(suggestionList.getSelectedValue());
                    popup.setVisible(false);
                }
            }
        });
        suggestionList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && suggestionList.getSelectedValue() != null) {
                    textField.setText(suggestionList.getSelectedValue());
                    popup.setVisible(false);
                }
            }
        });

        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPopup();
            }
        });
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public void showPopup() {
        if (!popup.isVisible()) {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (String keyword : suggestions) {
                model.addElement(keyword);
            }
            suggestionList.setModel(model);
            Point p = getLocationOf(textField, textField.getTopLevelAncestor());
            popup.setPopupSize(textField.getWidth(), 100);
            popup.show(textField.getTopLevelAncestor(), p.x, p.y);
        }
    }

    private Point getLocationOf(Component c1, Component c2) {
        Point p = c1.getLocationOnScreen();
        Point frameLoc = c2.getLocationOnScreen();
        return new Point(p.x - frameLoc.x, p.y - frameLoc.y + c1.getHeight());
    }
}
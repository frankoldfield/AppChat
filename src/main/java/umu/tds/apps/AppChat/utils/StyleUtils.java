package umu.tds.apps.AppChat.utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Clase de utilidades para aplicar estilos comunes en la aplicación
 */
public class StyleUtils {
    
    // Paleta de colores oscura
    public static final Color BACKGROUND_DARK = UIManager.getColor("Panel.background");
    public static final Color BACKGROUND_DARKER = UIManager.getColor("Panel.background").darker();
    public static final Color ACCENT_COLOR = new Color(116, 127, 224);  // Violeta suave
    public static final Color SUCCESS_COLOR = new Color(80, 200, 120);  // Verde
    public static final Color ERROR_COLOR = new Color(230, 80, 80);     // Rojo
    
    // Fuentes
    public static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Dialog", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Dialog", Font.PLAIN, 14);
    public static final Font CODE_FONT = new Font("Monospaced", Font.PLAIN, 14);
    
    // Bordes
    public static Border createPanelBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BACKGROUND_DARKER, 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
    
    public static Border createTitleBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BACKGROUND_DARKER),
            title
        );
    }
    
    // Aplicar estilo a componentes
    public static void applyCardStyle(JPanel panel) {
        panel.setBackground(BACKGROUND_DARK);
        panel.setBorder(createPanelBorder());
    }
    
    public static void applyPrimaryButtonStyle(JButton button) {
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(NORMAL_FONT);
    }
    
    public static void applySecondaryButtonStyle(JButton button) {
        button.setBackground(BACKGROUND_DARKER);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(NORMAL_FONT);
    }
    
    public static void setMargin(JComponent component, int top, int left, int bottom, int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }
 // Para estilizar campos de texto
    public static void applyTextFieldStyle(JTextField textField) {
        textField.setBackground(BACKGROUND_DARKER);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    // Para estilizar áreas de texto
    public static void applyTextAreaStyle(JTextArea textArea, boolean isCode) {
        textArea.setBackground(BACKGROUND_DARKER.darker());
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BACKGROUND_DARKER, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        if (isCode) {
            textArea.setFont(CODE_FONT);
        } else {
            textArea.setFont(NORMAL_FONT);
        }
    }
}
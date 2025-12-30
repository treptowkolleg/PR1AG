package pr1.a07.plot.components;

import pr1.a07.Colors;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import java.awt.Font;

public class ModernCheckBox extends JCheckBox {
    public ModernCheckBox(String text) {
        super(text);
        initStyle();
    }

    public ModernCheckBox(String text, boolean defaultValue) {
        super(text, defaultValue);
        initStyle();
    }

    public ModernCheckBox() {
        initStyle();
    }

    private void initStyle() {
        setOpaque(false);
        setFont(new Font("SF Pro Text, Helvetica Neue, Arial", Font.PLAIN, 12));
        setForeground(Colors.BLACK);
        setIcon(new ModernCheckBoxIcon(false));
        setSelectedIcon(new ModernCheckBoxIcon(true));
        setIconTextGap(10); // Abstand zwischen Icon und Text
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0)); // vertikaler
        // Luftabstand
        setFocusPainted(false); // kein hässlicher Fokus-Rahmen
    }
}

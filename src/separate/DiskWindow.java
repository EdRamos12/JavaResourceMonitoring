/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package separate;
import resource.ResourceMonitor;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author aluno
 */
public class DiskWindow {
    private final JFrame frame;
    private final JTextArea textArea;

    public DiskWindow() {
        frame = new JFrame("Monitor de Disco");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.setLocationRelativeTo(null);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void update() {
        textArea.setText(ResourceMonitor.getInstance().getDiskUsage());
    }

    public void show() {
        frame.setVisible(true);
    }
}
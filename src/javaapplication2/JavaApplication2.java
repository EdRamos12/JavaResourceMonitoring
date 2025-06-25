package javaapplication2;

import resource.ResourceMonitor;

import javax.swing.*;
import java.awt.*;
import separate.*;

public class JavaApplication2 {

    private static CPUWindow cpuWindow;
    private static RAMWindow ramWindow;
    private static DiskWindow diskWindow;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Monitor de Recursos (Singleton + OSHI + Auto Refresh)");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField intervalField = new JTextField("1", 1);
        JButton applyButton = new JButton("Aplicar");

        JButton openCPU = new JButton("Abrir CPU");
        JButton openRAM = new JButton("Abrir RAM");
        JButton openDisk = new JButton("Abrir Disco");

        controlPanel.add(new JLabel("Intervalo (segundos):"));
        controlPanel.add(intervalField);
        controlPanel.add(applyButton);
        controlPanel.add(openCPU);
        controlPanel.add(openRAM);
        controlPanel.add(openDisk);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final int[] intervalSeconds = {1};

        // Background UI update thread
        Thread updaterThread = new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeLater(() -> {
                        ResourceMonitor monitor = ResourceMonitor.getInstance();
                        StringBuilder output = new StringBuilder();
                        output.append(monitor.getCPUUsage()).append("\n");
                        output.append(monitor.getMemoryUsage()).append("\n\n");
                        output.append("Uso de Disco:\n").append(monitor.getDiskUsage());
                        outputArea.setText(output.toString());

                        if (cpuWindow != null) cpuWindow.update();
                        if (ramWindow != null) ramWindow.update();
                        if (diskWindow != null) diskWindow.update();
                    });

                    Thread.sleep(intervalSeconds[0] * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        updaterThread.setDaemon(true);
        updaterThread.start();

        applyButton.addActionListener(e -> {
            try {
                int newInterval = Integer.parseInt(intervalField.getText());
                if (newInterval < 1) throw new NumberFormatException();
                intervalSeconds[0] = newInterval;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Valor inválido. Use um número maior ou igual a 1!!");
            }
        });

        openCPU.addActionListener(e -> {
            if (cpuWindow == null) cpuWindow = new CPUWindow();
            else cpuWindow.show();
        });

        openRAM.addActionListener(e -> {
            if (ramWindow == null) ramWindow = new RAMWindow();
            else ramWindow.show();
        });

        openDisk.addActionListener(e -> {
            if (diskWindow == null) diskWindow = new DiskWindow();
            else diskWindow.show();
        });
    }
}

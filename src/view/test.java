 package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class test extends JFrame {
    private JPanel imagePanel;

    public test() {
        imagePanel = new JPanel(new GridLayout(0, 6, 5, 5)); // 6 cột, hàng tự động
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(e -> uploadImage());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(uploadButton, BorderLayout.SOUTH);
        
        setTitle("Image Grid Uploader");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "jpeg"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

            // Resize image if needed
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));

            imagePanel.add(imageLabel);
            imagePanel.revalidate();
            imagePanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new test().setVisible(true));
    }
}

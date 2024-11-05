import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PikachuGame extends JFrame {
    private static final int SIZE = 7;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private int[][] values = new int[SIZE][SIZE];
    private JButton firstButton = null;
    private JButton secondButton = null;
    private LinePanel linePanel = new LinePanel();

    public PikachuGame() {
        setTitle("Pikachu Game");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        generateValues();
        initializeGameBoard();

        setVisible(true);
    }

    private void generateValues() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] = rand.nextInt(9) + 1; // Generate values from 1 to 9
            }
        }
    }

    private void initializeGameBoard() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 500));
        setContentPane(layeredPane);

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        gridPanel.setBounds(0, 0, 500, 500);
        gridPanel.setOpaque(false);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = new JButton(String.valueOf(values[i][j]));
                button.addActionListener(new ButtonClickListener(i, j));
                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }

        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        linePanel.setBounds(0, 0, 500, 500);
        linePanel.setOpaque(false);
        layeredPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = buttons[row][col];

            if (firstButton == null) {
                firstButton = clickedButton;
            } else if (secondButton == null && clickedButton != firstButton) {
                secondButton = clickedButton;
                checkAndRemove();
            }
        }

        private void checkAndRemove() {

            if (firstButton != null && secondButton != null && firstButton.getText().equals(secondButton.getText())) {
                int row1 = getButtonRow(firstButton);
                int col1 = getButtonCol(firstButton);
                int row2 = getButtonRow(secondButton);
                int col2 = getButtonCol(secondButton);

                if (isValidPath(row1, col1, row2, col2)) {
                    linePanel.drawLineBetweenButtons(firstButton, secondButton);

                    Timer timer = new Timer(500, new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            
                            linePanel.clearLine();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    
                    firstButton.setVisible(false);
                    secondButton.setVisible(false);
                           
                }
            }

            firstButton = null;
            secondButton = null;
        }

        private int getButtonRow(JButton button) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (buttons[i][j] == button) return i;
                }
            }
            return -1;
        }

        private int getButtonCol(JButton button) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (buttons[i][j] == button) return j;
                }
            }
            return -1;
        }

        private boolean isValidPath(int row1, int col1, int row2, int col2) {
            return isStraightPathClear(row1, col1, row2, col2) ||
                   isOneCornerPathClear(row1, col1, row2, col2) ||
                   isTwoCornerPathClear(row1, col1, row2, col2);
            
        }
        private boolean isStraightPathClear(int row1, int col1, int row2, int col2) {
            if (row1 == row2) { // Same row
                for (int col = Math.min(col1, col2) + 1; col < Math.max(col1, col2); col++) {
                    if (buttons[row1][col].isVisible()) return false;
                }
                return true;
            } else if (col1 == col2) { // Same column
                for (int row = Math.min(row1, row2) + 1; row < Math.max(row1, row2); row++) {
                    if (buttons[row][col1].isVisible()) return false;
                }
                return true;
            }
            return false;
        }


        private boolean isOneCornerPathClear(int row1, int col1, int row2, int col2) {
            return (isStraightPathClear(row1, col1, row1, col2) && isStraightPathClear(row1, col2, row2, col2) && !buttons[row1][col2].isVisible()) ||
                   (isStraightPathClear(row1, col1, row2, col1) && isStraightPathClear(row2, col1, row2, col2) && !buttons[row2][col1].isVisible());
        }


        private boolean isTwoCornerPathClear(int row1, int col1, int row2, int col2) {
            for (int i = 0; i < SIZE; i++) {
                if (!buttons[row1][i].isVisible() &&
                    isOneCornerPathClear(row1, col1, row1, i) &&
                    isOneCornerPathClear(row1, i, row2, col2)) return true;
                
                if (!buttons[i][col1].isVisible() &&
                    isOneCornerPathClear(row1, col1, i, col1) &&
                    isOneCornerPathClear(i, col1, row2, col2)) return true;
            }
            return false;
        }
    }

    private class LinePanel extends JPanel {
        private Point start;
        private Point end;

        public void drawLineBetweenButtons(JButton b1, JButton b2) {
            start = SwingUtilities.convertPoint(b1, b1.getWidth() / 2, b1.getHeight() / 2, this);
            end = SwingUtilities.convertPoint(b2, b2.getWidth() / 2, b2.getHeight() / 2, this);

            int midX = (start.x + end.x) / 2; 
            int midY = start.y; 

            // Vẽ lại
            repaint();
        }


        public void clearLine() {
            start = null;
            end = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (start != null && end != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(start.x, start.y, end.x, end.y);
            }
        }
    }

    public static void main(String[] args) {
        new PikachuGame();
    }
}

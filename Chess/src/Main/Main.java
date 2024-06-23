package Main;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // We create a window for the game
        JFrame window = new JFrame("Chess Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // It stops the program once the window closes
        window.setLocationRelativeTo(null); // The window opens in the center of the screen
        window.setResizable(true); // Makes the window able to resize
        window.setVisible(true); // Makes the window visible

        // We add a Panel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // The window accommodates to the panel added

        // Once the window is created and configured, we launch the game
        gamePanel.launchGame();
    }
}
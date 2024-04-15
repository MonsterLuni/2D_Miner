package game;

import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame window = new JFrame("2D_Miner");
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        new GameManager();
    }
}
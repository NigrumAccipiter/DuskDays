package main;

import entity.Player;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //configuración de pantalla
    int originalTileSize = 16; //16x16
    int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48

    public final int maxScreenCol = 16; //columnas
    public final int maxScreenRow = 12; //filas

    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FPS
    int FPS = 60;
    int currentFPS = 0;
    int frameCount = 0;
    long lastCheckTime = System.currentTimeMillis();


    TileManager TileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    //definir posición default del jugador


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frameCount++;
            }

            if (System.currentTimeMillis() - lastCheckTime >= 1000) {
                currentFPS = frameCount;
                frameCount = 0;
                lastCheckTime = System.currentTimeMillis();
            }
        }
    }

    public void update() {

        player.update();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;


        int width = this.getWidth();
        int height = this.getHeight();

        double scaleX = (double) width / screenWidth;
        double scaleY = (double) height / screenHeight;
        double exactScale = Math.min(scaleX, scaleY);

        int xOffset = (int) ((width - (screenWidth * exactScale)) / 2);
        int yOffset = (int) ((height - (screenHeight * exactScale)) / 2);


        g2.translate(xOffset, yOffset);
        g2.scale(exactScale, exactScale);


        TileM.draw(g2);
        player.draw(g2);


        g2.scale(1.0 / exactScale, 1.0 / exactScale);
        g2.translate(-xOffset, -yOffset);


        g2.setColor(Color.green);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("FPS: " + currentFPS, 20, 20);

        g2.dispose();
    }
}
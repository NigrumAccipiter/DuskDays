package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY= gp.screenHeight/2 - (gp.tileSize/2);
    }

    public void setDefaultValues() {
        // Modifica los valores de inicio de tu jugador
        worldX = gp.tileSize * 15; // Columna 15 (la mitad del mapa)
        worldY = gp.tileSize * 15; // Fila 15 (hacia abajo, donde dibujaste la costa)
        speed = 7;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorDetras.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorDetras2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorDetras3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorFrontal2.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorFrontal3.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorFrontal4.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralIzq.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralIzq2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralIzq3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralDer.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralDer2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/jugadorLateralDer3.png"));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyH.upPressed==true || keyH.downPressed== true || keyH.leftPressed== true || keyH.rightPressed== true) {

            if (keyH.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                worldX += speed;
            }

            //la imagen del jugador cambia cada 10 fotogramas
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }


    }

    public void draw(Graphics2D g2) {



        BufferedImage image = null;

        switch (direction) {


            case "up":
            image = up1;

            if (spriteNum==1) {
                image = up1;
            }
            if (spriteNum==2) {
            image = up2;
            }
                if (spriteNum==3) {
                    image = up3;
                }
            break;

            case "down":
            image = down1;
                if (spriteNum==1) {
                image = down1;
                }
                if (spriteNum==2) {
                image = down2;
                }
                if (spriteNum==3) {
                image = down3;
                }
            break;


            case "left":
            image = left1;

            if (spriteNum==1) {
            image = left1;
            }
                if (spriteNum==2) {
                    image = left2;
                }
                if (spriteNum==3) {
                    image = left3;
                }
                break;
            case "right":
            image = right1;
                if (spriteNum==1) {
                    image = right1;
                }
                    if (spriteNum==2) {
                        image = right2;
                    }
                if (spriteNum==3) {
                    image = right3;
                }
            break;

        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    
}

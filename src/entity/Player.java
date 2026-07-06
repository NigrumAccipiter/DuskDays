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

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        this.x = 100;
        this.y = 100;
        speed = 4;
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
                y -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
    
}

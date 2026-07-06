package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {
    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pisoCeniza.png"));


        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int col1 = 0;
        int row = 0;

        int x= 0;
        int y= 0;

        while (col1< gp.maxScreenCol && row< gp.maxScreenRow){

            g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
            col1++;
            x += gp.tileSize;

            if(col1 == gp.maxScreenCol){
                col1 = 0;
                x = 0;
                row++;

                y += gp.tileSize;


            }

        }




    }
}

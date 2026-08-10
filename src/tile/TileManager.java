package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Aumentamos el tamaño a 1000 para soportar un tileset grande sin dar error (IndexOutOfBounds)
        tile = new Tile[1000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            // 1. Ruta de tu imagen Tileset completa
            BufferedImage tileSet = ImageIO.read(getClass().getResourceAsStream("/tiles/WorldMap01/mapTile01 (2).png"));

            // 2. Tamaño en píxeles de cada cuadrito dentro de la imagen original (Ej: 16, 32)
            int originalTileSize = 16;
            int columnas = tileSet.getWidth() / originalTileSize;
            int filas = tileSet.getHeight() / originalTileSize;

            // 3. Empezamos el ID en 0 para que el primer gráfico asuma ese valor del CSV
            int id = 0;

            for(int y = 0; y < filas; y++) {
                for(int x = 0; x < columnas; x++) {
                    tile[id] = new Tile();
                    // Cortamos la imagen automáticamente
                    tile[id].image = tileSet.getSubimage(
                            x * originalTileSize,
                            y * originalTileSize,
                            originalTileSize,
                            originalTileSize
                    );
                    id++;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            // Ruta de tu mapa exportado desde Tiled en formato CSV
            InputStream is = getClass().getResourceAsStream("/maps/map01.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            // Recorremos el archivo fila por fila
            while (row < gp.maxWorldRow) {
                String line = br.readLine();

                // Si llegamos al final del archivo prematuramente, salimos
                if (line == null) break;

                // Dividimos los valores de esta línea
                String[] numbers = line.split(",");

                // Usamos un FOR independiente que siempre arranca la columna en 0
                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    String val = numbers[col].trim();

                    if (!val.isEmpty()) {
                        mapTileNum[col][row] = Integer.parseInt(val);
                    }
                }

                // Forzamos el salto a la siguiente fila del mapa sin importar qué pase en las columnas
                row++;
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Verificamos si el tile está dentro de la cámara para dibujarlo
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                // Validamos que el tile sea mayor O IGUAL a 0 (>= 0)
                if (tileNum >= 0 && tileNum < tile.length) {

                    // Pintamos en pantalla si la imagen existe
                    if (tile[tileNum] != null && tile[tileNum].image != null) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                }
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
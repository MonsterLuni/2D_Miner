import java.awt.*;
import java.util.ArrayList;

public class Map {
    public UI ui;
    public Map(UI ui){
        this.ui = ui;
        ui.blocks = new ArrayList<>((ui.screenWidth / 25)*(ui.screenHeight / 25));
    }
    public int[] mapHeightPerColumn = {300,300,300,300,250,250,250,200,200,200,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300};
    public void loadMap() {
        for(int i = 0; i < ui.screenWidth / 25; i++) {
            for (int l = 0; l < ui.screenHeight / 25; l++) {
                if (l * 25 >= mapHeightPerColumn[i]) {
                    ui.blocks.add(new Block(25,25,i,l));
                }
            }
        }
    }
    public void drawMap(Graphics g){
        for (Block block : ui.blocks) {
            g.setColor(Color.blue);
            block.drawBlock(g);
        }
    }
}
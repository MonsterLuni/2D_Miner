package game;

import listener.KeyListener;

import java.awt.*;

public class Player {
    public int X;
    public int Y;
    public int offsetX = 0;
    public int offsetY = 0;
    public int speed = 5;
    public int defaultHeight = 50;
    public int height = defaultHeight;
    KeyListener kh;
    UI ui;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        X = ui.screenWidth/2;
        Y = 250 - defaultHeight;
    }
    public void drawPlayer(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(X,Y,20,height);
    }
    public void gravity(){
        int index = ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25));
        if (index == -10 || Y + offsetY + 5 < ui.blocks.get(index).Y - (height + Y) ) {
            offsetY += 5;
        }
        else {
            offsetY = ui.blocks.get(ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25), (((Y + offsetY) / 25) * 25))).Y - (height + Y);
        }
    }
    public void jump(){
        if(kh.spacePressed){
            offsetY -= 10;
        }
    }
    public void walk(){
        if(kh.aPressed){
            int index = ui.map.getBlockFromPlayerX((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + 25);
            if(index == -10 || X + offsetX + speed > ui.blocks.get(index).X + 25){
                offsetX += speed;
            }
            else {
                System.out.println(index);
                offsetX = ui.blocks.get(index).X + 25 + offsetX;
            }
        } else if (kh.dPressed) {
            offsetX -= speed;
        }
    }
}

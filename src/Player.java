public class Player {
    public int X;
    public int Y;
    public int offset = 0;
    public int speed = 5;
    public int defaultHeight = 50;
    public int height = defaultHeight;
    KeyListener kh;
    UI ui;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        X = ui.screenWidth/2;
        Y = 300 - defaultHeight;
    }
    public void gravity(){
        if(Y + 5 <  ui.map.mapHeightPerColumn[Math.round((X - (float) offset) /25)] - height){
            Y += 5;
        }
        else{
            Y = ui.map.mapHeightPerColumn[Math.round((X - (float) offset) /25)] - height;
        }
    }
    public void jump(){
        if(kh.spacePressed){
            Y -= 10;
        }
    }
    public void walk(){
        if(kh.aPressed){
            offset += speed;
        } else if (kh.dPressed) {
            offset -= speed;
        }
    }
}

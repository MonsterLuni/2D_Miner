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
        Y = 300 - defaultHeight;
    }
    public void gravity(){
        if(Y + offsetY + 5 <  ui.map.mapHeightPerColumn[Math.round((X - (float) offsetX) /25)] - (height)){
            offsetY += 5;
        }
        else{
            offsetY = ui.map.mapHeightPerColumn[Math.round((X - (float) offsetX) /25)] - (height + Y);
        }
    }
    public void jump(){
        if(kh.spacePressed){
            offsetY -= 10;
        }
    }
    public void walk(){
        if(kh.aPressed){
            offsetX += speed;
        } else if (kh.dPressed) {
            offsetX -= speed;
        }
    }
}

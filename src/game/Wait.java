package game;

public class Wait {
    int counterFrames = 0;
    public boolean waitForFrames(int frames){
        if(counterFrames >= frames){
            counterFrames = 0;
            return true;
        }
        counterFrames++;
        return false;
    }
}

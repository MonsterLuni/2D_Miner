package game;

public class Wait {
    int counterFrames = 0;
    int counterSeconds = 0;
    public boolean waitForFrames(int frames){
        if(counterFrames >= frames){
            counterFrames = 0;
            return true;
        }
        counterFrames++;
        return false;
    }
    public boolean waitforSeconds(int seconds){
        int sec = seconds*60;
        if(counterSeconds > sec){
            counterSeconds = 0;
            return true;
        }
        counterSeconds++;
        return false;
    }
}

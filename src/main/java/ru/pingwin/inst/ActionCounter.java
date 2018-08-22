package ru.pingwin.inst;

public class ActionCounter {
    private int numberOfLikes = 0;
    public void Liked(){
        numberOfLikes++;
    }

    public int getNumberOfLikes(){
        return numberOfLikes;
    }
}

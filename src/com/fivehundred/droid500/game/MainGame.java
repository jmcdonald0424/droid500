package com.fivehundred.droid500.game;

import java.util.ArrayList;
import java.util.List;

public class MainGame {

    private List<Player> players = new ArrayList<>();
    
    public MainGame(){
        players.add(new Player());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}

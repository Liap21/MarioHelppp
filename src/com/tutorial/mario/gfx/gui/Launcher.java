package com.tutorial.mario.gfx.gui;

import com.tutorial.mario.Game;

import java.awt.*;



public class Launcher {

    public Button[] buttons;

    public Launcher() {
        buttons = new Button[2];

        buttons[0] = new Button(100, 100 ,100 ,100, "Start Game");
        buttons[1] = new Button(200, 200, 100, 100, "Exit Game");
    }


    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0 , Game.getFrameWidth(), Game.getFrameHeight());

        for(int i=0;i<buttons.length;i++) {
            buttons[i].render(g);

        }
    }
}

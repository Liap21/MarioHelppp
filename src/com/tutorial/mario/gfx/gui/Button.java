package com.tutorial.mario.gfx.gui;

import com.tutorial.mario.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static com.tutorial.mario.Game.*;

public class Button {
    public int x, y;
    public int width, height;

    public String label;


    public Button(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Century Gothic", Font.BOLD, 40));
        g.drawRect(getX(), getY(), getWidth(),getHeight());

        FontMetrics fm = g.getFontMetrics();
        int stringX = (getWidth() - fm.stringWidth(getLabel())) / 2;
        int stringY = (fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(getLabel(), getX() + stringX, getY() + stringY);
    }
    public void triggerEvent() {
        if(getLabel().toLowerCase().contains("start")) {
            Game.playing = true;
            Game.lives = 2;
            Game.gameOver = false;
            if(level == 6)Game.ScoobyDooTheme.play();
            else if(level == 7)Game.ScoobyDooTheme.play();
            else if(level == 8)Game.SouthParkTheme.play();
            else if(level == 9)Game.SouthParkTheme.play();
            else if(level == 10)Game.OPTheme.play();
            else if(level == 11)Game.OPTheme.play();
            else if(level == 12)Game.MlTheme.play();
            else if(level == 13)Game.MlTheme.play();
        }
        else if(getLabel().toLowerCase().contains("exit")) System.exit(0);
        else if(getLabel().toLowerCase().contains("previous")) {
            Game.level--;
            handler.clearLevel();
            handler.createLevel(levels[level]);
            Game.playing = true;
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

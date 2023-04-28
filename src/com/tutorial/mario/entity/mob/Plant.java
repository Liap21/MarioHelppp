package com.tutorial.mario.entity.mob;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;

import java.awt.*;

public class Plant extends Entity {
    private int wait;
    private int pixelTravelled;

    private boolean moving;
    private boolean insidePipe;

    public Plant(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        moving = false;
        insidePipe = true;
    }

    public void render(Graphics g) {
        g.drawImage(Game.phirana.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    public void tick() {
        y+=velY;

        if(!moving) wait++;

        if(wait>=180) {
            if(insidePipe) insidePipe = false;
            else insidePipe = true;

            moving = true;
            wait = 0;
        }

        if(moving) {
            if(insidePipe) setVelY(-3);
            else setVelY(3);

            pixelTravelled+=velY;

            if(pixelTravelled>=getHeight()||pixelTravelled<=-getHeight()) {
                pixelTravelled = 0;
                moving = false;

                setVelY(0);
            }
        }
    }
}

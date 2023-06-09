package com.tutorial.mario.entity.powerup;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.gfx.Sprite;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;
public class Mushroom extends Entity {
    private Random random = new Random();



    public Mushroom(int x, int y, int width, int height, Id id, Handler handler, Sprite powerUp, int type) {
        super(x, y, width, height, id, handler);
        this.type = type;


        int dir = random.nextInt(2);

        switch (dir) {
            case 0: setVelX(-1);
            break;
            case 1: setVelX(1);
            break;
        }
    }


    public void render(Graphics g) {
        switch(getType()) {
            case 0:
                g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
                break;
            case 1:
                g.drawImage(Game.lifeMushroom.getBufferedImage(), x, y, width, height, null);
                break;
        }
    }


    public void tick() {
        x+=velX;
        y+=velY;
        for(int i=0;i<handler.tile.size();i++) {
            Tile t = handler.tile.get(i);
            if(t.isSolid()) {
                    if (getBoundsBottom().intersects(t.getBounds())) {
                        setVelY(0);
                        if(falling) falling = false;

                    } else if(!falling) {
                            gravity = 0.8;
                            falling = true;
                        }

                    if (getBoundsLeft().intersects(t.getBounds())) {
                        setVelX(1);
                    }
                    if (getBoundsRight().intersects(t.getBounds())) {
                        setVelX(-1);
                    }
            }

        }

        if(falling) {
            gravity+=0.1;
            setVelY((int) gravity);
        }
    }
}

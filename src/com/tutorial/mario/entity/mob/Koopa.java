package com.tutorial.mario.entity.mob;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.states.KoopaStates;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Koopa extends Entity {

    private Random random;

    private int shellCount;

    public int frame = 0;
    public int frameDelay = 0;

    private boolean animate = false;

    public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        random = new Random();

        int dir = random.nextInt(2);

        switch (dir) {
            case 0: setVelX(-2);
                facing = 0;
                break;
            case 1: setVelX(2);
                facing = 1;
                break;
        }
        koopaState = KoopaStates.WALKING;
    }


    public void render(Graphics g) {

            if(koopaState==KoopaStates.WALKING) {
                g.drawImage(Game.koopa[frame+1].getBufferedImage(), x, y, width, height, null);
            } else {
                g.drawImage(Game.koopaShell.getBufferedImage(), x, y, width, height, null);
            }

    }

    public void tick() {
        x+=velX;
        y+=velY;

        if(koopaState==KoopaStates.SHELL) {
            setVelX(0);

            shellCount++;

            if(shellCount>=300) {
                shellCount = 0;
                koopaState = KoopaStates.WALKING;
            }
            if(koopaState==KoopaStates.WALKING||koopaState==KoopaStates.SPINNING){
                shellCount = 0;

                if(velX==0) {
                    int dir = random.nextInt(2);

                    switch (dir) {
                        case 0: setVelX(-2);
                            facing = 0;
                            break;
                        case 1: setVelX(2);
                            facing = 1;
                            break;
                    }
                }
            }
        }

        if (velX!=0) animate = true;
        else animate = false;
        for(int i=0;i<handler.tile.size();i++) {
            Tile t = handler.tile.get(i);
            if(t.isSolid()) {
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    if(jumping) {
                        jumping = false;
                        gravity = 0.0;
                        falling = true;

                    }
                }
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    if(falling) falling = false;

                } else if(!falling&&!jumping) {
                    gravity = 0.8;
                    falling = true;
                }
                if (getBoundsLeft().intersects(t.getBounds())) {
                   if(koopaState==KoopaStates.SPINNING) setVelX(10);
                   else setVelX(2);
                    x = t.getX()+t.width;
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    if(koopaState==KoopaStates.SPINNING) setVelX(-10);
                    else setVelX(-2);
                    x = t.getX()-t.width;
                }
            }
        }

        if(falling) {
            gravity+=0.1;
            setVelY((int) gravity);
        }
        if(animate) {
            frameDelay++;
            if (frameDelay>=3) {
                frame++;
                if(frame>=5) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}
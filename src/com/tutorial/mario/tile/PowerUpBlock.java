package com.tutorial.mario.tile;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.powerup.Flower;
import com.tutorial.mario.entity.powerup.Mushroom;
import com.tutorial.mario.gfx.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PowerUpBlock extends Tile {

    private Sprite powerUp;

    private boolean poppedUp = false;

    private int SpriteY = getY();
    private int type;
    private Random random;


    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler,Sprite powerUp,int type) {
        super(x, y, width, height, solid, id, handler);
        this.type = type;
        this.powerUp = powerUp;
    }

    public void render(Graphics g) {
        if(!poppedUp) g.drawImage(powerUp.getBufferedImage(), x, SpriteY, width, height, null);
        if(!activated) g.drawImage(Game.powerUp.getBufferedImage(), x, y, width, height, null);
        else g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        if(activated&&!poppedUp) {
            SpriteY--;
            if(SpriteY<=y-height) {
                if(powerUp==Game.mushroom||powerUp==Game.lifeMushroom) handler.addEntity(new Mushroom(x, SpriteY, width, height, Id.mushroom, handler,powerUp,type));
                else if(powerUp==Game.flower) handler.addEntity(new Flower(x, SpriteY, width, height, Id.flower, handler));
                poppedUp = true;
            }
        }
    }
}

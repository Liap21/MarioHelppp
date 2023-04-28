package com.tutorial.mario.entity.powerup;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.gfx.Sprite;

import java.awt.*;

public class Flower extends Entity {

    public Flower(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    public void render(Graphics g) {
        g.drawImage(Game.flower.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    public void tick() {

    }
}

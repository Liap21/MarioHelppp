package com.tutorial.mario.tile;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.mob.Plant;

import java.awt.*;

public class Pipe extends Tile {
    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) {
        super(x, y, width, height, solid, id, handler);
        this.facing = facing;

        if (plant) handler.addEntity(new Plant(getX(), getY()-64, getWidth(), 64, Id.plant, handler));

    }

    public void render(Graphics g) {
        g.drawImage(Game.pipe.getBufferedImage(), x, y ,width, height, null);
    }

    public void tick() {

    }
}

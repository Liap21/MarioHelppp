package com.tutorial.mario;

import com.tutorial.mario.entity.Coin;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.mob.Goomba;
import com.tutorial.mario.entity.mob.Koopa;
import com.tutorial.mario.entity.mob.Player;
import com.tutorial.mario.entity.mob.TowerBoss;
import com.tutorial.mario.entity.powerup.Mushroom;
import com.tutorial.mario.tile.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public void render(Graphics g) {
        for(int i=0;i<entity.size();i++) {
            Entity e = entity.get(i);
            e.render(g);
        }
        for(int i=0;i<tile.size();i++) {
            Tile t = tile.get(i);
            t.render(g);
        }
    }
    public void tick() {
        for(int i=0; i<entity.size(); i++) {
            Entity e = entity.get(i);
            e.tick();
        }
        for (int i=0;i<tile.size();i++) {
            Tile t = tile.get(i);
            t.tick();

        }
    }
    public void addEntity(Entity e) {
        entity.add(e);
    }
    public void removeEntity(Entity e) {
        entity.remove(e);

    }
    public void addTile(Tile t) {
        tile.add(t);
    }
    public void removeTile(Tile t) {
        tile.remove(t);
    }

    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                int pixel = level.getRGB(x,y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red==0&&green==0&&blue==0) addTile(new Wall(x*64, y*64,64,64,true, Id.wall,this));
                if(red==0&&green==0&&blue==255) addEntity(new Player(x*64, y*64 ,48 ,48, Id.player, this));
                if(red==0&&green==255&&blue==0) addEntity(new Koopa( x*64, y*64, 64, 64, Id.koopa, this));
                if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*64, y*64, 64,64,true,Id.powerUp,this,Game.mushroom, 0));
                if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*64,y*64,64,64*15,true,Id.pipe,this,128-green));
                if(red==255&&green==250&&blue==0) addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
                if(red==255&&green==0&&blue==255) addEntity(new TowerBoss(x*64,y*64,64,64,Id.towerBoss,this, 3));

            }
        }
    }
    public void clearLevel() {
        entity.clear();
        tile.clear();
    }
}

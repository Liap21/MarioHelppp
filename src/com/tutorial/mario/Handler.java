package com.tutorial.mario;

import com.tutorial.mario.entity.Coin;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.mob.*;
import com.tutorial.mario.entity.powerup.Mushroom;
import com.tutorial.mario.entity.powerup.PowerStar;
import com.tutorial.mario.tile.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();
    public Random random;

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
        random = new Random();


        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                int pixel = level.getRGB(x,y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red==0&&green==0&&blue==0) addTile(new Wall(x*64, y*64,64,64,true, Id.wall,this));
                if(red==0&&green==0&&blue==255) addEntity(new Player(x*64, y*64 ,48 ,48, Id.player, this));
                if(red==255&&green==0&&blue==0) addEntity(new Goomba( x*64, y*64, 64, 64, Id.goomba, this));
                if(red==255&&green==255&&blue==0) addEntity(new PowerStar(x*64,y*64,64,64,Id.star,this));
                if(red==0&&green==255&&blue==255) addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.mushroom, 0));
                if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*64,y*64,64,64*15,true,Id.pipe,this,128-green, false));
                if(red==255&&green==250&&blue==0) addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
                if(red==255&&green==0&&blue==255) addEntity(new TowerBoss(x*64,y*64,64,64,Id.towerBoss,this, 3));
                if(red==0&&green==255&&blue==0) addTile(new Flag(x*64,y*64,64,64*5,true,Id.flag,this));
                if(red==75&&green==0&&blue==130) addTile(new Ending(x*64,y*64,64,64*5,true,Id.ending,this));
                if(red==128&&green==0&&blue==0) addEntity(new Minion(x*64,y*64,64,64,Id.minion,this));
                if(red==128&&green==128&&blue==128) addEntity(new Ghost(x*64,y*64,64,64,Id.ghost,this));
                if(red==255&&green==165&&blue==0) addEntity(new Pirate(x*64,y*64,64,64,Id.pirate,this));
                if(red==128&&green==0&&blue==128) addEntity(new Marsh(x*64,y*64,64,64,Id.marsh,this));
                if(red==0&&green==128&&blue==128) addEntity(new Alien(x*64,y*64,64,64,Id.alien,this));

            }
        }
    }
    public void clearLevel() {
        entity.clear();
        tile.clear();
    }
}

package com.tutorial.mario.input;

import com.tutorial.mario.Game;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Game.handler.entity) {
            if(en.getId()==Id.player) {
                if(en.goingDownPipe) return;
                switch (key) {
                    case KeyEvent.VK_W:
                        for(int q=0;q<Game.handler.tile.size();q++){
                            Tile t = Game.handler.tile.get(q);
                            if(t.getId()==Id.pipe) {
                                if(en.getBoundsTop().intersects(t.getBounds())) {
                                    if(!en.goingDownPipe) en.goingDownPipe = true;
                                }
                            }
                        }
                        for(int q=0;q<Game.handler.tile.size();q++){
                            Tile t = Game.handler.tile.get(q);
                            if(t.isSolid()) {
                                if(en.getBoundsBottom().intersects(t.getBounds())) {
                                    if(!en.jumping) {
                                        en.jumping = true;
                                        en.gravity = 8.0;
                                }
                            }
                        }

                        }
                        break;
                    case KeyEvent.VK_S:
                        for(int q=0;q<Game.handler.tile.size();q++){
                            Tile t = Game.handler.tile.get(q);
                            if(t.getId()==Id.pipe) {
                                if(en.getBoundsBottom().intersects(t.getBounds())) {
                                    if(!en.goingDownPipe) en.goingDownPipe = true;
                                }
                            }
                        }
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(-5);
                        en.facing = 0;
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        en.facing = 1;
                        break;
                }
            }

        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Game.handler.entity) {
            if(en.getId()==Id.player) {
                switch (key) {
                    case KeyEvent.VK_W:
                        en.setVelY(0);
                        break;
                    case KeyEvent.VK_S:
                        en.setVelY(0);
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        break;
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        //not using
    }
}

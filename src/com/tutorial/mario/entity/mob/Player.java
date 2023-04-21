package com.tutorial.mario.entity.mob;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.states.BossState;
import com.tutorial.mario.states.KoopaStates;
import com.tutorial.mario.states.PlayerState;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {

    private PlayerState state;

    private int pixelsTravelled = 0;

    private int frame = 0;
    private int frameDelay = 0;

    private boolean animate = false;
    private Random random;

    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        state = PlayerState.SMALL;
        random = new Random();
    }

    public void render(Graphics g) {
        if(facing == 0) {
            g.drawImage(Game.player[frame+5].getBufferedImage(), x, y, width, height, null);
        } else if(facing == 1) {
            g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
        }
    }

    public void tick() {
        x+=velX;
        y+=velY;


        if (velX!=0) animate = true;
        else animate = false;
        for(int i=0;i<handler.tile.size();i++) {
            Tile t = handler.tile.get(i);
            if(t.isSolid()&&!goingDownPipe) {
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    if(jumping&&!goingDownPipe) {
                        jumping = false;
                        gravity = 0.0;
                        falling = true;

                    }
                    if(t.getId()==Id.powerUp) {
                        if(getBoundsTop().intersects(t.getBounds())) {
                            t.activated = true;
                        }
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
                    setVelX(0);
                    x = t.getX()+t.width;
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX()-t.width;
                }
            }
        }
        for(int i=0; i<handler.entity.size();i++) {
            Entity e = handler.entity.get(i);

            if(e.getId()==Id.mushroom) {
                switch(e.getType()) {
                    case 0:
                        if(getBounds().intersects(e.getBounds())) {
                            int tpX = getX();
                            int tpY = getY();
                            width+=(width/3);
                            height+=(height/3);
                            setX(tpX-width);
                            setY(tpY-height);
                            if(state==PlayerState.SMALL) state = PlayerState.BIG;
                            e.die();
                        }
                        break;
                    case 1:
                        if(getBounds().intersects(e.getBounds())) {
                            Game.lives++;
                            e.die();
                        }
                }
            } else if(e.getId()==Id.goomba||e.getId()==Id.towerBoss) {
                if(getBoundsBottom().intersects(e.getBoundsTop())) {
                    if(e.getId()!=Id.towerBoss) e.die();
                    else if(e.attackable) {
                        e.hp--;
                        e.falling = true;
                        e.gravity = 0.8;
                        e.bossState = BossState.RECOVERING;
                        e.attackable = false;
                        e.phaseTime = 0;

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    }
                } else if(getBounds().intersects(e.getBounds())) {
                    if(state==PlayerState.BIG){
                        state = PlayerState.SMALL;
                        width/=3;
                        height/=3;
                        x+=width;
                        y+=height;
                    }
                    else if(state==PlayerState.SMALL){
                        die();
                    }
                }
            } else if(e.getId()==Id.coin) {
                if(getBounds().intersects(e.getBounds())&&e.getId()==Id.coin) {
                    Game.coins++;
                    e.die();
                }
            } else if(e.getId()==Id.koopa) {
                if(e.koopaState==KoopaStates.WALKING) {

                    if(getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaStates.SHELL;

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    }   else if(getBounds().intersects(e.getBounds())) die();

                } else if(e.koopaState==KoopaStates.SHELL) {
                    if(getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaStates.SPINNING;

                        int dir = random.nextInt(2);

                        switch (dir) {
                            case 0:
                                e.setVelX(-10);
                                facing = 0;
                                break;
                            case 1:
                                e.setVelX(10);
                                facing = 1;
                                break;
                        }

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    }
                    if(getBoundsLeft().intersects(e.getBoundsRight())) {
                        e.setVelX(-10);
                        e.koopaState = KoopaStates.SPINNING;
                    }
                    if(getBoundsRight().intersects(e.getBoundsLeft())) {
                        e.setVelX(-10);
                        e.koopaState = KoopaStates.SPINNING;
                    }

                } else if(e.koopaState==KoopaStates.SPINNING) {
                    if(getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaStates.SHELL;

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    }   else if(getBounds().intersects(e.getBounds())) die();
                }
            }
        }

        if(jumping&&!goingDownPipe) {
            gravity -= 0.17;
            setVelY((int)-gravity);
            if(gravity<=0.5) {
                jumping = false;
                falling = true;
            }

        }
        if(falling&&!goingDownPipe) {
            gravity+=0.17;
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
        if(goingDownPipe) {
            for(int i=0;i<Game.handler.tile.size();i++) {
                Tile t = Game.handler.tile.get(i);
                if(t.getId()==id.pipe) {
                    if(getBounds().intersects(t.getBounds())) {
                        switch(t.facing) {
                            case 0:
                                setVelY(-5);
                                setVelX(0);
                                pixelsTravelled+=-velY;
                                break;
                            case 2:
                                setVelY(5);
                                setVelX(0);
                                pixelsTravelled+=velY;
                                break;
                        }

                    }
                    if(pixelsTravelled>t.height) {
                        goingDownPipe = false;
                        pixelsTravelled = 0;
                    }
                }
            }
        }
    }
}

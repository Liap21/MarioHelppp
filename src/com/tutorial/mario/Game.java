package com.tutorial.mario;



import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.gfx.Sprite;
import com.tutorial.mario.gfx.SpriteSheet;
import com.tutorial.mario.gfx.gui.Launcher;
import com.tutorial.mario.input.KeyInput;
import com.tutorial.mario.input.MouseInput;
import com.tutorial.mario.states.KoopaStates;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final int SCALE = 4;
    public static final String TITLE = "Mario";

    private Thread thread;
    private boolean running = false;
    public static BufferedImage[] levels;
    public static int level = 0;

    private static BufferedImage introbg1;
    private static BufferedImage introbg2;
    private static BufferedImage introbg3;
    private static BufferedImage introbg4;
    private static BufferedImage introbg5;
    private static BufferedImage introbg6;
    private static BufferedImage outrobg;


    private static BufferedImage background;
    private static BufferedImage background1;
    private static BufferedImage background2;
    private static BufferedImage background3;
    private static BufferedImage background4;
    private static BufferedImage background5;

    public static int coins = 0;
    public static int score = 0;
    public static int lives = 2;
    public static int deathScreenTime = 0;
    public static boolean gameOver = false;
    public static boolean playing = false;

    public static boolean showDeathScreen = true;

    public static Handler handler;
    public static SpriteSheet sheet;
    public SpriteSheet sheet2;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite pipe;
    public static Sprite coin;
    public static Sprite star;
    public static Sprite fireball;
    public static Sprite flower;
    public static Sprite phirana;
    
    public static Sprite[] player;
    public static Sprite[] firePlayer;
    public static Sprite mushroom;
    public static Sprite lifeMushroom;
    public static Sprite[] goomba;
    public static Sprite[] koopa;
    public static Sprite[] alien;
    public static Sprite[] minion;
    public static Sprite[] marsh;
    public static Sprite[] pirate;
    public static Sprite[] ghost;
    public static Sprite[] particle;
    public static Sprite koopaShell;
    public KoopaStates koopaState;
    public static Sprite bossIdle;
    public static Sprite bossRecoverting;
    public static Sprite bossRunningleft;
    public static Sprite bossRunningright;

    public static Sprite[] flag;
    public static Sprite[] ending;

    public static Sound jump;
    public static Sound goombastomp;
    public static Sound levelcomplete;
    public static Sound losealife;
    public static Sound themesong;
    public static Sound powerup;
    public static Sound getcoin;
    public static Sound firethrow;
    public static Sound mobhit;
    public static Sound ScoobyDooTheme;
    public static Sound SouthParkTheme;
    public static Sound OPTheme;
    public static Sound MlTheme;
    public static Sound Ben10Theme;


    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/testSpriteTransparent.png");
        sheet2 = new SpriteSheet("/testSprite2Transparent.png");
        cam = new Camera();
        launcher = new Launcher();
        mouse = new MouseInput();



        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        grass = new Sprite(sheet, 13, 1);
        powerUp = new Sprite(sheet, 16, 1);
        usedPowerUp = new Sprite(sheet, 13, 1);
        pipe = new Sprite(sheet, 14, 1);
        coin = new Sprite(sheet, 15, 1);
        star = new Sprite(sheet, 13, 2);

        mushroom = new Sprite(sheet, 16, 2);
        lifeMushroom = new Sprite(sheet, 15, 2);
        phirana = new Sprite(sheet, 8, 2);
        goomba = new Sprite[9];
        minion = new Sprite[4];
        ghost = new Sprite[4];
        alien = new Sprite[2];
        player = new Sprite[12];
        pirate = new Sprite[4];
        marsh = new Sprite[9];
        koopa = new Sprite[9];
        koopaShell = new Sprite(sheet, 5, 3);
        flag = new Sprite[1];
        ending = new Sprite[1];
        particle = new Sprite[5];
        firePlayer = new Sprite[8];
        flower = new Sprite(sheet, 14, 2);
        fireball = new Sprite(sheet, 15 ,1);

        bossIdle = new Sprite(sheet, 7, 3);
        bossRunningleft = new Sprite(sheet,2, 2 );
        bossRunningright    = new Sprite(sheet, 5, 2);
        bossRecoverting = new Sprite(sheet, 3, 3);


        levels = new BufferedImage[17];


        for(int i = 0;i<player.length;i++) {
            player[i] = new Sprite(sheet, i+1, 1) ;
        }

        for(int i = 0;i<goomba.length;i++) {
            goomba[i] = new Sprite(sheet, i+7, 2) ;
          }

        for(int i = 0;i<minion.length;i++) {
            minion[i] = new Sprite(sheet, i+1, 3) ;
        }

        for (int i = 0; i < koopa.length; i++) {
            koopa[i] = new Sprite(sheet, i+7, 2);
            }
        for(int i=0;i<flag.length;i++) {
            flag[i] = new Sprite(sheet2, i+16, 3);
        }
        for(int i=0;i<ending.length;i++) {
            ending[i] = new Sprite(sheet2, i+16, 3);
        }
        for(int i=0;i<particle.length;i++) {
            particle[i] = new Sprite(sheet, i+1, 3);
        }
        for(int i = 0;i<firePlayer.length;i++) {
            firePlayer[i] = new Sprite(sheet, i+8, 1) ;
        }
        for(int i = 0;i<koopa.length;i++) {
            koopa[i] = new Sprite(sheet, i+4, 3) ;
        }
        for(int i = 0;i<ghost.length;i++) {
            ghost[i] = new Sprite(sheet, i+5, 4) ;
        }
        for(int i = 0;i<pirate.length;i++) {
            pirate[i] = new Sprite(sheet, i+1, 4) ;
        }
        for(int i = 0;i<marsh.length;i++) {
            marsh[i] = new Sprite(sheet2, i+2, 4) ;
        }
        for(int i = 0;i<alien.length;i++) {
            alien[i] = new Sprite(sheet, i+9, 4) ;
        }




        try {

            levels[0] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg1 = ImageIO.read(getClass().getResource("/intro1.png"));
            levels[1] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg2 = ImageIO.read(getClass().getResource("/intro2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg3 = ImageIO.read(getClass().getResource("/intro3.png"));
            levels[3] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg4 = ImageIO.read(getClass().getResource("/intro4.png"));
            levels[4] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg5 = ImageIO.read(getClass().getResource("/intro5.png"));
            levels[5] = ImageIO.read(getClass().getResource("/introlevel.png"));
            introbg6 = ImageIO.read(getClass().getResource("/intro6.png"));

            levels[6] = ImageIO.read(getClass().getResource("/Scoobs1.png"));
            levels[7] = ImageIO.read(getClass().getResource("/Scoobs2.png"));
            background = ImageIO.read(getClass().getResource("/scoobydoobackground.png"));
            levels[8] = ImageIO.read(getClass().getResource("/SouthPark3.png"));
            levels[9] = ImageIO.read(getClass().getResource("/Southpark4.png"));
            background2 = ImageIO.read(getClass().getResource("/MLbackground.png"));
            levels[10] = ImageIO.read(getClass().getResource("/OP5.png"));
            levels[11] = ImageIO.read(getClass().getResource("/OP6.png"));
            background3 = ImageIO.read(getClass().getResource("/OPbackground.jpg"));
            levels[12] = ImageIO.read(getClass().getResource("/ML7.png"));
            levels[13] = ImageIO.read(getClass().getResource("/ML8.png"));
            background1 = ImageIO.read(getClass().getResource("/Southparkbackground.png"));
            levels[14] = ImageIO.read(getClass().getResource("/Ending.png"));
            outrobg = ImageIO.read(getClass().getResource("/Outro.png"));

           // background4 = ImageIO.read(getClass().getResource(""));

        } catch (IOException e) {
            e.printStackTrace();
        }

        jump = new Sound("/jump.wav");
        goombastomp = new Sound("/StompingGoomba.wav");
        levelcomplete = new Sound("/level complete.wav");
        losealife = new Sound("/death-sound.wav");
        themesong = new Sound("/retro-funk-theme-music.wav");
        powerup = new Sound("/power-up.wav");
        getcoin = new Sound("/getcoin.wav");
        firethrow = new Sound("/StompingGoomba.wav");
        mobhit = new Sound("/mob-hit.wav");
        ScoobyDooTheme = new Sound("/ScoobyDooTheme.wav");
        SouthParkTheme = new Sound("/SouthParkTheme.wav");
        OPTheme = new Sound("/OPTheme.wav");
        MlTheme = new Sound("/MLTheme.wav");
        Ben10Theme = new Sound("/Ben10Theme.wav");


    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
    }

    private synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        int frames = 0;
        int ticks = 0;
        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " frames per seconds " + ticks + " updates per seconds");
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }


    public void render() {
        if(level <= 5) {
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(4);
                return;
            }
            Graphics g = bs.getDrawGraphics();

            if (playing) g.translate(cam.getX(), cam.getY());
            if (!showDeathScreen && playing) handler.render(g);

            if (!showDeathScreen) {
                if (level == 0) {
                    g.drawImage(introbg1, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 1) {
                    g.drawImage(introbg2, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 2) {
                    g.drawImage(introbg3, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 3) {
                    g.drawImage(introbg4, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 4) {
                    g.drawImage(introbg5, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 5) {
                    g.drawImage(introbg6, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 6) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 7) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 8) {
                    g.drawImage(background2, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 9) {
                    g.drawImage(background2, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 10) {
                    g.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 11) {
                    g.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 12) {
                    g.drawImage(background1, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 13) {
                    g.drawImage(background1, 0, 0, getWidth(), getHeight(), null);
                } else if (level == 14) {
                    g.drawImage(outrobg, 0, 0, getWidth(), getHeight(), null);
                }





            }
            if (showDeathScreen) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                if (!gameOver) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Courier", Font.BOLD, 20));
                    g.drawImage(player[4].getBufferedImage(), 500, 300, 100, 100, null);
                    g.drawString("x" + lives, 610, 370);
                } else if (gameOver) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Courier", Font.BOLD, 20));
                    g.drawString("You Died ;-;", 300, 370);
                    deathScreenTime++;
                    if (deathScreenTime >= 300) launcher.render(g);
                    Game.ScoobyDooTheme.stop();
                    Game.SouthParkTheme.stop();
                    Game.OPTheme.stop();
                    Game.MlTheme.stop();
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("x" + coins, 100, 95);
            g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("Score: " + score, 100, 150);

            if (!playing) launcher.render(g);
            g.dispose();
            bs.show();
        }
        else {
            BufferStrategy bs = getBufferStrategy();
            if(bs == null) {
                createBufferStrategy(4);
                return;
            }
            Graphics g = bs.getDrawGraphics();

            if(!showDeathScreen) {
                if(level == 0) {
                    g.drawImage(introbg1, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 1) {
                    g.drawImage(introbg2, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 2) {
                    g.drawImage(introbg3, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 3) {
                    g.drawImage(introbg4, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 4) {
                    g.drawImage(introbg5, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 5) {
                    g.drawImage(introbg6, 0, 0, getWidth(), getHeight(), null);
                }

                else if (level == 6) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 7) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 8) {
                    g.drawImage(background2, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 9) {
                    g.drawImage(background2, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 10) {
                    g.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 11) {
                    g.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 12) {
                    g.drawImage(background1, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 13) {
                    g.drawImage(background1, 0, 0, getWidth(), getHeight(), null);
                }
                else if(level == 14) {
                    g.drawImage(outrobg, 0, 0, getWidth(), getHeight(), null);
                }



            }
            if(showDeathScreen) {
                g.setColor(Color.BLACK);
                g.fillRect(0,0,getWidth(),getHeight());

                if(!gameOver) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Courier", Font.BOLD, 20));
                    g.drawImage(player[4].getBufferedImage(), 500,  300, 100, 100, null);
                    g.drawString("x" + lives, 610, 370);
                } else if(gameOver) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Courier", Font.BOLD, 20));
                    g.drawString("You Died ;-;", 300, 370);
                    deathScreenTime++;
                    if(deathScreenTime >= 300) launcher.render(g);
                    Game.ScoobyDooTheme.stop();
                    Game.SouthParkTheme.stop();
                    Game.OPTheme.stop();
                    Game.MlTheme.stop();
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("x" + coins, 100, 95);
            g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("Score: " + score, 100, 150);

            if(playing) g.translate(cam.getX(), cam.getY());
            if(!showDeathScreen&&playing) handler.render(g);
            if(!playing) launcher.render(g);
            g.dispose();
            bs.show();
        }
    }



    private boolean isActiveLevel(BufferedImage level) {
        return true;
    }

    public void tick() {
            if(playing) {
                handler.tick();
            }

        for(int i=0;i<handler.entity.size();i++) {
            Entity e = handler.entity.get(i);
            if(e.getId()==Id.player) {
              if(!e.goingDownPipe) cam.tick(e);
            }
        }
        if(showDeathScreen&&!gameOver&&playing) deathScreenTime++;
        if(deathScreenTime>=180) {
            if(!gameOver) {
                showDeathScreen = false;
                deathScreenTime = 0;
                handler.clearLevel();
                handler.createLevel(levels[level]);


            } else if(gameOver) {
                showDeathScreen = false;
                deathScreenTime = 0;
                playing = false;
                lives = 2;
                System.out.println(lives);
                coins = 0;
                score = 0;
                handler.clearLevel();
                handler.createLevel(levels[level]);
            }

        }
    }

    public static int getFrameWidth() {
        return WIDTH*SCALE;
    }

    public static int getFrameHeight() {
        return HEIGHT*SCALE;
    }

    public static void switchLevel() {
        Game.level++;
        System.out.println(level);

        handler.clearLevel();
        handler.createLevel(levels[level]);

        if(level == 6)Game.ScoobyDooTheme.play();
        else if(level == 7)Game.ScoobyDooTheme.play();
        else if(level == 8)Game.SouthParkTheme.play();
        else if(level == 9)Game.SouthParkTheme.play();
        else if(level == 10)Game.OPTheme.play();
        else if(level == 11)Game.OPTheme.play();
        else if(level == 12)Game.MlTheme.play();
        else if(level == 13)Game.MlTheme.play();



        if(level == 8) Game.ScoobyDooTheme.stop();
        else if(level == 10) Game.SouthParkTheme.stop();
        else if(level == 12) Game.OPTheme.stop();
        else if(level == 14) Game.MlTheme.stop();
        Game.levelcomplete.play();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }



}
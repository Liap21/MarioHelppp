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
    private static BufferedImage[] levels;
    private static int level = 0;

    private static BufferedImage background;
    private static BufferedImage background1;
    private static BufferedImage background2;

    public static int coins = 0;
    public static int score = 0;
    public static int lives = 4;
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
    public static Sprite[] bossIdle;

    public static Sprite[] flag;

    public static Sound jump;
    public static Sound goombastomp;
    public static Sound levelcomplete;
    public static Sound losealife;
    public static Sound themesong;
    public static Sound powerup;
    public static Sound getcoin;
    public static Sound firethrow;
    public static Sound mobhit;


    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/Sprite.jpg");
        sheet2 = new SpriteSheet("/Sprite2.jpg");
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
        flag = new  Sprite[1];
        particle = new Sprite[5];
        firePlayer = new Sprite[8];
        flower = new Sprite(sheet, 14, 2);
        fireball = new Sprite(sheet, 15 ,1);

        bossIdle = new Sprite[4];

        levels = new BufferedImage[3];


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

        for(int i = 0;i<bossIdle.length;i++) {
            bossIdle[i] = new Sprite(sheet, i+5, 3) ;
        }



        try {

            levels[0] = ImageIO.read(getClass().getResource("/level.png"));
            levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/bosslevel.png"));
            background = ImageIO.read(getClass().getResource("/scoobydoobackground.png"));


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


    }

    private synchronized void start() {
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
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
                return;
        }
        Graphics g = bs.getDrawGraphics();

       if(!showDeathScreen) {
           g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

           g.setColor(Color.WHITE);
           g.setFont(new Font("Courier", Font.BOLD, 20));
           g.drawString("x" + coins, 100, 95);
           g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
           g.setFont(new Font("Courier", Font.BOLD, 20));
           g.drawString("Score: " + score, 100, 150);

       }
        if(showDeathScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());

            if(!gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                g.drawImage(player[4].getBufferedImage(), 500,  300, 100, 100, null);
                g.drawString("x" + lives, 610, 370);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                g.drawString("GAMEOVER ;-;", 300, 370);
            }
            }

       if(playing) g.translate(cam.getX(), cam.getY());
        if(!showDeathScreen&&playing) handler.  render(g);
        else if(!playing) launcher.render(g);
        g.dispose();
        bs.show();
    }

    private boolean isActiveLevel(BufferedImage level) {
        return true;
    }

    public void tick() {
        if(playing) handler.tick();

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

               // themesong.play();
            } else if(gameOver) {
                showDeathScreen = false;
                deathScreenTime = 0;
                playing = false;
                gameOver = false;
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

        handler.clearLevel();
        handler.createLevel(levels[level]);

        //Game.themesong.close();
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
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
    private BufferedImage image;

    public static int coins = 0;
    public static int lives = 4;
    public static int deathScreenTime = 0;
    public static boolean gameOver = false;
    public static boolean playing = false;

    public static boolean showDeathScreen = true;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite pipe;
    public static Sprite coin;
    
    public static Sprite[] player;
    public static Sprite mushroom;
    public static Sprite lifeMushroom;
    public static Sprite[] goomba;
    public static Sprite[] koopa;
    public static Sprite koopaShell;
    public KoopaStates koopaState;


    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/Sprite.jpg");
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

        mushroom = new Sprite(sheet, 16, 2);
        lifeMushroom = new Sprite(sheet, 15, 2);
        goomba = new Sprite[9];
        player = new Sprite[12];
        koopa = new Sprite[12];
        koopaShell = new Sprite(sheet, 16, 2);

        for(int i = 0;i<player.length;i++) {
            player[i] = new Sprite(sheet, i+1, 1) ;
        }

        for(int i = 0;i<goomba.length;i++) {
            goomba[i] = new Sprite(sheet, i+1, 2) ;
        }
            for (int i = 0; i < koopa.length; i++) {
                koopa[i] = new Sprite(sheet, i + 1, 2);
            }


        try {
            image = ImageIO.read(getClass().getResource("/level.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



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

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
       if(!showDeathScreen) {
           g.setColor(Color.WHITE);
           g.setFont(new Font("Courier", Font.BOLD, 20));
           g.drawString("x" + coins, 100, 95);
           g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
       }
        if(showDeathScreen) {
            if(!gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                g.drawImage(player[4].getBufferedImage(), 500, 300, 100, 100, null);
                g.drawString("x" + lives, 610, 370);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                g.drawString("GAMEOVER ;-;", 300, 370);
            }
            }

       if(playing) g.translate(cam.getX(), cam.getY());
        if(!showDeathScreen&&playing) handler.render(g);
        else if(!playing) launcher.render(g);
        g.dispose();
        bs.show();
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
                handler.createLevel(image);
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
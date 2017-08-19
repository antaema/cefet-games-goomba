package br.cefetmg.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    public boolean walking, up, down, left, right;  
    public Sprite bot;
    public AnimatedSprite moving;
    private Texture mapLevelsTextures;
    
    private float larguraTela, alturaTela, playerStep, playerWidth, playerHeight, count, timebase; 
    private int way = 5;
    
    private long mSeconds;
    private float interval;
    private Texture botTexture;
    private Timer timer;
    private Random r ;
    
    

    class BotTask extends TimerTask {
        @Override
        public void run() {
            updateMoviment();
        }
    }

    public Bot(int x, int y, long mSeconds){
        mapLevelsTextures = new Texture("map-level-1.png");
        r = new Random();
        moving = new AnimatedSprite("goomba-spritesheet.png",21,24);
        botTexture = new Texture("goomba.png");
        bot = new Sprite(botTexture);
        
        larguraTela = mapLevelsTextures.getWidth(); 
        alturaTela = mapLevelsTextures.getHeight();
        playerStep = (float)r.nextInt(21)/10 + 0.5f;
        playerWidth = botTexture.getWidth();
        playerHeight = botTexture.getHeight();
        timebase = 0;
 
        
        moving.createMoviment(AnimatedSprite.direction.NORTH, AnimatedSprite.sense.HORIZONTAL, 5, 0.1f, 2, 0);
        moving.createMoviment(AnimatedSprite.direction.SOUTH, AnimatedSprite.sense.HORIZONTAL, 5, 0.1f, 0, 0);
        moving.createMoviment(AnimatedSprite.direction.EAST, AnimatedSprite.sense.HORIZONTAL, 5, 0.1f, 1, 0);
        moving.createMoviment(AnimatedSprite.direction.WEST, AnimatedSprite.sense.HORIZONTAL, 5, 0.1f, 3, 0);
        
        bot.setPosition(x, y);
        this.mSeconds = mSeconds;
        
        timer = new Timer();
        TimerTask task = new BotTask();
        timer.schedule(task, 0, mSeconds);
        
        walking = true;
        interval = (float)r.nextInt(21)/10;
        count = 0;
    }
    
    public void updateMoviment(){
        float x = bot.getX();
        float y = bot.getY();
        
        up= false;
        down= false; 
        left= false;
        right= false;
        
        if(way == 5){
            way = r.nextInt(4);
        }
        else if(count - timebase > 2){
            timebase = count;
            if(r.nextInt(10) == 9){
                walking = false;
            }
            else{
                walking = true;
                way = r.nextInt(4);
            }
        }
        if(walking){
            switch(way){
                case 0:
                    if(x - playerStep > 0){
                        x-=1;
                    }
                    else{
                        if(r.nextInt(10) == 9){
                            walking = false;
                        }
                        else{
                            walking = true;
                            way = r.nextInt(4);
                        }
                    }
                    left = true;
                    break;
                case 1:
                    if(x + playerStep < larguraTela - playerWidth){
                        x+=1;
                    }
                    else{
                        if(r.nextInt(10) == 9){
                            walking = false;
                        }
                        else{
                            walking = true;
                            way = r.nextInt(4);
                        }
                    }
                    right = true;
                    break;
                case 2:
                    if(y + playerStep < alturaTela - playerHeight){  
                        y+=1;
                    }
                    else{
                        if(r.nextInt(10) == 9){
                            walking = false;
                        }
                        else{
                            walking = true;
                            way = r.nextInt(4);
                        }
                    }
                    up = true;
                    break;
                case 3:
                    if(y - playerStep > 0){
                        y-=1;
                    }
                    else{
                        if(r.nextInt(10) == 9){
                            walking = false;
                        }
                        else{
                            walking = true;
                            way = r.nextInt(4);
                        }
                    }
                    down = true;
                    break;
            }
        }
        count += (float)mSeconds/1000;
        bot.setPosition(x, y);
        
    }
}

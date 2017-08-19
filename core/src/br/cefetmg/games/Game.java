package br.cefetmg.games;

import br.cefetmg.games.AnimatedSprite.direction;
import br.cefetmg.games.AnimatedSprite.sense;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A classe principal de uma aplicação LibGDX deve herdar de ApplicationAdapter,
 * ou então de Game (específico para o caso quando há várias telas diferentes
 * no jogo - splash, menu, jogo etc.).
 * 
 * Um ApplicationAdapter possui alguns métodos que normalmente nós 
 * sobrecarregamos:
 * - create(): é invocado quando o jogo é criado.
 * - dispose(): é invocado quando o jogo é finalizado.
 * - render(): é invocado muitas vezes por segundo.
 * 
 * @author fegemo
 */

public class Game extends ApplicationAdapter {
    private boolean walking, up, down, left, right;  
    private float larguraTela; 
    private float alturaTela;
    private float playerStep;
    private float playerWidth;
    private float playerHeight;
    private SpriteBatch batch;
    public Texture[] mapLevelsTextures;
    private AnimatedSprite moving;
    private Texture playerTexture;
    private Sprite player;
    private Bot b,b2,b3,b4;
    /**
     * No método create colocamos código de inicialização do jogo. Por exemplo,
     * carregamos texturas, sons e outros recursos. Aqui também instanciamos
     * e posicionamos os elementos que compõem a cena do jogo, como personagens,
     * inimigos, o mapa etc.
     */
    @Override
    public void create() {
        mapLevelsTextures = new Texture[2];
        mapLevelsTextures[0] = new Texture("map-level-1.png");
        mapLevelsTextures[1] = new Texture("map-level-2.png");
        batch = new SpriteBatch();
        moving = new AnimatedSprite("goomba-spritesheet.png",21,24);
        playerTexture = new Texture("goomba.png");
        player = new Sprite(playerTexture);
        b = new Bot(80, 30, 16);
        b2 = new Bot(200, 70, 16);
        b3 = new Bot(20, 150, 16);
        b4 = new Bot(180, 130, 16);
        
        larguraTela = mapLevelsTextures[0].getWidth(); 
        alturaTela = mapLevelsTextures[0].getHeight();
        playerStep = 1;
        playerWidth = playerTexture.getWidth();
        playerHeight = playerTexture.getHeight();
        
        
        
        
        
        moving.createMoviment(direction.NORTH, sense.HORIZONTAL, 5, 0.1f, 2, 0);
        moving.createMoviment(direction.SOUTH, sense.HORIZONTAL, 5, 0.1f, 0, 0);
        moving.createMoviment(direction.EAST, sense.HORIZONTAL, 5, 0.1f, 1, 0);
        moving.createMoviment(direction.WEST, sense.HORIZONTAL, 5, 0.1f, 3, 0);
        
        
        player.setPosition(30, 10);
        
        Music music_background = Gdx.audio.newMusic(Gdx.files.internal("Super Mario Bros. Theme Song.mp3"));
        music_background.setLooping(true);
        music_background.play();
        
        // cor de fundo da tela: branco
        Gdx.gl.glClearColor(1, 1, 1, 1);        
    }

    /**
     * No método dispose nós desfazemos dos recursos que estávamos usando. Por
     * exemplo, texturas, sons e outras coisas. É interessante implementar
     * o dispose próximo ao create porque praticamente tudo o que está no create
     * precisa estar no dispose.
     */
    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * No método render desenhamos tudo o que faz parte da cena do jogo. É aqui
     * que, inicialmente, limpamos a tela e então mandamos desenhar a cena. Este
     * método é chamado muitas vezes por segundo.
     */
    @Override
    public void render() {
        // apaga a tela, para desenharmos de novo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.draw(algumaTextura, x, y);
        //batch.end();
        // chamando o método update(), que atualiza a lógica do jogo.
        // passa para o método quanto tempo se passou desde a última vez
        // que renderizamos
        update(Gdx.graphics.getDeltaTime());
        

        batch.begin();        
            // desenhos são realizados aqui
            batch.draw(mapLevelsTextures[0], 0, 0);
            batch.draw(mapLevelsTextures[1], 0, 0);
            if(walking){
                if(up)
                    batch.draw((TextureRegion)moving.moviments[direction.NORTH.ordinal()].getKeyFrame(moving.timeAnimation), player.getX(), player.getY());
                else if(down)
                    batch.draw((TextureRegion)moving.moviments[direction.SOUTH.ordinal()].getKeyFrame(moving.timeAnimation), player.getX(), player.getY());
                else if(left)
                    batch.draw((TextureRegion)moving.moviments[direction.WEST.ordinal()].getKeyFrame(moving.timeAnimation), player.getX(), player.getY());
                else if(right)
                    batch.draw((TextureRegion)moving.moviments[direction.EAST.ordinal()].getKeyFrame(moving.timeAnimation), player.getX(), player.getY());
            
            }
            else{
                player.draw(batch);
            }
            
            if(b.walking){
                if(b.up)
                    batch.draw((TextureRegion)b.moving.moviments[direction.NORTH.ordinal()].getKeyFrame(b.moving.timeAnimation), b.bot.getX(), b.bot.getY());
                else if(b.down)
                    batch.draw((TextureRegion)b.moving.moviments[direction.SOUTH.ordinal()].getKeyFrame(b.moving.timeAnimation), b.bot.getX(), b.bot.getY());
                else if(b.left)
                    batch.draw((TextureRegion)b.moving.moviments[direction.WEST.ordinal()].getKeyFrame(b.moving.timeAnimation), b.bot.getX(), b.bot.getY());
                else if(b.right)
                    batch.draw((TextureRegion)b.moving.moviments[direction.EAST.ordinal()].getKeyFrame(b.moving.timeAnimation), b.bot.getX(), b.bot.getY());
            
            }
            else{
                b.bot.draw(batch);
            }
            
            if(b2.walking){
                if(b2.up)
                    batch.draw((TextureRegion)b2.moving.moviments[direction.NORTH.ordinal()].getKeyFrame(b2.moving.timeAnimation), b2.bot.getX(), b2.bot.getY());
                else if(b2.down)
                    batch.draw((TextureRegion)b2.moving.moviments[direction.SOUTH.ordinal()].getKeyFrame(b2.moving.timeAnimation), b2.bot.getX(), b2.bot.getY());
                else if(b2.left)
                    batch.draw((TextureRegion)b2.moving.moviments[direction.WEST.ordinal()].getKeyFrame(b2.moving.timeAnimation), b2.bot.getX(), b2.bot.getY());
                else if(b2.right)
                    batch.draw((TextureRegion)b2.moving.moviments[direction.EAST.ordinal()].getKeyFrame(b2.moving.timeAnimation), b2.bot.getX(), b2.bot.getY());
            
            }
            else{
                b2.bot.draw(batch);
            }
            
            if(b3.walking){
                if(b3.up)
                    batch.draw((TextureRegion)b3.moving.moviments[direction.NORTH.ordinal()].getKeyFrame(b3.moving.timeAnimation), b3.bot.getX(), b3.bot.getY());
                else if(b3.down)
                    batch.draw((TextureRegion)b3.moving.moviments[direction.SOUTH.ordinal()].getKeyFrame(b3.moving.timeAnimation), b3.bot.getX(), b3.bot.getY());
                else if(b3.left)
                    batch.draw((TextureRegion)b3.moving.moviments[direction.WEST.ordinal()].getKeyFrame(b3.moving.timeAnimation), b3.bot.getX(), b3.bot.getY());
                else if(b3.right)
                    batch.draw((TextureRegion)b3.moving.moviments[direction.EAST.ordinal()].getKeyFrame(b3.moving.timeAnimation), b3.bot.getX(), b3.bot.getY());
            
            }
            else{
                b3.bot.draw(batch);
            }
            
            if(b4.walking){
                if(b4.up)
                    batch.draw((TextureRegion)b4.moving.moviments[direction.NORTH.ordinal()].getKeyFrame(b4.moving.timeAnimation), b4.bot.getX(), b4.bot.getY());
                else if(b4.down)
                    batch.draw((TextureRegion)b4.moving.moviments[direction.SOUTH.ordinal()].getKeyFrame(b4.moving.timeAnimation), b4.bot.getX(), b4.bot.getY());
                else if(b4.left)
                    batch.draw((TextureRegion)b4.moving.moviments[direction.WEST.ordinal()].getKeyFrame(b4.moving.timeAnimation), b4.bot.getX(), b4.bot.getY());
                else if(b4.right)
                    batch.draw((TextureRegion)b4.moving.moviments[direction.EAST.ordinal()].getKeyFrame(b4.moving.timeAnimation), b4.bot.getX(), b4.bot.getY());
            
            }
            else{
                b4.bot.draw(batch);
            }
        batch.end();
    }
           
    /**
     *
     * @param delta
     */
    public void update(float delta) {
        float x = player.getX(), y = player.getY();
        
        walking  = false;
        up= false;
        down= false; 
        left= false;
        right= false;
        
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            if(x - playerStep > 0){
                x-=1;
            }    
            walking = true;
            left = true;
        }
        else if (Gdx.input.isKeyPressed(Keys.RIGHT ) || Gdx.input.isKeyPressed(Keys.D)) {
            if(x + playerStep < larguraTela - playerWidth){
                x+=1;
            }
            walking = true;
            right = true;
        }
        else if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            if(y + playerStep < alturaTela - playerHeight){  
                y+=1;
            }
            walking = true;
            up = true;
        }
        else if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            if(y - playerStep > 0){
                y-=1;
            }
            walking = true;
            down = true;
        }
        else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        player.setPosition(x, y);
        b.moving.timeAnimation +=  Gdx.graphics.getDeltaTime();
        b2.moving.timeAnimation +=  Gdx.graphics.getDeltaTime();
        b3.moving.timeAnimation +=  Gdx.graphics.getDeltaTime();
        b4.moving.timeAnimation +=  Gdx.graphics.getDeltaTime();
        
        moving.timeAnimation +=  Gdx.graphics.getDeltaTime();
    }
}


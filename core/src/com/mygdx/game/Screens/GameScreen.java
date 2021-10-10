package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ForUnbeliver;
import com.mygdx.game.entities.Asteroid;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.EnemyBullet;
import com.mygdx.game.entities.EnemyShip;
import com.mygdx.game.entities.Explosion;
import com.mygdx.game.tools.Collision_Of_Objects;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
        Music music;
    public static final float SPEED = 300;

    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;

    public static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
    public static final float SHOOT_WAIT_TIME = 0.19f;

    public static final float MIN_ASTEROID_SPAWN_TIME = 0.05f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.1f;
    public static final float MIN_ENEMY_SPAWN_TIME = 0.5f;
    public static final float MAX_ENEMY_SPAWN_TIME = 1f;
        diffic dif;
    Animation[] rolls;

    float x;
    float y;
    int roll;
    float rollTimer;
    float stateTime;
    float shootTimer;
    float enemy_shoot_time;
    float asteroidSpawnTimer;
    float enemySpawnTimer;
 boolean isPlaying;
    Random random;

    ForUnbeliver game;

    ArrayList<EnemyBullet> enemybullets;
    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;
    ArrayList<EnemyShip> enemy;
    ArrayList<Explosion> explosions;

    Texture blank;
    Texture controls;

    BitmapFont scoreFont;

    Collision_Of_Objects playerRect;

    float health = 1;// 0 - конец игры. 1 - полные hp
    int mul, mul_dmg,velocity;
   int score;

    boolean showControls = true;

    public GameScreen(ForUnbeliver game, int score, int damage , int velocity) {

        dif = new diffic(game);
        mul = score; mul_dmg = damage; this.velocity = velocity;
        this.game = game;
        y = 15;
        x = ForUnbeliver.WIDTH / 2 - SHIP_WIDTH / 2;
        bullets = new ArrayList<Bullet>();
        enemybullets = new ArrayList<EnemyBullet>();
        asteroids = new ArrayList<Asteroid>();
        enemy = new ArrayList<EnemyShip>();
        explosions = new ArrayList<Explosion>();
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        playerRect = new Collision_Of_Objects(0, 0, SHIP_WIDTH, SHIP_HEIGHT);

        blank = new Texture("blank.png");
        if (ForUnbeliver.IS_MOBILE)
            controls = new Texture("controls.png");



        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
        enemySpawnTimer = random.nextFloat() * (MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME) + MIN_ENEMY_SPAWN_TIME;
        shootTimer = 0;
        enemy_shoot_time = 0;

        roll = 2;
        rollTimer = 0;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);//влево
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);//переход
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);//дижение прямло
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);//переход
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);//вправо

        game.scrollingBackground.setSpeedFixed(false);
        isPlaying = false;
        music = Gdx.audio.newMusic(Gdx.files.internal("fonts/mmmm.mp3"));

    }

    @Override
    public void show () {

    }

    @Override
    public void render (float delta) {
        if(!isPlaying )
        music.play();
        music.setVolume(0.25f);
         if (!music.isPlaying())
         {
             isPlaying = false;
         }

        //код выстрела
        shootTimer += delta;
        if ((isRight() || isLeft()) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;


            //прекратить показыать картинки после первого нажатия
            showControls = false;

            int offset = 4;
            if (roll == 1 || roll == 3)//Slightly tilted
                offset = 8;

            if (roll == 0 || roll == 4)//Fully tilted
                offset = 16;

            bullets.add(new Bullet(x + offset));
            bullets.add(new Bullet(x + SHIP_WIDTH - offset));
        }



        for(EnemyShip enemys : enemy )
        {
               if( (int) enemys.WaitShoot % 30 == 0)
               {
                   enemybullets.add(new EnemyBullet (enemys.getX(), enemys.getY()));
                   enemybullets.add(new EnemyBullet(enemys.getX()+16, enemys.getY()));
                   System.out.println("add");
               }
        }



        //спавн астероидов
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0 ) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt(ForUnbeliver.WIDTH - Asteroid.WIDTH)));
        }

        enemySpawnTimer -= delta;
        if(enemySpawnTimer <= 0)
        {
          enemySpawnTimer = random.nextFloat()*(MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME) + MIN_ENEMY_SPAWN_TIME;
          enemy.add(new EnemyShip(random.nextInt(ForUnbeliver.WIDTH - EnemyShip.WIDTH)));
        }


        //обнова астероидов
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove)
                asteroidsToRemove.add(asteroid);
        }


        ArrayList<EnemyShip> enemyToRemove = new ArrayList<EnemyShip>();
        for( EnemyShip enemysh:enemy)
        {

            enemysh.update(delta);
            if (enemysh.remove)
                enemyToRemove.add(enemysh);

        }


        //обнова лазера
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove)
                bulletsToRemove.add(bullet);
        }

        ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<EnemyBullet>();
        for(EnemyBullet enembul : enemybullets)
        {       enembul.update(delta);
            if(enembul.remove)
            {
                enemyBulletsToRemove.add(enembul);
            }
        }

        //обнова взрывов
        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for (Explosion explosion : explosions) {
            explosion.update(delta);
            if (explosion.remove)
                explosionsToRemove.add(explosion);
        }
        explosions.removeAll(explosionsToRemove);

        //движение
        if (isLeft()) {//влево
            x -= SPEED * Gdx.graphics.getDeltaTime();

            if (x < 0)
                x = 0;

            //Update roll if button just clicked
            if (isJustLeft() && !isRight() && roll > 0) {
                rollTimer = 0;
                roll--;
            }

            //Update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll--;
            }
        } else {
            if (roll < 2) {
                //Update roll to make it go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll++;
                }
            }
        }

        if (isRight()) {//вправо
            x += SPEED * Gdx.graphics.getDeltaTime();

            if (x + SHIP_WIDTH > ForUnbeliver.WIDTH)
                x = ForUnbeliver.WIDTH - SHIP_WIDTH;

            //Обновить анимацию, если кнопка только что нажата
            if (isJustRight() && !isLeft() && roll > 0) {
                rollTimer = 0;
                roll--;
            }

            //Update roll
            rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll++;
            }
        } else {
            if (roll > 2) {
                //Update roll
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll--;
                }
            }
        }

        //оновление координат прямоугольника
        playerRect.move(x, y);

        //коллизия
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {//Collision occured
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
                    score += 100*mul;
                }
            }
        }

        for (Bullet bullet : bullets) {
            for (EnemyShip a : enemy) {
                if (bullet.getCollisionRect().collidesWith(a.getCollisionRect())) {
                    bulletsToRemove.add(bullet);
                    enemyToRemove.add(a);
                    explosions.add(new Explosion(a.getX(), a.getY()));
                    score += 2000*mul;
                }
            }
        }

        for(EnemyBullet bullet : enemybullets)
        {
            if(bullet.getCollisionRect().collidesWith(playerRect))
            {
                enemyBulletsToRemove.add(bullet);
                explosions.add(new Explosion(bullet.x,bullet.y));
                health-= 0.005*mul_dmg;
                if (health <= 0) {
                    this.dispose(); music.stop();
                    game.setScreen(new GameOver(game, (int)score));
                    return;
                }
            }
        }

        bullets.removeAll(bulletsToRemove);
        enemybullets.removeAll(bulletsToRemove);

        for (Asteroid asteroid : asteroids) {
            if (asteroid.getCollisionRect().collidesWith(playerRect)) {
                asteroidsToRemove.add(asteroid);
                explosions.add(new Explosion(asteroid.getX(),asteroid.getY()));
                health -= 0.1*mul_dmg;

                //выход из игры
                if (health <= 0) {
                    this.dispose(); music.stop();
                    game.setScreen(new GameOver(game, (int)score));
                    return;
                }
            }
        }
        for (EnemyShip enemys : enemy) {
            if (enemys.getCollisionRect().collidesWith(playerRect)) {
                enemyToRemove.add(enemys);
                health -= 0.1*mul_dmg;

                //выход из игры
                if (health <= 0) {
                    this.dispose(); music.stop();
                    game.setScreen(new GameOver(game, (int)score));
                    return;
                }
            }
        }
        for (Asteroid asteroid : asteroids) {
           asteroid.SPEED =velocity;
        }
        for(EnemyBullet bullet: enemybullets)
        {
            bullet.SPEED =  2*velocity;
        }



        asteroids.removeAll(asteroidsToRemove);
        enemy.removeAll(enemyToRemove);

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(game.batch, scoreLayout, ForUnbeliver.WIDTH / 2 - scoreLayout.width / 2, ForUnbeliver.HEIGHT - scoreLayout.height - 10);

        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        for(EnemyBullet bullet: enemybullets)
        {
            bullet.render(game.batch);
        }

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }
        for (EnemyShip enemys : enemy)
        {
                enemys.render(game.batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }


        if (health > 0.6f)
            game.batch.setColor(Color.GREEN);
        else if (health > 0.2f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);

        game.batch.draw(blank, 0, 0, ForUnbeliver.WIDTH * health, 5);
        game.batch.setColor(Color.WHITE);

        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);


        if (showControls) {
            if (ForUnbeliver.IS_MOBILE) {
                //left
                game.batch.setColor(Color.RED);
                game.batch.draw(controls, 0, 0, ForUnbeliver.WIDTH / 2, ForUnbeliver.HEIGHT, 0, 0, ForUnbeliver.WIDTH / 2, ForUnbeliver.HEIGHT, false, false);

               //right
                game.batch.setColor(Color.BLUE);
                game.batch.draw(controls, ForUnbeliver.WIDTH / 2, 0, ForUnbeliver.WIDTH / 2, ForUnbeliver.HEIGHT, 0, 0, ForUnbeliver.WIDTH / 2, ForUnbeliver.HEIGHT, true, false);

                game.batch.setColor(Color.WHITE);
            }
        }

        game.batch.end();
    }

    private boolean isRight () {
        return Gdx.input.isKeyPressed(Keys.RIGHT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x >= ForUnbeliver.WIDTH / 2);

    }

    private boolean isLeft () {
        return Gdx.input.isKeyPressed(Keys.LEFT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x < ForUnbeliver.WIDTH / 2);
    }

    private boolean isJustRight () {
        return Gdx.input.isKeyJustPressed(Keys.RIGHT) || (Gdx.input.justTouched() && game.cam.getInputInGameWorld().x >= ForUnbeliver.WIDTH / 2);
    }

    private boolean isJustLeft () {
        return Gdx.input.isKeyJustPressed(Keys.LEFT) || (Gdx.input.justTouched() && game.cam.getInputInGameWorld().x < ForUnbeliver.WIDTH / 2);
    }

    @Override
    public void resize (int width, int height) {}

    @Override
    public void pause () {}

    @Override
    public void resume () {}

    @Override
    public void hide () {}

    @Override
    public void dispose ()
    {
        blank.dispose();
        controls.dispose();

    }

}
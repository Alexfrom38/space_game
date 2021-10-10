package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ForUnbeliver;

public class diffic implements Screen {
    ForUnbeliver game;
    int x;  public int damage = 0; public int score = 0; public int  Velocity = 0;



    private static final int HARD_BUTTO_WIDTH = 110;
    private static final int HARD_BUTTO_HEIGHT= 28;

    private static final int MEDIUM_BUTTO_WIDTH = 110;
    private static final int MEDIUM_BUTTO_HEIGHT= 28;

    private static final int EASY_BUTTO_WIDTH = 110;
    private static final int EASY_BUTTO_HEIGHT= 28;


    private static final int HARD_BUTTON_Y = 600;
    private static final int MEDIUM_BUTTON_Y = 400;
    private static final int EASY_BUTTON_Y = 200;

    private static final int STORY_BUTTON_Y  = 800;
    private static final int STORY_BUTTON_WIDTH = 336;
    private static final int STORY_BUTTON_HEIGHT = 26;


    Texture Hard;
    Texture Medium;
    Texture Easy;
    Texture Story;


    public diffic(ForUnbeliver game)
    {
        this.game = game;

        Hard = new Texture("hard.png");
        Medium = new Texture("medium.png");
        Easy = new Texture("easy.png");
        Story = new Texture("readthe story.png");



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();




            x = ForUnbeliver.WIDTH/2 - STORY_BUTTON_WIDTH/2;
            game.batch.draw(Story,x,STORY_BUTTON_Y,STORY_BUTTON_WIDTH,STORY_BUTTON_HEIGHT);
        if (Gdx.input.getX() < x + STORY_BUTTON_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT -Gdx.input.getY() < STORY_BUTTON_Y+ STORY_BUTTON_HEIGHT && ForUnbeliver.HEIGHT -Gdx.input.getY()>STORY_BUTTON_Y)
        {

            if(Gdx.input.isTouched())
            {
                System.out.println("kek");
                game.setScreen(new Story(game));
            }
        }

        x = ForUnbeliver.WIDTH / 2 - EASY_BUTTO_WIDTH / 2;
        game.batch.draw(Easy, x, EASY_BUTTON_Y, EASY_BUTTO_WIDTH, EASY_BUTTO_HEIGHT);
        if (Gdx.input.getX() < x + EASY_BUTTO_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT -Gdx.input.getY() < EASY_BUTTON_Y + EASY_BUTTO_HEIGHT && ForUnbeliver.HEIGHT -Gdx.input.getY()> EASY_BUTTON_Y)
        {

            if(Gdx.input.isTouched())
            {
                damage = 1 ; score = 1; Velocity = 250;
                game.setScreen(new GameScreen(game,score,damage,Velocity));
            }
        }
        x = ForUnbeliver.WIDTH / 2 - MEDIUM_BUTTO_WIDTH / 2;
        game.batch.draw(Medium, x, MEDIUM_BUTTON_Y, MEDIUM_BUTTO_WIDTH, MEDIUM_BUTTO_HEIGHT);
        if (Gdx.input.getX() < x + MEDIUM_BUTTO_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT -Gdx.input.getY() < MEDIUM_BUTTON_Y + MEDIUM_BUTTO_HEIGHT && ForUnbeliver.HEIGHT -Gdx.input.getY()> MEDIUM_BUTTON_Y)
        {

            if(Gdx.input.isTouched())
            { damage = 5 ; score = 3; Velocity = 450;
                game.setScreen(new GameScreen(game,score,damage,Velocity));
            }
        }

        x = ForUnbeliver.WIDTH / 2 - HARD_BUTTO_WIDTH / 2;
        game.batch.draw(Hard, x, HARD_BUTTON_Y, HARD_BUTTO_WIDTH, HARD_BUTTO_HEIGHT);
        if (Gdx.input.getX() < x + HARD_BUTTO_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT -Gdx.input.getY() < HARD_BUTTON_Y + HARD_BUTTO_HEIGHT && ForUnbeliver.HEIGHT -Gdx.input.getY()> HARD_BUTTON_Y)
        {

            if(Gdx.input.isTouched())
            {  damage = 10 ; score = 6; Velocity = 550;
                game.setScreen(new GameScreen(game,score,damage,Velocity));
            }
        }

        game.batch.end();
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       Easy.dispose();
       Medium.dispose();
       Hard.dispose();
       Story.dispose();

    }
}

package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.game.ForUnbeliver;

public class MainMenu implements Screen {
    ForUnbeliver game; diffic dif;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;

    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 430;


    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;


    public MainMenu(ForUnbeliver game)
    {
        this.game = game;

        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       game.batch.begin();



           int x = ForUnbeliver.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
           if (Gdx.input.getX()< x + EXIT_BUTTON_WIDTH && Gdx.input.getX()> x && ForUnbeliver.HEIGHT -Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && ForUnbeliver.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y)
           {
               game.batch.draw(exitButtonActive, ForUnbeliver.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
               if(Gdx.input.isTouched()) Gdx.app.exit();
           }
           else {
               game.batch.draw(exitButtonInactive, ForUnbeliver.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

           }


           x = ForUnbeliver.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
           if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT -Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && ForUnbeliver.HEIGHT -Gdx.input.getY()> PLAY_BUTTON_Y)
           {
               game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
               if(Gdx.input.isTouched())
               {
                   game.setScreen(new diffic(game));
               }
           }
           else {
               game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
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
        playButtonActive .dispose();
        playButtonInactive .dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();

    }
}

package com.mygdx.game.Screens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import com.mygdx.game.ForUnbeliver;

public class Story implements Screen {
    int x; private static final int BACK_BUTTON_WIDTH = 106; private static final int BACK_BUTTON_HEIGHT = 26;
    private static final int BACK_BUTTON_Y = ForUnbeliver.HEIGHT / 9;

    Music music;
    ForUnbeliver game;
    BitmapFont scoreFont;
    Texture texture;


    Texture gameOverBanner;

    private static final int BANNER_HEIGHT = 100;

    Story(ForUnbeliver game) {


        texture = new Texture("back.png");



        this.game = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("to_space.mp3"));

        music.setVolume(0.085f);
        music.play();
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/18.fnt"));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);


        GlyphLayout scoreLayout = new GlyphLayout(scoreFont,

                "               Тут будет написана какая-то история  \n   " +
                        "           Тут будет написана какая-то история \n  " +
                        "           Тут будет написана какая-то история \n  " +
                        "           Тут будет написана какая-то история \n  " +
                        "           Тут будет написана какая-то история \n  " +
                        " \n" +
                        " Жаль, что некоторые не верят, \n " +
                        "что школьник из 10(ну уже 11) класса может написать игру...\n" +

                        "\n" +
                        "\n"
                         , Color.WHITE, 0, Align.left, false);

        scoreFont.draw(game.batch, scoreLayout, ForUnbeliver.WIDTH / 2 - scoreLayout.width / 2, ForUnbeliver.HEIGHT - BANNER_HEIGHT - 5 * 2);
       // game.batch.draw(texture, 0.45f * Gdx.graphics.getWidth(), 0.1f * Gdx.graphics.getHeight());


        x = ForUnbeliver.WIDTH / 2 - BACK_BUTTON_WIDTH / 2;
        game.batch.draw(texture, x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        if (Gdx.input.getX() < x + BACK_BUTTON_WIDTH && Gdx.input.getX() > x && ForUnbeliver.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT && ForUnbeliver.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y) {

            if (Gdx.input.isTouched()) {
                System.out.println("kek");
                game.setScreen(new diffic(game));
                music.stop();
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
        texture.dispose();
    music.dispose();
    }
}

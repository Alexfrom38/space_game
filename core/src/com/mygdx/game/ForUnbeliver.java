package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenu;
import com.mygdx.game.tools.Camera;
import com.mygdx.game.tools.ScrollingBackground;

public class ForUnbeliver extends Game {

	public int getWidth ;
    public int getHeight ;

	public static int WIDTH ;
	public static int HEIGHT ;



	public static boolean IS_MOBILE = false;

	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public Camera cam;

	@Override
	public void create () {
		getWidth =   Gdx.graphics.getWidth();

		getHeight = Gdx.graphics.getHeight();

		WIDTH = getWidth;
		HEIGHT = getHeight;
		batch = new SpriteBatch();
		cam = new Camera(WIDTH, HEIGHT);

		if (Gdx.app.getType() == Application.ApplicationType.Android )
			IS_MOBILE = true;
		IS_MOBILE = true;

		this.scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		cam.update(width, height);
		super.resize(width, height);
	}

}
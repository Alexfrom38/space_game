package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ForUnbeliver;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ForUnbeliver.WIDTH;
		config.height = ForUnbeliver.HEIGHT;
		config.resizable = false;

		new LwjglApplication(new ForUnbeliver(), config);
	}
}
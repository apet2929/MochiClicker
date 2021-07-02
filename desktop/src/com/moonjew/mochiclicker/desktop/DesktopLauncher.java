package com.moonjew.mochiclicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonjew.mochiclicker.MochiClicker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		MochiClicker.HEIGHT = config.height;
		MochiClicker.WIDTH = config.height * 4 / 3;
		new LwjglApplication(new MochiClicker(), config);
	}
}

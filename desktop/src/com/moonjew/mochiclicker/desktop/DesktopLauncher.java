package com.moonjew.mochiclicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonjew.mochiclicker.MochiClicker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MochiClicker.WIDTH;
		config.height = MochiClicker.HEIGHT;
		new LwjglApplication(new MochiClicker(), config);
	}
}

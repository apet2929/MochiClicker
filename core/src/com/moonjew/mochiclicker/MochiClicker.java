package com.moonjew.mochiclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.MainMenuState;
import com.moonjew.mochiclicker.state.TutorialState;

public class MochiClicker extends ApplicationAdapter {
	public static int WIDTH = 640;
	public static int HEIGHT = 480;
	SpriteBatch batch;
	GameStateManager gsm;
	ShapeRenderer sr;
	public static Font FONT;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		WIDTH = width;
		HEIGHT = height;
		System.out.println(width + " " + height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		sr = new ShapeRenderer();
		gsm.push(new MainMenuState(gsm));
		FONT = new Font();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, sr);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		FONT.dispose();
	}

}
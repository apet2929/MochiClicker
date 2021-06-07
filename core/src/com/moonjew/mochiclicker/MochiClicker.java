package com.moonjew.mochiclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.MainMenuState;

public class MochiClicker extends ApplicationAdapter {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	SpriteBatch batch;
	GameStateManager gsm;
	ShapeRenderer sr;
	public static Font FONT;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		sr = new ShapeRenderer();
		gsm.push(new MainMenuState(gsm, sr));
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

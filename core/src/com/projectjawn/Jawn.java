/*
 * Carnage Games
 * December 18, 2015
 * Jawn.java
 *
 * Main game controller
 */

package com.projectjawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projectjawn.states.GameStateManager;
import com.projectjawn.states.MenuState;

/**
 * A main game class controller.
 */
public class Jawn extends ApplicationAdapter {
	public static final int WIDTH = 720;
	public static final int HEIGHT = 1280;

	private GameStateManager gsm;
	private SpriteBatch sb;

	/**
	 * Initialize the MenuState and create a sprite batch for rendering.
	 */
	@Override
	public void create() {

		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
		sb = new SpriteBatch();
		Gdx.gl.glClearColor(0, 0, 1, 1);
	}

	/**
	 * Clear the screen to blue, update gsm components and render gsm components.
	 */
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}

	/**
	 * Dispose all game assets.
	 */
	@Override
	public void dispose() {
		super.dispose();
		gsm.dispose();
		System.out.println("Jawn disposed");
	}


} // End of public class Jawn extends Application Adapter

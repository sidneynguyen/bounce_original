/*
 * Carnage Games
 * December 18, 2015
 * State.java
 *
 * Game state
 */

package com.projectjawn.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * An abstract class representing a game State.
 */
public abstract class State {

    protected GameStateManager gsm;
    protected OrthographicCamera cam;

    /**
     * Constructs a game State with a camera.
     * @param gsm GameStateManager to update game States
     */
    public State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();

    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

} // End of public class State

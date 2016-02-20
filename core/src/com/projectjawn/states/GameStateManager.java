/*
 * Carnage Games
 * December 18, 2015
 * GameStateManager.java
 *
 * Manages game states
 */

package com.projectjawn.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * A class managing all game States and updating and rendering the current game State.
 */
public class GameStateManager {

    private Stack<State> states;

    /**
     * Constructs a Stack of game States.
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Pushes on a game State.
     * @param state game State
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Pops off a game State.
     */
    public void pop() {
        states.pop().dispose();
    }

    /**
     * Sets peek game State.
     * @param state game State
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Updates peek game state.
     * @param dt seconds per frames
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * Renders peek game state.
     * @param sb SpriteBatch for rendering
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    /**
     * Dispose assets of all States.
     */
    public void dispose() {
        for (State s: states) {
            s.dispose();
            System.out.println("GameStateManager disposed");
        }
    }

} // End of public class GameStateManager

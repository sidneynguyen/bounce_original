/*
 * Carnage Games
 * December 18, 2015
 * Ball.java
 *
 * Ball sprite
 */

package com.projectjawn.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A class representing the Ball sprite.
 */
public class Ball {

    private Texture texture;
    private float diameter;
    private Rectangle bound;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 gravity;

    private float screenEdge;


    /**
     * Constructs a Ball sprite.
     * @param x position
     * @param y position
     */
    public Ball(float x, float y, float diameter, float gravity, float acceleration, float screenEdge) {
        this.texture = new Texture("ball.png");
        this.diameter = diameter;
        this.bound = new Rectangle(x, y, diameter, diameter);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.gravity = new Vector2(0, gravity);
        this.acceleration = new Vector2(0, acceleration);
        this.screenEdge = screenEdge;
    }

    /**
     * Accelerates the Ball when the screen is touched.
     */
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            velocity.add(acceleration);
        }
    }

    /**
     * Moves the Ball and subjects it to gravity.
     * @param dt seconds per frame
     */
    public void update(float dt) {
        velocity.add(gravity);
        velocity.scl(dt);
        position.add(velocity);
        velocity.scl(1/dt);
        bound.setPosition(position.x, position.y);
        handleInput();
    }

    /**
     * Changes direction of Ball, decrease its size, and slow its speed.
     * @param y position
     */
    public void bounce(float y, float dDiameter, float dVelocity) {
        position.x += (diameter - (diameter * dDiameter)) / 2;
        diameter *= dDiameter;
        bound.setSize(diameter, diameter);
        velocity.scl(dVelocity);
        position.y = y;
        bound.setPosition(position.x, position.y);
    }

    /**
     * Check for collision
     * @param platform boundary
     * @return true if collided
     */
    public boolean collides(Rectangle platform) {
        if (bound.overlaps(platform)) {
            return true;
        }
        return false;
    }

    public boolean collides(int y) {
        if (position.y < y) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets position of Ball.
     * @return position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Gets texture of Ball.
     * @return texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Gets diameter pf Ball
     * @return diameter
     */
    public float getDiameter() {
        return diameter;
    }

    /**
     * Dispose texture asset
     */
    public void dispose() {
        texture.dispose();
        System.out.println("Ball disposed");
    }

} // End of public class Ball

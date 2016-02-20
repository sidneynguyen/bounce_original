package com.projectjawn.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TopPlatform {

    private Texture texture;
    private Vector2 position;
    private float width;
    private float height;
    private Rectangle bound;

    public TopPlatform(float x, float y, float width, float height) {
        this.texture = new Texture("top.png");
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        bound = new Rectangle(x, y, width, height);
    }


    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBound() {
        return bound;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

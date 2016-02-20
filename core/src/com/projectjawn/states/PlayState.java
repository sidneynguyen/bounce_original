/*
 * Carnage Games
 * December 18, 2015
 * PlayState.java
 *
 * Game runner
 */

package com.projectjawn.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projectjawn.sprites.Ball;
import com.projectjawn.sprites.Platform;
import com.projectjawn.sprites.TopPlatform;


/**
 * A class running the main game and controlling game mechanics.
 */
public class PlayState extends State {

    private static final float BALL_X = 0.5f;
    private static final float BALL_Y = 0.8f;
    private static final float BALL_RADIUS = 0.075f;
    private static final float BALL_DIAMETER = 0.15f;
    private static final float BALL_ACCELERATION = -0.01f;
    private static final float BALL_GRAVITY = -0.008f;

    private static final float PLATFORM_X = 0.5f;
    private static final float PLATFORM_Y = 0.2f;
    private static final float PLATFORM_WIDTH = 0.3f;
    private static final float PLATFORM_HALF_WIDTH = 0.5f;
    private static final float PLATFORM_HEIGHT = 0.01f;
    private static final float PLATFORM_SPEED = 0.5f;
    private static final float PLATFORM_WIDTH_MULTIPLIER = 0.99f;
    private static final float PLATFORM_SPEED_MULTIPLIER = 1.01f;

    private static final float TOP_PLATFORM_HEIGHT = 0.05f;

    private static final float SCORE_TEXT_X = 0.1f;
    private static final float SCORE_TEXT_Y = 0.1f;

    private static final float BOUNCE_PLATFORM_DIAMETER = 0.999f;
    private static final float BOUNCE_PLATFORM_ACCELERATION = -0.9f;
    private static final float BOUNCE_TOP_DIAMETER = 1;
    private static final float BOUNCE_TOP_ACCELERATION = -0.5f;

    private Texture background;
    private Ball ball;
    private Platform platform;
    private TopPlatform top;
    private BitmapFont scoreText;
    int score;
    private Sound bounceSound;

    /**
     * Constructs a PlayState that initializes the game graphics and camera.
     * @param gsm GameStateManager
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        ball = new Ball((cam.viewportWidth * BALL_X) - (cam.viewportWidth * BALL_RADIUS),
                        cam.viewportHeight * BALL_Y,
                        (int) (cam.viewportWidth * BALL_DIAMETER),
                        cam.viewportHeight * BALL_GRAVITY,
                        cam.viewportHeight * BALL_ACCELERATION,
                        (int) cam.viewportHeight);
        platform = new Platform((int) ((cam.viewportWidth * PLATFORM_X) -
                                       (cam.viewportWidth * PLATFORM_WIDTH * PLATFORM_HALF_WIDTH)),
                                (int) (cam.viewportHeight * PLATFORM_Y),
                                (int) (cam.viewportWidth * PLATFORM_WIDTH),
                                (int) (cam.viewportHeight * PLATFORM_HEIGHT),
                                cam.viewportWidth * PLATFORM_SPEED,
                                (int) cam.viewportWidth);
        top = new TopPlatform(0, cam.viewportHeight - (cam.viewportHeight * TOP_PLATFORM_HEIGHT),
                              cam.viewportWidth, cam.viewportHeight * TOP_PLATFORM_HEIGHT);
        scoreText = new BitmapFont();
        scoreText.setColor(Color.BLUE);
        scoreText.getData().scale(5);
        bounceSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
    }

    /**
     * No action.
     */
    @Override
    public void handleInput() {

    }

    /**
     * Updates the ball and platform and checks for ball collision.
     * @param dt seconds per frame
     */
    @Override
    public void update(float dt) {
        handleInput();
        ball.update(dt);
        platform.update(dt);
        if (ball.collides(platform.getBound())) {
            ball.bounce(platform.getPosition().y + (int) (cam.viewportHeight * PLATFORM_HEIGHT),
                        BOUNCE_PLATFORM_DIAMETER, BOUNCE_PLATFORM_ACCELERATION);
            platform.bounce(PLATFORM_WIDTH_MULTIPLIER, PLATFORM_SPEED_MULTIPLIER);
            score++;
            bounceSound.play();
        }
        else if (ball.collides(top.getBound())) {
            ball.bounce(cam.viewportHeight - ((cam.viewportHeight * TOP_PLATFORM_HEIGHT) +
                                              ball.getDiameter()),
                        BOUNCE_TOP_DIAMETER, BOUNCE_TOP_ACCELERATION);
        }
        else if (ball.collides((int) (-ball.getDiameter()))) {
            Preferences hs = Gdx.app.getPreferences("highscore");
            if (score > hs.getInteger("highscore", 0)) {
                hs.putInteger("highscore", score);
                hs.flush();
            }
            gsm.pop();
        }
    }

    /**
     * Renders the background, ball, platform, and score.
     * @param sb sprite batch
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, cam.viewportWidth, cam.viewportHeight);
        sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y,
                ball.getDiameter(), ball.getDiameter());
        sb.draw(platform.getTexture(), platform.getPosition().x, platform.getPosition().y,
                platform.getWidth(), platform.getHeight());
        sb.draw(top.getTexture(), top.getPosition().x, top.getPosition().y, top.getWidth(),
                top.getHeight());
        scoreText.draw(sb, "" + score, (int) (cam.viewportWidth * SCORE_TEXT_X), (int) (cam.viewportHeight * SCORE_TEXT_Y));
        sb.end();
    }

    /**
     * Disposes of background, ball, and platform assets.
     */
    @Override
    public void dispose() {
        ball.dispose();
        platform.dispose();
        scoreText.dispose();
        System.out.println("PlayState disposed");
    }


} // End of public class PlayState extends State

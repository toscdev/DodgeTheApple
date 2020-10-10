package com.example.dodgetheapple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Droid {
    private static final float WIDTH = 112f;
    private static final float HEIGHT = 150f;
    private final Rectangle collisionRectangle;

    private float x = 0;
    private float y = 0;

    private static final float MAX_X_SPEED = 10f;
    private float xSpeed = 0f;

    private final Texture droidTexture;

    public Droid(Texture droidTexture) {
        this.droidTexture = droidTexture;
        collisionRectangle = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public static float getWIDTH() {
        return WIDTH;
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y,
                collisionRectangle.width, collisionRectangle.height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionRectangle();
    }

    private void updateCollisionRectangle() {
        collisionRectangle.setPosition(x, y);
    }


    public void updatePosition() {
        Input input = Gdx.input;
        if (input.isTouched() && (input.getX() > (Gdx.graphics.getWidth() / 2)) && ((collisionRectangle.getX() +
                collisionRectangle.getWidth()) < GameScreen.getWorldWidth())) {
            xSpeed = MAX_X_SPEED;
        } else if (input.isTouched() && (input.getX() < (Gdx.graphics.getWidth() / 2)) && (collisionRectangle.getX() > 0)) {
            xSpeed = -MAX_X_SPEED;
        } else {
            xSpeed = 0;
        }
        x += xSpeed;
        updateCollisionRectangle();

        if (input.justTouched()) {
            System.out.println(input.getX());
            System.out.println(Gdx.graphics.getWidth());
        }
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(droidTexture, collisionRectangle.x, collisionRectangle.y,
                collisionRectangle.getWidth(), collisionRectangle.getHeight());
    }
}






















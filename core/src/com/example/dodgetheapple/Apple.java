package com.example.dodgetheapple;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Apple {
    private static final float COLLISION_RECTANGLE_WIDTH = 100;
    private static final float COLLISION_RECTANGLE_HEIGHT = 100;
    private final Rectangle collisionRectangle;

    private float x = 0;
    private float y = 0;

    private static final float MAX_SPEED_PER_SECOND = 300f;

    private final Texture appleTexture;

    public Apple(Texture appleTexture) {
        this.appleTexture = appleTexture;
        this.collisionRectangle = new Rectangle(x, y,
                COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionRectangle();
    }

    private void updateCollisionRectangle() {
        collisionRectangle.setX(x);
        collisionRectangle.setY(y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y,
                collisionRectangle.width, collisionRectangle.height);
    }

    public void updatePosition(float delta) {
        setPosition(x, y - (MAX_SPEED_PER_SECOND * delta));

        updateCollisionRectangle();
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public boolean droidColliding(Droid droid) {
        Rectangle droidCollisionRectangle = droid.getCollisionRectangle();
        return Intersector.overlaps(droidCollisionRectangle, collisionRectangle);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(appleTexture, collisionRectangle.x, collisionRectangle.y,
                collisionRectangle.getWidth(), collisionRectangle.getHeight());
    }
}

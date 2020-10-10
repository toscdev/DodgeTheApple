package com.example.dodgetheapple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;

    private Droid droid;

    private Array<Apple> apples = new Array<Apple>();

    private float time = 0;
    private float score = 0;

    private float timeTillAppleSpawns = 2;


    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private Texture background;
    private Texture droidTexture;
    private Texture appleTexture;

    private final Game game;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        background = new Texture("bg.png");

        droidTexture = new Texture("android8bit.png");
        appleTexture = new Texture("apple8bit.png");

        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        droid = new Droid(droidTexture);
        droid.setPosition((WORLD_WIDTH - droid.getWIDTH()) / 2, WORLD_HEIGHT / 50);

        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        glyphLayout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        draw();
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //droid.drawDebug(shapeRenderer);

        //for (Apple apple : apples) {
        //    apple.drawDebug(shapeRenderer);
        //}
        update(delta);
        shapeRenderer.end();
    }

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        droid.draw(batch);
        for (Apple apple : apples) {
            apple.draw(batch);
        }
        drawScore();
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void update(float delta) {
        droid.updatePosition();
        updateApples(delta);
        countScore(delta);
        if (checkCollision()) {
            gameover();
        }
    }

    private void createNewApple() {
        Apple newApple = new Apple(appleTexture);
        newApple.setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - newApple.getCollisionRectangle().getWidth()),
                WORLD_HEIGHT + newApple.getCollisionRectangle().getHeight());
        apples.add(newApple);
    }

    private void updateApples(float delta) {
        for (Apple apple : apples) {
            apple.updatePosition(delta);
        }
        time += delta;
        if (time >= timeTillAppleSpawns) {
            createNewApple();
            if (timeTillAppleSpawns >= 0.5)
                timeTillAppleSpawns -= 0.1;
            time = 0;
        }
    }

    private void countScore(float delta) {
        score += delta;
    }

    private boolean checkCollision() {
        for (Apple apple : apples) {
            if (apple.droidColliding(droid)) {
                return true;
            }
        }
        return false;
    }

    private void gameover() {
        game.setScreen(new GameOverScreen(game, score));
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        appleTexture.dispose();
        droidTexture.dispose();
        background.dispose();
    }

    public GameScreen(Game game) {
        this.game = game;
    }

    private void drawScore() {
        int scoreInt = (int)score;
        String scoreString = Integer.toString(scoreInt) + " punkte";
        glyphLayout.setText(bitmapFont, scoreString);
        bitmapFont.draw(batch, scoreString, ((viewport.getWorldWidth() - glyphLayout.width) / 2),
                viewport.getWorldHeight() - (viewport.getWorldHeight() / 10));
    }

    public static float getWorldWidth() {
        return WORLD_WIDTH;
    }
}




















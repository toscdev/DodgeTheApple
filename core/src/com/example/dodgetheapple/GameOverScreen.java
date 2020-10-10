package com.example.dodgetheapple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();

    private Stage stage;

    private final Game game;

    private Texture backgroundTexture;
    private Texture playAgainButtonTexture;

    private float score = 0;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private SpriteBatch batch;


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("bg.png");
        Image background = new Image(backgroundTexture);
        background.setX(0);
        background.setY(0);
        background.setWidth(WORLD_WIDTH);
        background.setHeight(WORLD_HEIGHT);

        playAgainButtonTexture = new Texture("playagainbutton.png");
        Image playAgainButton = new Image(playAgainButtonTexture);
        playAgainButton.setWidth(300);
        playAgainButton.setHeight(200);
        playAgainButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 5, Align.center);

        stage.addActor(background);
        stage.addActor(playAgainButton);

        playAgainButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        glyphLayout = new GlyphLayout();
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        batch.begin();
        drawGameOver();
        batch.end();
    }

    private void drawGameOver() {
        int scoreInt = (int)score;
        String gameOverString = "du hast\n" + Integer.toString(scoreInt) +
                " punkte\nerreicht";
        glyphLayout.setText(bitmapFont, gameOverString);
        bitmapFont.draw(batch, gameOverString,
                (WORLD_WIDTH / 2) - (glyphLayout.width / 2), WORLD_HEIGHT - (WORLD_HEIGHT / 3));
        //bitmapFont.draw(batch, "test", 300, 300);
    }

    public GameOverScreen(Game game, float score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playAgainButtonTexture.dispose();
        bitmapFont.dispose();
    }
}

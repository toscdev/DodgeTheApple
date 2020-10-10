package com.example.dodgetheapple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();

    private Stage stage;

    private final Game game;

    private Texture backgroundTexture;
    private Texture playbuttonTexture;
    private Texture titleTexture;

    public void show() {

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("bg.png");
        Image background = new Image(backgroundTexture);
        background.setX(0);
        background.setY(0);
        background.setWidth(WORLD_WIDTH);
        background.setHeight(WORLD_HEIGHT);

        playbuttonTexture = new Texture("playbutton.png");
        Image playbutton = new Image(playbuttonTexture);
        playbutton.setWidth(300);
        playbutton.setHeight(200);
        playbutton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 5, Align.center);

        titleTexture = new Texture("title.png");
        Image title = new Image(titleTexture);
        title.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 3), Align.center);

        stage.addActor(background);
        stage.addActor(playbutton);
        stage.addActor(title);

        playbutton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playbuttonTexture.dispose();
        titleTexture.dispose();
    }
}

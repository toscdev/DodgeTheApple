package com.example.dodgetheapple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class DodgeTheAppleGame extends Game {
	
	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}
}

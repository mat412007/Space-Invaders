package com.space_invaders;

import com.badlogic.gdx.Game; // Importamos la clase Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// 'MyGame' extiende Game, que es la forma correcta de manejar pantallas.
public class MyGame extends Game {

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // 1. Cargamos la pantalla inicial (GameScreen)
        // Le pasamos 'this' (una referencia a MyGame) para que GameScreen pueda acceder al batch y cambiar de pantalla.
        setScreen(new MenuScreen(this));
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        // Asegúrate de liberar el batch al salir
        super.dispose();
        batch.dispose();
    }
}

package com.space_invaders;

import com.badlogic.gdx.Game; // Importamos la clase Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// 'MyGame' extiende Game, que es la forma correcta de manejar pantallas.
public class MyGame extends Game {
    // Definimos el SpriteBatch aquí para que todas las pantallas lo compartan
    private SpriteBatch batch;

    @Override
    public void create() {
        // Inicializar el SpriteBatch una sola vez
        batch = new SpriteBatch();

        // 1. Cargamos la pantalla inicial (GameScreen)
        // Le pasamos 'this' (una referencia a MyGame) para que GameScreen pueda acceder al batch y cambiar de pantalla.
        setScreen(new GameScreen(this));
    }

    // Método para obtener el SpriteBatch (útil para todas las pantallas)
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        // Asegúrate de liberar el batch al salir
        super.dispose();
        batch.dispose();
    }

    // NOTA: Los métodos render(), resize(), etc. de ApplicationAdapter ya no son necesarios aquí.
    // La clase Game llama a los métodos de la pantalla activa.
}

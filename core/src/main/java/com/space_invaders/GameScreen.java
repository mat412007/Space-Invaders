package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Importamos la interfaz Screen
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

// GameScreen implementa Screen
public class GameScreen implements Screen {
    final MyGame game; // Referencia al objeto MyGame principal

    // --- Mover todas las variables de tu juego aquí ---
    private Texture nave;
    private Texture nave_2;
    private Texture disparo;
    private Texture alien;
    private Jugador jugador;
    private Jugador2 jugador_2;
    private AlienManager alienManager;
    // --------------------------------------------------

    public GameScreen(final MyGame game) {
        this.game = game;

        // --- LÓGICA DEL ANTIGUO 'create()' ---
        // El SpriteBatch lo obtenemos de la clase MyGame

        nave = new Texture("nave.png");
        nave_2 = new Texture("nave_2.png");
        disparo = new Texture("bala_2.png");
        alien = new Texture("alien_1.png");
        jugador = new Jugador(nave, disparo);
        jugador_2 = new Jugador2(nave_2, disparo);

        int anchoAliens = 7;
        int altoAliens = 4;
        int espacioAliens = 80;
        alienManager = new AlienManager(altoAliens, anchoAliens, espacioAliens, alien);
    }

    // El método principal de la pantalla, reemplaza a render() del ApplicationAdapter
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        float deltaTime = Gdx.graphics.getDeltaTime();

        // --- LÓGICA DE ACTUALIZACIÓN DEL JUEGO ---
        alienManager.ActualizarMovimiento(deltaTime);

        // Usamos el batch de la clase MyGame
        game.getBatch().begin();

        // Lógica de dibujo y colisión (Copiado de tu antiguo render)
        jugador.Dibujar(game.getBatch());
        jugador_2.Dibujar(game.getBatch());

        // Comprobar la colisión con los aliens
        for (Alien alien : alienManager.getAliens()) {
            if (alien.colisionConBala(jugador.sprite_disparo) && alien.alive) {
                jugador.posicion_disparo.y = 10000;
                alien.alive = false;
            }
            if (alien.colisionConBala(jugador_2.sprite_disparo) && alien.alive) {
                jugador_2.posicion_disparo.y = 10000;
                alien.alive = false;
            }
            alien.Dibujar(game.getBatch());
        }

        game.getBatch().end();
    }

    // --- Métodos obligatorios de la interfaz Screen ---

    @Override
    public void resize(int width, int height) {
        // Aquí es donde irá la lógica del Viewport más adelante.
    }

    @Override
    public void dispose() {
        // Liberar todos los assets que son exclusivos de esta pantalla.
        nave.dispose();
        nave_2.dispose();
        disparo.dispose();
        alien.dispose();
        // El batch NO se libera aquí, se libera en MyGame.
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Importamos la interfaz Screen
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

// GameScreen implementa Screen
public class GameScreen implements Screen {
    final MyGame game;

    private Texture nave;
    private Texture nave_2;
    private Texture disparo;
    private Texture alien;
    private Jugador jugador;
    private Jugador2 jugador_2;
    private AlienManager alienManager;

    public GameScreen(final MyGame game) {
        this.game = game;

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

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        float deltaTime = Gdx.graphics.getDeltaTime();
        alienManager.ActualizarMovimiento(deltaTime);

        game.getBatch().begin();

        jugador.Dibujar(game.getBatch());
        jugador_2.Dibujar(game.getBatch());

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
        // Aquí va la lógica del viewport
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

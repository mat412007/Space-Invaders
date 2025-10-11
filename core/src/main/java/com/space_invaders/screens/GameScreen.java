package com.space_invaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Importamos la interfaz Screen
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.space_invaders.*;

import java.awt.*;

// GameScreen implementa Screen
public class GameScreen implements Screen {
    final MyGame game;
    private SpriteBatch batch;

    private Texture fondo;
    private Sprite fondoPantalla;

    private boolean multijugador;

    private Texture nave;
    private Texture nave_2;
    private Texture disparo;
    private Texture alien;
    private Jugador jugador;
    private Jugador2 jugador_2;
    private AlienManager alienManager;

    public GameScreen(final MyGame game, boolean multijugador) {
        this.game = game;
        batch = game.getBatch();

        fondo = new Texture("FondoJuego.png");
        fondoPantalla = new Sprite(fondo);

        this.multijugador = multijugador;

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

        if(!multijugador) {
            jugador.posicion = new Vector2(Gdx.graphics.getWidth()/2f-jugador.sprite.getWidth()/2f, 10);
            jugador_2.posicion = new Vector2(0, Gdx.graphics.getHeight());
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        float deltaTime = Gdx.graphics.getDeltaTime();
        alienManager.ActualizarMovimiento(deltaTime);

        batch.begin();
        // batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibujar fondo de pantalla ajustado
        fondoPantalla.setSize(700, Gdx.graphics.getHeight());
        fondoPantalla.setPosition(150, 0);
        fondoPantalla.draw(batch);

        // Dibujamos las naves de los jugadores
        jugador.Dibujar(batch);
        jugador_2.Dibujar(batch);

        // Cuando ya no quedan aliens
        if(alienManager.victoria()){
            game.setScreen(new MenuScreen(game));
        }

        // Si la bala conecta con un alien, si transporta fuera de la pantalla
        if (alienManager.colisionConBala(jugador.sprite_disparo)) {
            jugador.posicion_disparo.y = Gdx.graphics.getHeight();
        }
        if (alienManager.colisionConBala(jugador_2.sprite_disparo)) {
            jugador_2.posicion_disparo.y = Gdx.graphics.getHeight();
        }

        for (Alien alien : alienManager.getAliens()) {
            // Si los aliens tocan a cualquier jugador, ambos pierden
            if(alien.sprite.getBoundingRectangle().overlaps(jugador.sprite.getBoundingRectangle()) ||
                alien.sprite.getBoundingRectangle().overlaps(jugador_2.sprite.getBoundingRectangle())){
                game.setScreen(new MenuScreen(game));
            }
        }
        alienManager.Dibujar(batch);
        game.getBatch().end();
        Linea.dibujar(150, 0, 150, Gdx.graphics.getHeight()); // Espacio de juego es de 700
        Linea.dibujar(850, 0, 850, Gdx.graphics.getHeight());
    }

    // --- Métodos obligatorios de la interfaz Screen ---

    @Override
    public void resize(int width, int height) {
        // Aquí va la lógica del viewport
    }

    @Override
    public void dispose() {
        nave.dispose();
        nave_2.dispose();
        disparo.dispose();
        alien.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

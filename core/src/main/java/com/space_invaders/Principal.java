package com.space_invaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Principal extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture nave;
    private Texture nave_2;
    private Texture disparo;
    private Texture alien;
    private Jugador jugador;
    private Jugador2 jugador_2;
    Alien[] aliens;

    @Override
    public void create() {
        batch = new SpriteBatch();
        nave = new Texture("nave.png");
        nave_2 = new Texture("nave_2.png");
        disparo = new Texture("bala_2.png");
        alien = new Texture("alien_1.png");
        jugador = new Jugador(nave, disparo);
        jugador_2 = new Jugador2(nave_2, disparo);

        int anchoAliens = 7;  // cantidad por fila
        int altoAliens = 4;   // cantidad de filas
        int espacioAliens = 75;
        aliens = new Alien[anchoAliens * altoAliens]; // Array con los aliens
        Alien.llenar(altoAliens, anchoAliens, aliens, espacioAliens, alien); // Inserto los aliens en el array
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f); // Color del fondo de pantalla
        batch.begin();
        jugador.Dibujar(batch);
        jugador_2.Dibujar(batch);

        // Comprobar la colisión con los aliens
        for (Alien alien : aliens) {
            if (alien.colisionConBala(jugador.sprite_disparo) && alien.alive) {
                jugador.posicion_disparo.y = 10000;  // Desplazar la bala fuera de la pantalla
                alien.alive = false;  // Destruir el alien
            }
            if (alien.colisionConBala(jugador_2.sprite_disparo) && alien.alive) {
                jugador_2.posicion_disparo.y = 10000;  // Desplazar la bala fuera de la pantalla
                alien.alive = false;  // Destruir el alien
            }
        }

        // Dibujo a los aliens
        Alien.Dibujar(batch, aliens);

        // Mostrar el puntaje en la esquina superior izquierda
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        nave.dispose();
        nave_2.dispose(); // No olvides liberar la fuente
    }
}

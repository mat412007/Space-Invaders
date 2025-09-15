package com.space_invaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Principal extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture disparo;
    private Texture alien;
    private Jugador jugador;
    Alien[] aliens;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("nave.png");
        disparo = new Texture("bala_2.png");
        alien = new Texture("alien_1.png");
        jugador = new Jugador(image, disparo);

        int anchoAliens = 7;  // cantidad de aliens por fila
        int altoAliens = 4;   // cantidad de filas
        int espacioAliens = 75;
        aliens = new Alien[anchoAliens * altoAliens];

        int i = 0;
        for (int x = 0; x < altoAliens; x++) {
            for (int y = 0; y < anchoAliens; y++) {
                Vector2 posicionAlien = new Vector2(y*espacioAliens, x*espacioAliens);
                posicionAlien.x += Gdx.graphics.getWidth()/2f;
                posicionAlien.y += Gdx.graphics.getHeight();
                posicionAlien.x -= (anchoAliens/2f) * espacioAliens;
                posicionAlien.y -= altoAliens * espacioAliens;

                aliens[i] = new Alien(posicionAlien, alien, Color.GREEN);
                i++;
            }
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f); // Color del fondo de pantalla
        batch.begin();
        jugador.Dibujar(batch);

        for (Alien alien : aliens) {
            // Verificar si la bala colisiona con cada alien
            if (alien.colisionConBala(jugador.sprite_disparo) && alien.alive) {
                jugador.posicion_disparo.y = 10000;
                alien.alive = false;  // Destruir el alien si hay colisión
                break;
            }
        }
        for(Alien alien: aliens){
            alien.Dibujar(batch);  // Dibujar el alien si está vivo
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}

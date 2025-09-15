package com.space_invaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
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
                /*float a = Gdx.graphics.getWidth() / 2f - alien.getWidth() * 0.15f / 2;
                float b = Gdx.graphics.getHeight() / 2f - alien.getHeight() * 0.15f / 2;
                Vector2 posicionAlien = new Vector2(a, b);*/

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
        for (Alien value : aliens) {
            value.Dibujar(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}

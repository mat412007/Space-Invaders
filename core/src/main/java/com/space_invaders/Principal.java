package com.space_invaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

        int anchoAliens = 5;  // cantidad de aliens por fila
        int altoAliens = 3;   // cantidad de filas
        int espacioAliens = 100;
        aliens = new Alien[anchoAliens * altoAliens];

        int i = 0;
        for (int x = 0; x < altoAliens; x++) {
            for (int y = 0; y < anchoAliens; y++) {
                // Vector2 posicionAlien = new Vector2(50 + y * espacioAliens, 300 + x * espacioAliens - 200);
                Vector2 posicionAlien = new Vector2(Gdx.graphics.getWidth()/2f - alien.getWidth()/2f, Gdx.graphics.getHeight()/2f);
                System.out.println(Gdx.graphics.getWidth() + "\n" + Gdx.graphics.getHeight());
                System.out.println(alien.getWidth() + "\n" + alien.getHeight());
                aliens[i] = new Alien(posicionAlien, alien);
                i++;
            }
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f); // Color del fondo de pantalla
        batch.begin();
        jugador.Dibujar(batch);
        for(int i = 0; i < aliens.length; i++){
            aliens[i].Dibujar(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}

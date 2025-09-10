package com.space_invaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Principal extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture disparo;
    private Jugador jugador;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("nave.png");
        disparo = new Texture("bala_2.png");
        jugador = new Jugador(image, disparo);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f); // Color del fondo de pantalla
        batch.begin();
        jugador.Dibujar(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}

package com.space_invaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.space_invaders.MyGame;

public class Controles implements Screen {

    SpriteBatch batch;
    MyGame game;

    BitmapFont font;
    GlyphLayout layout;

    // Imagenes
    Texture nave_1;
    Texture nave_2;
    Texture imagenEnemigo;

    // Visuales
    Sprite jugador_1;
    Sprite jugador_2;
    Sprite enemigo;

    // Posiciones
    Vector2 posicion_1;
    Vector2 posicion_2;
    Vector2 posicionEnemigo;

    public Controles(final MyGame game){
        this.game = game;
        batch = game.getBatch();

        nave_1 = new Texture("nave.png");
        nave_2 = new Texture("nave_2.png");
        imagenEnemigo = new Texture("alien_1.png");

        jugador_1 = new Sprite(nave_1);
        jugador_2 = new Sprite(nave_2);
        enemigo = new Sprite(imagenEnemigo);

        // Tamaño de visuales
        jugador_1.setSize(100, 100);
        jugador_2.setSize(100, 100);
        enemigo.setSize(100, 100);

        posicion_1 = new Vector2(25, Gdx.graphics.getHeight()*(5/8f));
        posicion_2 = new Vector2(25, Gdx.graphics.getHeight()*(3/8f));
        posicionEnemigo = new Vector2(25, Gdx.graphics.getHeight()*(1/8f));

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3f);
        layout = new GlyphLayout();
    }

    // Para dibujar las imágenes
    public void dibujarJugador1(SpriteBatch batch){
        jugador_1.setPosition(posicion_1.x, posicion_1.y);
        jugador_1.draw(batch);
    }
    public void dibujarJugador2(SpriteBatch batch){
        jugador_2.setPosition(posicion_2.x, posicion_2.y);
        jugador_2.draw(batch);
    }
    public void dibujarEnemigo(SpriteBatch batch){
        enemigo.setPosition(posicionEnemigo.x, posicionEnemigo.y);
        enemigo.draw(batch);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        batch.begin();
        // Dibujamos las imágenes
        dibujarJugador1(batch);
        dibujarJugador2(batch);
        dibujarEnemigo(batch);

        String titulo = "SPACE INVADERS";
        layout.setText(font, titulo);
        font.draw(batch, titulo, (Gdx.graphics.getWidth()/2f)-(layout.width/2f), Gdx.graphics.getHeight()-layout.height-10f);
        batch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        nave_1.dispose();
        nave_2.dispose();
        imagenEnemigo.dispose();
    }
}

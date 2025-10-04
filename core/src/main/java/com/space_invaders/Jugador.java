package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Jugador {
    private float x;
    private float y;
    public Vector2 posicion;
    public Vector2 posicion_disparo;
    public Sprite sprite;
    public Sprite sprite_disparo;
    public float velocidad = 350;
    public float velocidad_disparo = 1300;

    public Jugador(Texture img_nave, Texture img_disparo){
        sprite = new Sprite(img_nave);
        sprite_disparo = new Sprite(img_disparo);

        // Redimensionar la imagen directamente al cargarla
        float scaleFactor = 0.15f;  // Factor de escala para cambiar el tamaño
        sprite.setSize(sprite.getWidth() * scaleFactor, sprite.getHeight() * scaleFactor);
        sprite_disparo.setSize(sprite.getWidth() * scaleFactor - 2.5f, sprite.getHeight() * scaleFactor + 10);

        // Calcular la posición centrada
        x = (float) Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2 - sprite.getWidth();
        y = 10;
        posicion = new Vector2(x, y);
        posicion_disparo = new Vector2(0, 10000);
    }

    // Metodo para actualizar la posición de la nave
    public void Actualizar(float deltaTime){
        // Teclas para el movimiento
        if(Gdx.input.isKeyPressed(Keys.A)){
            posicion.x -= deltaTime * velocidad;
        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            posicion.x += deltaTime * velocidad;
        }
        // Para que la nave no se salga de los limites
        if(posicion.x < 0){
            posicion.x = 0;
        } else if(posicion.x >= Gdx.graphics.getWidth()-sprite.getWidth()){
            posicion.x = Gdx.graphics.getWidth()-sprite.getWidth();
        }

        posicion_disparo.y += deltaTime*velocidad_disparo;
        if(Gdx.input.isKeyPressed(Keys.W) && posicion_disparo.y >= Gdx.graphics.getHeight()){
            posicion_disparo.x = posicion.x + sprite.getWidth()/2 - sprite_disparo.getWidth()/2;// +(sprite_disparo.getWidth()/4);
            posicion_disparo.y = posicion.y + sprite.getHeight();
        }
    }

    // Metodo para dibujar la nave en la posicion actualizada
    public void Dibujar(SpriteBatch batch){
        Actualizar(Gdx.graphics.getDeltaTime());
        sprite.setPosition(posicion.x, posicion.y);
        sprite.draw(batch);

        sprite_disparo.setPosition(posicion_disparo.x, posicion_disparo.y);
        sprite_disparo.draw(batch);
    }
}

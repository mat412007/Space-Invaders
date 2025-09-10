package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Jugador {
    public Vector2 posicion;
    public Sprite sprite;
    private float x;
    private float y;
    public float velocidad = 350;

    public Jugador(Texture img){
        sprite = new Sprite(img);

        // Redimensionar la imagen directamente al cargarla
        float scaleFactor = 0.15f;  // Factor de escala para cambiar el tamaño
        sprite.setSize(sprite.getWidth() * scaleFactor, sprite.getHeight() * scaleFactor);

        // Calcular la posición centrada
        x = (float) Gdx.graphics.getWidth() / 2 - (sprite.getWidth()) / 2;
        y = 15;
        posicion = new Vector2(x, y);
    }

    // Metodo para actualizar la posición de la nave
    public void Actualizar(float deltaTime){
        if(Gdx.input.isKeyPressed(Keys.A)){
            posicion.x -= deltaTime * velocidad;
        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            posicion.x += deltaTime * velocidad;
        }
    }

    // Metodo para dibujar la nave en la posicion actualizada
    public void Dibujar(SpriteBatch batch){
        Actualizar(Gdx.graphics.getDeltaTime());
        sprite.setPosition(posicion.x, posicion.y);
        sprite.draw(batch);
    }
}

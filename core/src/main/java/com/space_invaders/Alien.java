package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Alien {
    public boolean alive;  // Ahora es un campo por cada alien
    public Vector2 posicion;
    public Sprite sprite;

    public Alien(Vector2 posicion, Texture image, Color color){
        this.sprite = new Sprite(image);
        sprite.setColor(color);
        sprite.setSize(image.getWidth()*0.15f, image.getHeight()*0.15f);
        this.posicion = posicion;
        this.alive = true;  // Se inicializa como vivo
    }

    public void Dibujar(SpriteBatch batch){
        if(alive){
            this.sprite.setPosition(posicion.x, posicion.y);
            this.sprite.draw(batch);
        }
    }

    // Metodo para verificar la colisión con una bala
    public boolean colisionConBala(Sprite spriteBala) {
        return sprite.getBoundingRectangle().overlaps(spriteBala.getBoundingRectangle());
    }

    // llenar el array de los aliens
    public static void llenar(int alto, int ancho, Alien[] aliens, int espacio, Texture alien_img){
        int i = 0;
        for (int x = 0; x < alto; x++) {
            for (int y = 0; y < ancho; y++) {
                Vector2 posicionAlien = new Vector2(y * espacio, x * espacio);
                posicionAlien.x += Gdx.graphics.getWidth() / 2f;
                posicionAlien.y += Gdx.graphics.getHeight();
                posicionAlien.x -= (ancho / 2f) * espacio;
                posicionAlien.y -= alto * espacio;
                aliens[i] = new Alien(posicionAlien, alien_img, Color.GREEN);
                i++;
            }
        }
    }
}

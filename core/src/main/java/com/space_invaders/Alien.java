package com.space_invaders;

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
        sprite = new Sprite(image);
        sprite.setColor(color);
        sprite.setSize(image.getWidth()*0.15f, image.getHeight()*0.15f);
        this.posicion = posicion;
        this.alive = true;  // Se inicializa como vivo
    }

    public void Dibujar(SpriteBatch batch){
        if (alive) {  // Solo se dibuja si está vivo
            sprite.setPosition(posicion.x, posicion.y);
            sprite.draw(batch);
        }
    }

    // Metodo para verificar la colisión con una bala
    public boolean colisionConBala(Sprite spriteBala) {
        return sprite.getBoundingRectangle().overlaps(spriteBala.getBoundingRectangle());
    }
}

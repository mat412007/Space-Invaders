package com.space_invaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Alien {
    public Vector2 posicion;
    public Sprite sprite;
    private int x;
    private int y;

    public Alien(Texture image){
        sprite = new Sprite(image);
        posicion = new Vector2(x, y);
    }

    public void Dibujar(SpriteBatch batch){
        sprite.setPosition(posicion.x, posicion.y);
        sprite.draw(batch);
    }

}

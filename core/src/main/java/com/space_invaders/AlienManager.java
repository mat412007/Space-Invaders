package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AlienManager {
    private Alien[] aliens;
    private int aliensRestantes;

    private float alienMoveDirectionHorizontal = 1f;
    private float alienMoveDirectionVertical = 1f;
    private final float alienHorizontalSpeed = 250f;

    // Variables para el descenso fluido
    private final float alienTotalDropDistance = 25f;
    private final float alienVerticalSpeed = 100f;
    private float currentDropDistance = 0f; // Distancia recorrida entre bajadas

    public AlienManager(int alto, int ancho, int espacio, Texture alien_img) {
        aliens = new Alien[ancho * alto];
        llenar(alto, ancho, aliens, espacio, alien_img);
        aliensRestantes = getAliens().length;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public void ActualizarMovimiento(float deltaTime) {

        boolean hayAliensVivos;

        // Si ya hay una distancia de descenso pendiente, aplicarla primero
        if (currentDropDistance > 0f && currentDropDistance < alienTotalDropDistance) {

            float descensoEnEsteFrame = alienVerticalSpeed * deltaTime;

            /*float minY = Float.MAX_VALUE;
            float maxY = Float.MIN_VALUE;
            hayAliensVivos = false;
            for (Alien alien : aliens) {
                if (alien.alive) {
                    hayAliensVivos = true;
                    if (alien.sprite.getX() < minY) minY = alien.sprite.getX();
                    if (alien.sprite.getX() > maxY) maxY = alien.sprite.getX();
                }
            }*/

            // Limita el descenso
            if (currentDropDistance + descensoEnEsteFrame > alienTotalDropDistance) {
                descensoEnEsteFrame = alienTotalDropDistance - currentDropDistance;
            }

            // Aplica el descenso
            for (Alien alien : aliens) {
                if (alien.alive) {
                    alien.posicion.y -= descensoEnEsteFrame;
                    alien.sprite.setPosition(alien.posicion.x, alien.posicion.y);
                }
            }

            currentDropDistance += descensoEnEsteFrame;

            // Si el descenso terminó en este frame, lo forzamos a 0.0f para reanudar el horizontal
            if (currentDropDistance >= alienTotalDropDistance) {
                currentDropDistance = 0.0f;
            }

            return; // No mover horizontalmente mientras desciende
        }

        // --- Si el descenso está inactivo o acaba de terminar, movemos horizontalmente ---
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        hayAliensVivos = false;
        boolean tocaBorde = false;

        // 1. Encontrar límites
        for (Alien alien : aliens) {
            if (alien.alive) {
                hayAliensVivos = true;
                if (alien.sprite.getX() < minX) minX = alien.sprite.getX();
                if (alien.sprite.getX() > maxX) maxX = alien.sprite.getX();
            }
        }

        if (!hayAliensVivos) return;

        // 2. Comprueba si toca el borde y activa la bandera tocaBorde
        float screenRight = Gdx.graphics.getWidth();
        float alienWidth = aliens[0].sprite.getWidth();

        if (alienMoveDirectionHorizontal == 1.0f && (maxX + alienWidth) >= screenRight) {
            alienMoveDirectionHorizontal = -1.0f; // Cambia a izquierda
            tocaBorde = true;
        } else if (alienMoveDirectionHorizontal == -1.0f && minX <= 0) {
            alienMoveDirectionHorizontal = 1.0f; // Cambia a derecha
            tocaBorde = true;
        }

        // 3. Aplicar movimiento
        if (tocaBorde) {
            // SI toca el borde, inciamos el descenso, y el movimiento horizontal se detendrá
            currentDropDistance = 0.001f;
        }


        // Si no tocamos el borde, nos movemos horizontalmente
        if (!tocaBorde) {
            float movimientoHorizontal = alienHorizontalSpeed * alienMoveDirectionHorizontal * deltaTime;

            for (Alien alien : aliens) {
                if (alien.alive) {
                    alien.posicion.x += movimientoHorizontal;
                    alien.sprite.setPosition(alien.posicion.x, alien.posicion.y);
                }
            }
        }
    }

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

    // Verificar el impacto de la bala con el alien
    public boolean colisionConBala(Sprite spriteBala) {
        for(Alien alien : aliens){
            if(alien.sprite.getBoundingRectangle().overlaps(spriteBala.getBoundingRectangle()) && alien.alive){
                alien.alive = false;
                aliensRestantes--;
                return true;
            }
        }
        return false;
    }

    public boolean victoria(){
        return aliensRestantes == 0;
    }

    public void Dibujar(SpriteBatch batch) {
        for (Alien alien : aliens) {
            alien.Dibujar(batch);
        }
    }
}

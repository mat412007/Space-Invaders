package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AlienManager {
    private Alien[] aliens;
    private int aliensRestantes;

    float currentDropDistance = 0f; // Distancia recorrida entre bajadas
    float alienMoveDirectionHorizontal = 1f;
    float alienMoveDirectionVertical = 1f;

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
        // Variables para el descenso fluido
        float alienTotalDropDistance = 25f;
        final float alienHorizontalSpeed = 500f;
        float alienVerticalSpeed = 300f;

        // Si ya hay una distancia de descenso pendiente, aplicarla primero
        if (currentDropDistance > 0f && currentDropDistance < alienTotalDropDistance) {

            float minY = Float.MAX_VALUE;
            float maxY = Float.MIN_VALUE;
            float alienHeight = aliens[0].sprite.getHeight();

            // 1. Encontrar límites
            for (Alien alien : aliens) {
                if (alien.alive) {
                    if (alien.sprite.getY() < minY) minY = alien.sprite.getY();
                    if (alien.sprite.getY() > maxY) maxY = alien.sprite.getY() + alienHeight;
                }
            }

            System.out.println("alienMoveDirectionVertical" + alienMoveDirectionVertical);
            System.out.println("minY " + minY + " alienHeight "+ alienHeight);
            if (alienMoveDirectionVertical == -1f && maxY >= Gdx.graphics.getHeight()) {
                alienMoveDirectionVertical = 1f;
            } else if (alienMoveDirectionVertical == 1f && minY <= 0) {
                alienMoveDirectionVertical = -1f; // C
            }

            float descensoEnEsteFrame = alienVerticalSpeed * deltaTime * alienMoveDirectionVertical;

            // Aplica el descenso
            for (Alien alien : aliens) {
                if (alien.alive) {
                    alien.posicion.y -= descensoEnEsteFrame;
                    alien.sprite.setPosition(alien.posicion.x, alien.posicion.y);
                }
            }

            currentDropDistance += descensoEnEsteFrame *  alienMoveDirectionVertical;

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
        float alienWidth = aliens[0].sprite.getWidth();
        for (Alien alien : aliens) {
            if (alien.alive) {
                hayAliensVivos = true;
                if (alien.sprite.getX() < minX) minX = alien.sprite.getX();
                if (alien.sprite.getX() > maxX) maxX = alien.sprite.getX() + alienWidth;
            }
        }

        if (!hayAliensVivos) return;

        // 2. Comprueba si toca el borde y activa la bandera tocaBorde
        float screenLeft = 150;
        float screenRight = 850;

        if (alienMoveDirectionHorizontal == 1.0f && maxX >= screenRight) {
            alienMoveDirectionHorizontal = -1.0f; // Cambia a izquierda
            tocaBorde = true;
        } else if (alienMoveDirectionHorizontal == -1.0f && minX <= screenLeft) {
            alienMoveDirectionHorizontal = 1.0f; // Cambia a derecha
            tocaBorde = true;
        }

        // 3. Aplicar movimiento
        if (tocaBorde) {
            // SI toca el borde, iniciamos el descenso, y el movimiento horizontal se detendrá
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

    // Llenamos el array de los aliens al empezar la partida
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

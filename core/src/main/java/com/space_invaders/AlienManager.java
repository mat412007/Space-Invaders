package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienManager {
    private Alien[] aliens;
    private float alienMoveDirection = 1.0f; // 1.0f para derecha, -1.0f para izquierda
    private final float alienHorizontalSpeed = 100.0f; // Píxeles/segundo
    private final float alienVerticalStep = 40.0f; // Cuánto bajan
    private float timeSinceLastMove = 0.0f;
    private final float moveInterval = 0.5f; // Tiempo entre cada "paso" del bloque

    public AlienManager(int alto, int ancho, int espacio, Texture alien_img) {
        // Inicializa el array y llama al método estático de Alien para llenarlo.
        aliens = new Alien[ancho * alto];
        Alien.llenar(alto, ancho, aliens, espacio, alien_img);
    }

    public Alien[] getAliens() {
        return aliens;
    }

    // El método principal de actualización para el grupo
    public void ActualizarMovimiento(float deltaTime) {
        timeSinceLastMove += deltaTime;
        boolean mustDrop = false;

        // Solo movemos el bloque en el intervalo definido
        if (timeSinceLastMove >= moveInterval) {

            // 1. Encontrar límites del bloque (solo aliens vivos)
            float minX = Float.MAX_VALUE;
            float maxX = Float.MIN_VALUE;
            boolean hayAliensVivos = false;

            for (Alien alien : aliens) {
                if (alien.alive) {
                    hayAliensVivos = true;
                    if (alien.posicion.x < minX) minX = alien.posicion.x;
                    if (alien.posicion.x > maxX) maxX = alien.posicion.x;
                }
            }

            if (hayAliensVivos) {
                // 2. Comprobar si el bloque toca el borde
                float screenRight = Gdx.graphics.getWidth();
                float alienWidth = aliens[0].sprite.getWidth();

                if (alienMoveDirection == 1.0f && (maxX + alienWidth) >= screenRight) {
                    alienMoveDirection = -1.0f; // Cambia a izquierda
                    mustDrop = true;
                } else if (alienMoveDirection == -1.0f && minX <= 0) {
                    alienMoveDirection = 1.0f; // Cambia a derecha
                    mustDrop = true;
                }

                // 3. Aplicar el movimiento y el descenso a todos los aliens vivos
                for (Alien alien : aliens) {
                    if (alien.alive) {
                        // Mueve lateralmente
                        float movimiento = alienHorizontalSpeed * alienMoveDirection * moveInterval;
                        alien.posicion.x += movimiento;
                        // Si toca borde, baja
                        if (mustDrop) {
                            alien.posicion.y -= alienVerticalStep;
                        }
                        // Asegurarse de que el sprite se actualiza para la colisión
                        alien.sprite.setPosition(alien.posicion.x, alien.posicion.y);
                    }
                }
            }
            timeSinceLastMove = 0.0f; // Reinicia el temporizador
        }
    }

    public void Dibujar(SpriteBatch batch) {
        // Llama al método Dibujar de cada alien
        for (Alien alien : aliens) {
            alien.Dibujar(batch);
        }
    }
}

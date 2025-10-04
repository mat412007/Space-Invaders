package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienManager {
    private Alien[] aliens;
    private float alienMoveDirection = 1.0f;
    private final float alienHorizontalSpeed = 100.0f;

    // Variables para el descenso fluido
    private final float alienTotalDropDistance = 25.0f;
    private final float alienVerticalSpeed = 50.0f;
    private float currentDropDistance = 0.0f; // Inicialmente 0.0f (sin descenso activo)

    public AlienManager(int alto, int ancho, int espacio, Texture alien_img) {
        aliens = new Alien[ancho * alto];
        Alien.llenar(alto, ancho, aliens, espacio, alien_img);
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public void ActualizarMovimiento(float deltaTime) {

        // --- 0. LÓGICA DE DESCENSO FLUIDO (Prioridad) ---
        // Si ya hay una distancia de descenso pendiente, aplicarla primero
        if (currentDropDistance > 0.0f && currentDropDistance < alienTotalDropDistance) {

            float descensoEnEsteFrame = alienVerticalSpeed * deltaTime;

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
        boolean hayAliensVivos = false;
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

        if (alienMoveDirection == 1.0f && (maxX + alienWidth) >= screenRight) {
            alienMoveDirection = -1.0f; // Cambia a izquierda
            tocaBorde = true;
        } else if (alienMoveDirection == -1.0f && minX <= 0) {
            alienMoveDirection = 1.0f; // Cambia a derecha
            tocaBorde = true;
        }

        // 3. Aplicar movimiento
        if (tocaBorde) {
            // SI toca el borde, INICIAMOS el descenso, y el movimiento horizontal
            // se detendrá automáticamente en el siguiente frame debido al 'return'
            currentDropDistance = 0.001f;
        }

        // Si tocaBorde es false, o si el descenso acaba de terminar (currentDropDistance==0.0f),
        // APLICAMOS MOVIMIENTO HORIZONTAL
        if (!tocaBorde) {
            float movimientoHorizontal = alienHorizontalSpeed * alienMoveDirection * deltaTime;

            for (Alien alien : aliens) {
                if (alien.alive) {
                    alien.posicion.x += movimientoHorizontal;
                    alien.sprite.setPosition(alien.posicion.x, alien.posicion.y);
                }
            }
        }
    }

    public void Dibujar(SpriteBatch batch) {
        for (Alien alien : aliens) {
            alien.Dibujar(batch);
        }
    }
}

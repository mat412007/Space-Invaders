package com.space_invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// Reemplaza 'MyGame' con el nombre de tu clase principal que extiende Game.
public class MenuScreen implements Screen {

    private final MyGame game;
    private Stage stage;
    private Skin skin;
    private Texture alienTexture;
    private SpriteBatch batch;

    // Asume que tienes una clase para la pantalla de juego, por ejemplo:
    // private final GameScreen gameScreen;

    public MenuScreen(final MyGame game) {
        this.game = game;
        this.batch = game.getBatch(); // O usa tu propio SpriteBatch si no lo pasas por el Game

        // 1. Cargar recursos
        // Carga el Skin para los botones (asume que está en assets/skin/uiskin.json)
        // Puedes usar el 'glassy-ui.json' o cualquier skin que te guste.
        try {
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        } catch (Exception e) {
            Gdx.app.error("MainMenuScreen", "Error al cargar el Skin, ¿está en assets/skin/uiskin.json?", e);
            // Si el skin falla, puedes intentar crear un skin básico con código
            // Pero es muy recomendable usar un skin con JSON.
            skin = new Skin(); // Fallback
        }

        // Carga la textura del alien que subiste
        alienTexture = new Texture(Gdx.files.internal("alien_2.png"));

        // 2. Configurar Stage
        stage = new Stage(new ScreenViewport());

        // El stage necesita recibir los eventos de entrada (taps, clicks)
        Gdx.input.setInputProcessor(stage);

        // 3. Crear Table para el layout
        // Una Table nos ayuda a centrar y alinear los widgets fácilmente.
        Table table = new Table();
        table.setFillParent(true); // Hace que la tabla ocupe toda la pantalla
        // table.setDebug(true); // Descomenta para ver los límites de la tabla

        // --- Título del juego ---
        Label titleLabel = new Label("S P A C E  I N V A D E R S", skin, "title", Color.GREEN); // Usa un estilo de fuente 'title' si tu skin lo tiene
        if (!skin.has("title", Label.LabelStyle.class)) {
            // Fallback si el skin no tiene el estilo 'title'
            titleLabel = new Label("S P A C E  I N V A D E R S", skin);
        }
        table.add(titleLabel).padBottom(50).row();

        // --- Imagen del alien ---
        Image alienImage = new Image(alienTexture);
        table.add(alienImage).size(128, 128).padBottom(30).row(); // Ajusta el tamaño (width, height) si es necesario

        // --- Botones ---

        // Botón JUGAR
        TextButton playButton = new TextButton("JUGAR", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Lógica para cambiar a la pantalla de juego
                // Asume que tienes una GameScreen
                game.setScreen(new GameScreen(game));
                dispose(); // Limpia los recursos de esta pantalla
            }
        });
        table.add(playButton).width(200).height(50).padBottom(10).row();

        // Botón OPCIONES
        TextButton optionsButton = new TextButton("OPCIONES", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Aquí iría la lógica para cambiar a una pantalla de Opciones
                Gdx.app.log("Menu", "Botón Opciones clickeado");
                // game.setScreen(new OptionsScreen(game));
            }
        });
        table.add(optionsButton).width(200).height(50).padBottom(10).row();

        // Botón SALIR
        TextButton exitButton = new TextButton("SALIR", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Cierra la aplicación
            }
        });
        table.add(exitButton).width(200).height(50).padBottom(10).row();

        // 4. Agregar la Table al Stage
        stage.addActor(table);
    }

    // Implementación de la Interfaz Screen

    @Override
    public void show() {
        // Se llama cuando esta pantalla es mostrada como la actual.
        // Ya lo hicimos en el constructor, pero es la forma estándar:
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con un color oscuro para el ambiente Space Invaders
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar y dibujar el Stage (que contiene la Table y los botones)
        stage.act(delta);
        stage.draw();

        // Opcional: Si quieres un fondo con estrellas/partículas, lo dibujarías aquí
        // usando tu SpriteBatch antes de stage.draw(), o en un Actor dentro del Stage.
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el Stage y el Viewport cuando la ventana cambia de tamaño
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Se llama cuando la pantalla deja de ser la actual
        Gdx.input.setInputProcessor(null); // Quita el InputProcessor
    }

    @Override
    public void dispose() {
        // Libera los recursos que *solo* usa esta pantalla.
        stage.dispose();
        skin.dispose();
        alienTexture.dispose();
        // Si tu SpriteBatch es global (en MyGame), no lo descartes aquí.
    }
}

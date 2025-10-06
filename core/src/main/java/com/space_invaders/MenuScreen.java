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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private final MyGame game;
    private final Texture imagenFondo;
    private Skin skin;
    private Stage stage;
    private Table table;
    private final SpriteBatch batch;

    public MenuScreen(final MyGame game) {
        this.game = game;
        this.batch = game.getBatch(); // Hereda el SpriteBatch de MyGame
        imagenFondo = new Texture("FondoMenu.png");
    }

    @Override
    public void show() {
        // 1. Cargar la Skin (apariencia de los botones)
        // ¡Asegúrate de que "uiskin.json" exista en tu carpeta assets!
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // 2. Inicializar el Stage
        // Al no especificar un Viewport, LibGDX usa uno por defecto que se actualiza con la pantalla.
        stage = new Stage();

        // 3. Establecer el Stage como el procesador de entrada
        // ¡Esto es obligatorio para que los botones funcionen!
        Gdx.input.setInputProcessor(stage);

        // 4. Crear la Table y centrarla
        table = new Table(skin); // Si la Skin tiene estilos para Table, úsala aquí.
        table.setFillParent(true); // Hace que la Table ocupe todo el Stage (y, por lo tanto, toda la pantalla)
        stage.addActor(table);

        // 5. Crear y añadir los botones
        crearBotones();
    }

    private void crearBotones() {
        // 1. Crear los TextButton
        TextButton botonJugar = new TextButton("JUGAR", skin);
        TextButton botonOpciones = new TextButton("OPCIONES", skin);
        TextButton botonSalir = new TextButton("SALIR", skin);

        // 2. Añadir los botones a la Table, uno debajo del otro
        // El método .row() es clave para la disposición vertical.

        float anchoBoton = 300f; // Ancho fijo en píxeles
        float altoBoton = 80f; // Alto fijo en píxeles

        table.add(botonJugar).width(anchoBoton).height(altoBoton).pad(15);
        table.row(); // Siguiente fila

        table.add(botonOpciones).width(anchoBoton).height(altoBoton).pad(15);
        table.row(); // Siguiente fila

        table.add(botonSalir).width(anchoBoton).height(altoBoton).pad(15);

        // 3. Añadir Listeners para las acciones

        botonJugar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Acción: Navegar a la pantalla de juego
                // game.setScreen(new GameScreen(game));
                Gdx.app.log("Menu", "JUGAR presionado");
            }
        });

        botonSalir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Cierra la aplicación
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        batch.begin();
        batch.draw(imagenFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Dibujar los botones
        if (stage != null) {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el Stage para que el Table se recalcule y se mantenga centrado.
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        imagenFondo.dispose();

        // 2. Liberar recursos que se inicializarán más adelante (verificando que no sean null)
        if (stage != null) {
            stage.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
        // El 'batch' NO se libera porque pertenece a la clase MyGame (global).
    }
}

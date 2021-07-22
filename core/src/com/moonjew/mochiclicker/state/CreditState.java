package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.moonjew.mochiclicker.MochiClicker;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class CreditState extends State{
    Texture img;
    Stage stage;

    protected CreditState(GameStateManager gsm) {
        super(gsm);
        img = new Texture("menubg.png");
        Skin skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        BitmapFont font = new BitmapFont(Gdx.files.internal("font1.fnt"));
        stage = new Stage(new ScreenViewport());
        stage.getViewport().setCamera(cam);
//        stage.getViewport().update(640,480, true);

        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.setRoot(root);

        Button button = new Button(skin);
        button.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leaveCredits();
            }
        });
        Label label = new Label("Credits", skin);
        label.setFontScale(1);
        label.getStyle().font = font;
        label.setStyle(label.getStyle());
        root.add(label).top().colspan(3).growY();

        root.row();

        Table table = new Table();
        table.defaults().space(10);

        label = new Label("Art:", skin);
        label.getStyle().font = font;
        table.add(label);

        table.row();
        table.row();

        label = new Label("Gameplay:", skin);
        label.getStyle().font = font;
        table.add(label);

        root.add(table).left();

        table = new Table();
        table.defaults().space(10);

        label = new Label("CeaShell", skin);
        label.getStyle().font = font;
        table.add(label);

        table.row();
        table.row();

        label = new Label("MoonJew \n & CeaShell", skin);
        label.getStyle().font = font;
        table.add(label);

        root.add(table).right();

    }
    public void leaveCredits(){
        gsm.pop();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
//        handleInput();
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        stage.getViewport().apply();
        sb.begin();
        sb.draw(img, 0, 0, MochiClicker.WIDTH, MochiClicker.HEIGHT);
//        FONT.drawMiddle(sb, "Credits", 0,150, MochiClicker.WIDTH, MochiClicker.HEIGHT, 4, 4);
//        FONT.draw(sb, "Art       CeaShell", new Rectangle(50, MochiClicker.HEIGHT/2, MochiClicker.WIDTH, MochiClicker.HEIGHT), 4, 4);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}

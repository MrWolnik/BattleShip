package com.almasb.battleship;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class  GameMenu extends Application {

    public GameMenuPriv gameMenuPriv;

    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(863, 486);

        InputStream is = Files.newInputStream(Paths.get("images/logo.jpg"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView(img);

        gameMenuPriv = new GameMenuPriv();

        root.getChildren().addAll(imgView, gameMenuPriv);

        Scene scene = new Scene(root);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class GameMenuPriv extends Parent {


        public GameMenuPriv() {
            VBox menu0 = new VBox(10);  //glowne menu
            VBox menu1 = new VBox(10);  //menu ustawien

            menu0.setTranslateX(100);
            menu0.setTranslateY(100);

            menu1.setTranslateX(100);
            menu1.setTranslateY(100);

            final int offset = 400;

            menu1.setTranslateX(offset);

            MenuButton btnNew = new MenuButton("New Game");
            btnNew.setOnMouseClicked(event -> {
            });
            //settings button
            MenuButton btnSettings = new MenuButton("Settings");
            btnSettings.setOnMouseClicked(event -> {
                getChildren().add(menu1);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());
                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });
            //exit button
            MenuButton btnExit = new MenuButton("Exit");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("Back");
            btnBack.setOnMouseClicked(enevt -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(ext -> {
                    getChildren().remove(menu1);
                });
            });

            menu0.getChildren().addAll(btnNew, btnSettings, btnExit);
            menu1.getChildren().addAll(btnBack);

            Rectangle bg = new Rectangle(863, 486);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);

            getChildren().addAll(bg, menu0);
        }
    }

    private static class MenuButton extends StackPane {

        private Text text;

        public MenuButton(String name) {
            //modyfikacja tekstu
            text = new Text(name);
            text.setFont(this.text.getFont().font(20));
            text.setFill(Color.WHITE);
            //modyfikacja przycisku
            Rectangle bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            GaussianBlur blur = new GaussianBlur(3.5);
            bg.setEffect(blur);
            //miejsce tekstu
            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(bg, text);
            //dzialanie na reakcje najechania myszka
            setOnMouseEntered(event -> {
                bg.setTranslateX(5);
                text.setTranslateX(5);
                //zamiana barw przycisku po najechaniu myszką
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });
            //działąnie na reakcję opuszczenia myszki z pola
            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow drop = new DropShadow(30, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));


        }
    }

    public static void main(String[] args){
    launch(args);
}

    }


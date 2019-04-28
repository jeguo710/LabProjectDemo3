package astroid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ast extends Application {
    private Pane root;
    private List<game> bullets = new ArrayList<>();
    private List<game> enemies = new ArrayList<>();
    private game player;

    private Parent createContent(){
        root = new Pane();
        root.setPrefSize(600,600);
        player = new player();
        player.setVelocity(new Point2D(1,0));
        addgame(player,300 ,300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;

    }
    private void addBullet(game bullet,double x, double y ){
        bullets.add(bullet);
        addgame(bullet,x,y);
    }

    private void addgame(game object, double x, double y){
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }
    private void addEnemy(game enemy, double x, double y){
        enemies.add(enemy);
        addgame(enemy,x,y);
    }

    private void onUpdate(){
        for(game bullet:bullets){
            for (game enemy : enemies){
                if(bullet.isColliding(enemy)){
                    bullet.setAlive(false);
                    enemy.setAlive(false);
                    root.getChildren().removeAll(bullet.getView(),enemy.getView());
                }
            }
        }

        for(game enemy: enemies){
            if(enemy.isColliding(player)){
                player.setAlive(false);
                player.isDead();
                root.getChildren().removeAll(player.getView());
            }
        }
        bullets.removeIf(game::isDead);
        enemies.removeIf(game::isDead);
        bullets.forEach(game::update);
        enemies.forEach(game::update);
        player.update();
        if(Math.random()<0.02){
            addEnemy(new Enemy(),Math.random()*root.getPrefWidth(),Math.random()*root.getPrefHeight());
        }
    }

    private static class player extends game {
        player(){
            super(new Rectangle(40,20, Color.BLUE));

        }
    }
    private static class Enemy extends game {
        Enemy(){
            super(new Circle(15,15,15, Color.RED));

        }
    }
    private static class Bullet extends game {
        Bullet(){
            super(new Circle(5,5,5, Color.BLACK));

        }
    }


    @Override
    public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e ->{
            if(e.getCode()== KeyCode.LEFT){
                player.rotateLeft();
            }
            else if(e.getCode()== KeyCode.RIGHT){
                player.rotateRight();
            }
            else if(e.getCode() == KeyCode.SPACE){
                Bullet bullet = new Bullet();
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                addBullet(bullet,player.getView().getTranslateX(),player.getView().getTranslateY());
            }
        });
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}

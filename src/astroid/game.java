package astroid;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public class game {
    private Node view;
    private Point2D velocity = new Point2D(0,0);
    private  boolean alive = true;
    public game (Node view){
        this.view = view;
    }
    public double getRotate(){
        return view.getRotate();
    }
    public void update(){
        view.setTranslateX(view.getTranslateX()+velocity.getX());
        view.setTranslateY(view.getTranslateY()+velocity.getY());

    }
    public void setVelocity(Point2D velocity){
        this.velocity = velocity;
    }
    public Point2D getVelocity(){
        return velocity;
    }
    public Node getView(){
        return view;
    }
    public boolean isAlive(){
        return alive;
    }
    public boolean isDead(){
        return !alive;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public void rotateRight(){
        view.setRotate(view.getRotate()+20);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate()))));

    }
    public void rotateLeft(){
        view.setRotate(view.getRotate()-20);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate()))));
    }
    public boolean isColliding(game other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());

    }



}

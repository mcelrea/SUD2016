package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    Player player = new Player("McElrea");
    public static final int CELLSIZE = 20;
    ArrayList<String> input = new ArrayList<String>();
    Room room1 = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-10.txt");
    public static final int OFFSET = 40;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SUD Alpha 0.0.0.5");
        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(800,600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Font myFont = Font.font("Courier New", FontWeight.BOLD, 24);
        gc.setFont(myFont);

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                if(!input.contains(code)) {
                    input.add(code);//add it to the list
                }
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println(input);
                processInput();
                //backdrop
                gc.setFill(Color.WHEAT);
                gc.fillRect(0,0,800,600);

                //map
                room1.draw(gc);

                //character
                gc.setFill(Color.BLACK);
                gc.fillText("@", OFFSET+player.getCol()*CELLSIZE, OFFSET+player.getRow()*CELLSIZE);
            }
        }.start();

        //last line
        primaryStage.show();
    }

    private void processInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            //if the input is equal to W
            if(input.get(i).equals("W")) {
                player.moveUp(room1);
                //remove W from list
                input.remove(i);
                i--;
            }
            //if the input is equal to S
            else if(input.get(i).equals("S")) {
                player.moveDown(room1);
                //remove S from list
                input.remove(i);
                i--;
            }
            //if the input is equal to A
            else if(input.get(i).equals("A")) {
                player.moveLeft(room1);
                //remove A from list
                input.remove(i);
                i--;
            }
            //if the input is equal to D
            else if(input.get(i).equals("D")) {
                player.moveRight(room1);
                //remove D from list
                input.remove(i);
                i--;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

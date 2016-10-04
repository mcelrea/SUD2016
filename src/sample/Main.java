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
    World world = new World();
    public static final int OFFSET = 40;
    public static final int MAP=1, FIGHT=2;
    public static int gameState = MAP;
    Enemy currentEnemy = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SUD Alpha 0.0.0.6");
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
                if(gameState == MAP) {
                    //System.out.println(input);
                    processInput();
                    //backdrop
                    gc.setFill(Color.WHEAT);
                    gc.fillRect(0, 0, 800, 600);

                    //map
                    Room currentRoom = world.getRoom(player.getWorldRow(), player.getWorldCol());
                    currentRoom.enemiesAct(world, player);
                    world.drawRoom(player.getWorldRow(), player.getWorldCol(), player, gc);

                    //character
                    player.draw(gc);

                    //check for enemy collision
                    if(currentRoom.getEnemyCollision(player) != null) {
                        currentEnemy = currentRoom.getEnemyCollision(player);
                        gameState = FIGHT;
                    }
                }
                else if(gameState == FIGHT) {
                    //backdrop
                    gc.setFill(Color.BROWN);
                    gc.fillRect(0, 0, 800, 600);

                    drawFightText(gc);

                    processFightInput();
                }
            }
        }.start();

        //last line
        primaryStage.show();
    }

    private void drawFightText(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillText("FIGHT", 350,50);
        gc.fillText(currentEnemy.getName(), 500,100);
        gc.fillText(player.getName(), 150,100);

        //player health bar
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(150,120,100*(player.getHp()/(double)player.getMaxHp()),20);

        //enemy health bar
        gc.setFill(Color.DIMGREY);
        gc.fillRect(500,120,100*(currentEnemy.getHp()/(double)currentEnemy.getMaxHp()),20);
    }


    private void processFightInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            if(input.get(i).equals("L")) {
                gameState = MAP;
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("O")) {
                player.setHp(player.getHp()-1);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("P")) {
                currentEnemy.setHp(currentEnemy.getHp()-1);
                input.remove(i);
                i--;
            }
        }
    }

    private void processInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            //if the input is equal to W
            if(input.get(i).equals("W")) {
                player.moveUp(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove W from list
                input.remove(i);
                i--;
            }
            //if the input is equal to S
            else if(input.get(i).equals("S")) {
                player.moveDown(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove S from list
                input.remove(i);
                i--;
            }
            //if the input is equal to A
            else if(input.get(i).equals("A")) {
                player.moveLeft(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove A from list
                input.remove(i);
                i--;
            }
            //if the input is equal to D
            else if(input.get(i).equals("D")) {
                player.moveRight(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove D from list
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("E")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).pickUpItem(player);
                //remove E from list
                input.remove(i);
                i--;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Main extends Application {

    Player player = new Player("McElrea");
    public static final int CELLSIZE = 20;
    ArrayList<String> input = new ArrayList<String>();
    World world = new World();
    public static final int OFFSET = 40;
    public static final int MAP=1, FIGHT=2, PLAYERTURN=3, ENEMYTURN=4, INTRO=5, GAMEOVER=6, STORE=7;
    public static int gameState = INTRO;
    int introPage = 1;
    public static int turn = PLAYERTURN;
    Enemy currentEnemy = null;
    public static String combatText1 = "COMBAT TEXT";
    static Color combatText1Color = Color.WHITE;
    public static String combatText2 = "COMBAT TEXT";
    static Color combatText2Color = Color.WHITE;
    public static String combatText3 = "COMBAT TEXT";
    static Color combatText3Color = Color.WHITE;
    public static String combatText4 = "COMBAT TEXT";
    static Color combatText4Color = Color.WHITE;
    public static String combatText5 = "COMBAT TEXT";
    static Color combatText5Color = Color.WHITE;
    Image titleImage;
    Image gameOverImage;

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

        //load my image files
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\titleScreen.jpg");
        titleImage = new Image(new FileInputStream(file));
        file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\endScreen.png");
        gameOverImage = new Image(new FileInputStream(file));

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

                    //draw the combat text
                    gc.setFill(combatText1Color);
                    gc.fillText(combatText1,10,450);
                    gc.setFill(combatText2Color);
                    gc.fillText(combatText2,10,475);
                    gc.setFill(combatText3Color);
                    gc.fillText(combatText3,10,500);
                    gc.setFill(combatText4Color);
                    gc.fillText(combatText4,10,525);
                    gc.setFill(combatText5Color);
                    gc.fillText(combatText5,10,550);

                    //check for enemy collision
                    if(currentRoom.getEnemyCollision(player) != null) {
                        currentEnemy = currentRoom.getEnemyCollision(player);
                        gameState = FIGHT;
                    }

                    //check for store collision
                    if(currentRoom.getStore(player) != null) {
                        gameState = STORE;
                    }
                }
                else if(gameState == FIGHT) {
                    //backdrop
                    gc.setFill(Color.BROWN);
                    gc.fillRect(0, 0, 800, 600);

                    player.drawAbilities(gc);
                    drawFightText(gc);

                    if(turn == PLAYERTURN) {
                        processFightInput();
                    }
                    else {//else its the enemies turn
                        currentEnemy.attack(player);
                        if(player.getHp() <= 0) {
                            gameState = GAMEOVER;
                        }
                    }

                    if(currentEnemy.getHp() <= 0) {
                        player.setXp(player.getXp()+currentEnemy.getXp());
                        gameState = MAP;
                        Room currentRoom = world.getRoom(player.getWorldRow(), player.getWorldCol());
                        currentRoom.removeEnemy(currentEnemy); //remove enemy from game
                        currentEnemy = null; //there is no longer a current enemy
                    }

                }
                else if(gameState == INTRO) {
                    drawIntro(gc);
                    processIntroInput();

                    gc.setFill(Color.WHITE);
                    if(introPage == 1) {
                        gc.drawImage(titleImage,0,0);
                    }
                    else if(introPage == 2) {
                        gc.fillText("Page 2", 400,300);
                    }
                    else if(introPage == 3) {
                        gc.fillText("Page 3", 400,300);
                    }
                    else if(introPage == 4) {
                        gc.fillText("Page 4", 400,300);
                    }
                }
                else if(gameState == GAMEOVER) {
                    gc.drawImage(gameOverImage,0,0);
                    processGameOverInput();
                }
                else if(gameState == STORE) {
                    Room currentRoom = world.getRoom(player.getWorldRow(),player.getWorldCol());
                    currentRoom.store.draw(gc);
                    processStoreInput();

                    //draw the combat text
                    gc.setFill(combatText1Color);
                    gc.fillText(combatText1,10,450);
                    gc.setFill(combatText2Color);
                    gc.fillText(combatText2,10,475);
                    gc.setFill(combatText3Color);
                    gc.fillText(combatText3,10,500);
                    gc.setFill(combatText4Color);
                    gc.fillText(combatText4,10,525);
                    gc.setFill(combatText5Color);
                    gc.fillText(combatText5,10,550);
                }
            }
        }.start();

        //last line
        primaryStage.show();
    }

    private void processStoreInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            if (input.get(i).equals("SPACE")) {
                player.setRow(player.getRow()+1);
                gameState = MAP;
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT1")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).store.purchaseAbility(player,1);
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT2")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).store.purchaseAbility(player,2);
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT3")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).store.purchaseAbility(player,3);
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT4")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).store.purchaseAbility(player,4);
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT5")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).store.purchaseAbility(player,5);
                input.remove(i);
                i--;
            }
        }
    }

    private void processGameOverInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            if (input.get(i).equals("SPACE")) {
                player = new Player(player.getName());//restart player
                world = new World();//reset world
                gameState = INTRO;
                introPage = 1;
                input.remove(i);
                i--;
            }
        }
    }

    private void processIntroInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            if (input.get(i).equals("SPACE")) {
                introPage++;
                if(introPage > 4) {
                    gameState = MAP;
                }
                input.remove(i);
                i--;
            }
        }
    }

    private void drawIntro(GraphicsContext gc) {
        //backdrop
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,800,600);
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

        //draw the combat text
        gc.setFill(combatText1Color);
        gc.fillText(combatText1,10,450);
        gc.setFill(combatText2Color);
        gc.fillText(combatText2,10,475);
        gc.setFill(combatText3Color);
        gc.fillText(combatText3,10,500);
        gc.setFill(combatText4Color);
        gc.fillText(combatText4,10,525);
        gc.setFill(combatText5Color);
        gc.fillText(combatText5,10,550);
    }

    public static void addCombatText(String text, Color color) {
        System.out.println("Got new text: " + text);

        //push all text down one
        combatText5 = combatText4;
        combatText5Color = combatText4Color;
        combatText4 = combatText3;
        combatText4Color = combatText3Color;
        combatText3 = combatText2;
        combatText3Color = combatText2Color;
        combatText2 = combatText1;
        combatText2Color = combatText1Color;

        //add new text
        combatText1 = text;
        combatText1Color = color;
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
            else if(input.get(i).equals("DIGIT1")) {
                player.useAbility(1,currentEnemy);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("DIGIT2")) {
                player.useAbility(2,currentEnemy);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("DIGIT3")) {
                player.useAbility(3,currentEnemy);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("DIGIT4")) {
                player.useAbility(4,currentEnemy);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("DIGIT5")) {
                player.useAbility(5,currentEnemy);
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("DIGIT6")) {
                player.useAbility(6,currentEnemy);
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

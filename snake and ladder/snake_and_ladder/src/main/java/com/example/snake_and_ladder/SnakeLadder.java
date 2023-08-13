package com.example.snake_and_ladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int tileSize = 40, width = 10, height = 10;
    public static final int buttonLine = height*tileSize+50, infoLine = buttonLine-30;
    public static Dice dice = new Dice();
    private Player playerOne, playerTwo;
    private boolean gameStarted = false, playerOneTurn = false, playerTwoTurn = false;
    private Pane createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(tileSize*width,height*tileSize + 100);
        for(int i = 0 ; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image("C:\\Users\\ADMIN\\Desktop\\snake and ladder\\snake_and_ladder\\src\\main\\img.png");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);
        root.getChildren().add(board);

        //buttons
        Button playerOneButton = new Button("Player__1");
        Button playerTwoButton = new Button("Player__2");
        Button startButton = new Button("Start");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(50);
        playerOneButton.setDisable(true);
        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(190);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(300);
        playerTwoButton.setDisable(true);

        // info display
        Label playerOneLable = new Label("");
        Label playerTwoLable = new Label("");
        Label diceLable = new Label("Start the Game");

        playerOneLable.setTranslateX(50);
        playerOneLable.setTranslateY(infoLine);
        playerTwoLable.setTranslateY(infoLine);
        playerTwoLable.setTranslateX(300);
        diceLable.setTranslateX(190);
        diceLable.setTranslateY(infoLine);

        playerOne = new Player(tileSize, Color.BLACK, "Amit");
        playerTwo = new Player(tileSize-5, Color.WHITE,"Sumit");

        //player action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted) {
                    if (playerOneTurn) {
                        int diceValue = dice.getRollerDiceValue();
                        diceLable.setText("Dice Value :" + diceValue);
                        playerOne.movePlayer(diceValue);

                        if(playerOne.isWinner())
                        {
                            diceLable.setText("Winner is " + playerOne.getName());
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");
                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted = false;
                        }
                        else
                        {
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playerTwoLable.setText("Your Trun "+ playerTwo.getName());
                        }
                    }
                }
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted) {
                    if (playerTwoTurn) {
                        int diceValue = dice.getRollerDiceValue();
                        diceLable.setText("Dice Value :" + diceValue);
                        playerTwo.movePlayer(diceValue);

                        if(playerTwo.isWinner())
                        {
                            diceLable.setText("Winner is " + playerTwo.getName());
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");
                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                        }
                        else
                        {
                            playerOneTurn = true;
                            playerOneButton.setDisable(false);
                            playerOneLable.setText("Your Trun "+ playerOne.getName());

                            playerTwoTurn = false;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");
                        }

                    }
                }
            }
        });
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                diceLable.setText("Game Started");
                startButton.setDisable(true);
                playerOneTurn = true;
                playerOneLable.setText("Your Turn " + playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPosition();
                playerTwoTurn = false;
                playerTwoLable.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });
        root.getChildren().addAll(playerOneButton,startButton,playerTwoButton,playerOneLable,playerTwoLable,diceLable,playerOne.getCoin(),playerTwo.getCoin());
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake And Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
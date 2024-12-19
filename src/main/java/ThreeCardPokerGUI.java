import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Region;

public class ThreeCardPokerGUI extends Application {
	private Stage primaryStage;
	private Scene startScene;
    private Scene gameScene;

	private AudioClip cardFlipSound;
    private GameController gameController;
    private Label walletLabel;
    private TextField walletField;
    private TextField anteField;
    private TextField pairPlusField;
    private Button playButton;
    private Button continueButton;
    private Button foldButton;
    private Button newLookButton;
    private Button freshStartButton;
    private StackPane deck;
    private Button exitButton;
    private VBox resultDisplay;
    private boolean isNewLookEnabled = false;
    private VBox sidebar;
    private StackPane mainPanel;
    
    private HBox dealerHandImages = new HBox(10);
    private HBox playerHandImages = new HBox(10);

	@Override
	public void start(Stage primaryStage) {
	    this.primaryStage = primaryStage; // Store primaryStage reference
	    createStartScreen();

	    primaryStage.setTitle("3 Card Poker");
	    primaryStage.setScene(startScene);
	    primaryStage.show();
	}
	
	// Create the start screen layout and buttons
	private void createStartScreen() {
	    double itemWidth = 320;

	    Label titleLabel = new Label("3 Card Poker");
	    titleLabel.getStyleClass().add("start-title");
	    titleLabel.setStyle("-fx-font-size: 32px;");
	    titleLabel.setPrefWidth(itemWidth);  
	    titleLabel.setAlignment(Pos.CENTER); 

	    Button singlePlayerButton = new Button("Single Player");
	    singlePlayerButton.getStyleClass().add("play-btn");
	    singlePlayerButton.setPrefWidth(itemWidth); 
	    singlePlayerButton.setOnAction(e -> startGame());

	    Button twoPlayersButton = new Button("Two Players");
	    twoPlayersButton.getStyleClass().add("play-btn");
	    twoPlayersButton.setPrefWidth(itemWidth); 
	    twoPlayersButton.setOnAction(e -> showUnderMaintenanceScreen());

	    Button exitButton = new Button("Exit");
	    exitButton.getStyleClass().add("btn");
	    exitButton.setPrefWidth(itemWidth); 
	    exitButton.setOnAction(e -> primaryStage.close());

	    VBox layout = new VBox(15, titleLabel, singlePlayerButton, twoPlayersButton, exitButton);
	    layout.setAlignment(Pos.CENTER);
	    layout.setPadding(new Insets(20));
	    layout.setStyle("-fx-background-color: #213743;");

	    StackPane startPane = new StackPane(layout);
	    startPane.setPrefSize(1000, 600);

	    StackPane.setAlignment(layout, Pos.CENTER);

	    startScene = new Scene(startPane);
	    startScene.getStylesheets().add(getClass().getResource("/styles/appStyle.css").toExternalForm());
	}

	// Create the "Under Maintenance" scene
	private Scene createUnderMaintenanceScene() {
	    Label titleLabel = new Label("Two Players Under Maintenance");
	    titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
	    titleLabel.setAlignment(Pos.CENTER);

	    Label messageLabel = new Label("Didn't complete Two Player feature as I spent too much time working on the UI for this project.");
	    messageLabel.setWrapText(true);
	    messageLabel.setTextAlignment(TextAlignment.CENTER);
	    messageLabel.setAlignment(Pos.CENTER);
	    messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

	    Image sadFaceImage = new Image(getClass().getResource("crying-face.png").toExternalForm());
	    ImageView sadFaceImageView = new ImageView(sadFaceImage);
	    sadFaceImageView.setFitWidth(100);
	    sadFaceImageView.setPreserveRatio(true);

	    Button backButton = new Button("Back to Start");
	    backButton.getStyleClass().add("play-btn");
	    backButton.setOnAction(e -> primaryStage.setScene(startScene));
	    backButton.setPrefWidth(200);

	    VBox layout = new VBox(20, titleLabel, messageLabel, sadFaceImageView, backButton);
	    layout.setAlignment(Pos.CENTER);
	    layout.setPadding(new Insets(20));
	    layout.setStyle("-fx-background-color: #213743;");

	    Scene underMaintenanceScene = new Scene(layout, 1000, 600);
	    underMaintenanceScene.getStylesheets().add(getClass().getResource("/styles/appStyle.css").toExternalForm());

	    return underMaintenanceScene;
	}

	// Show "Under Maintenance" screen
	private void showUnderMaintenanceScreen() {
	    primaryStage.setScene(createUnderMaintenanceScene());
	}

    // Transition to the main game screen
    private void startGame() {
        if (gameScene == null) {
            gameScene = createGameScene();
        }
        primaryStage.setScene(gameScene);
    }

    // Set up the main game layout
    private Scene createGameScene() {
        gameController = new GameController();

        deck = createDeck();
        sidebar = createSidebar();
        StackPane cardPanel = createCardPanel();

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(cardPanel);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/appStyle.css").toExternalForm());

        walletField.setText("$" + gameController.getPlayer().getFunds());

        return scene;
    }

    private HBox createWalletBox() {
        walletLabel = new Label("Wallet");
        walletLabel.getStyleClass().add("label");

        walletField = new TextField("$" + gameController.getPlayer().getFunds());
        walletField.setEditable(false);
        walletField.setFocusTraversable(false);
        walletField.getStyleClass().add("text-field");

        HBox walletBox = new HBox(24);
        walletBox.getChildren().addAll(walletLabel, walletField);
        walletBox.setStyle("-fx-alignment: center-left; -fx-padding: 0;");

        return walletBox;
    }
    
    private VBox createAnteBox() {
        Label anteLabel = new Label("Ante");
        anteLabel.getStyleClass().add("label");

        anteField = new TextField("0");
        anteField.setEditable(true);
        anteField.setFocusTraversable(true);
        anteField.getStyleClass().add("text-field");

        VBox anteBox = new VBox(5);
        anteBox.getChildren().addAll(anteLabel, anteField);
        anteBox.setStyle("-fx-padding: 0;");

        return anteBox;
    }

    private VBox createPairPlusBox() {
        Label pairPlusLabel = new Label("Pair Plus");
        pairPlusLabel.getStyleClass().add("label");

        pairPlusField = new TextField("0");
        pairPlusField.setEditable(true);
        pairPlusField.setFocusTraversable(true);
        pairPlusField.getStyleClass().add("text-field");

        VBox pairPlusBox = new VBox(5);
        pairPlusBox.getChildren().addAll(pairPlusLabel, pairPlusField);
        pairPlusBox.setStyle("-fx-padding: 0;");

        return pairPlusBox;
    }

    private void revealDealerCards() {
        dealerHandImages.getChildren().clear(); // Clear previous display

        for (Card card : gameController.getDealer().getDealersHand()) {
            String cardImagePath = "/images/" + card.toString() + ".png";
            
            ImageView cardFaceImageView = new ImageView(new Image(getClass().getResource(cardImagePath).toExternalForm()));
            cardFaceImageView.setFitWidth(90);
            cardFaceImageView.setPreserveRatio(true);

            dealerHandImages.getChildren().add(cardFaceImageView);
        }
    }
    
    private void handleContinueAction() {
        continueButton.setDisable(true);
        foldButton.setDisable(true);

        gameController.playerDecision(true);
        gameController.evaluateHands();

        revealDealerCards(); // Show dealer’s cards immediately

        walletField.setText("$" + gameController.getPlayer().getFunds());

        String playerOutcome = gameController.getPlayerHandOutcome();
        String dealerOutcome = gameController.getDealerHandOutcome();
        int winnings = gameController.getNetChange();
        int gameResult = gameController.getGameResult();

        // Determine result message based on game outcome
        String resultMessage;
        String winningHandOutcome;

        switch (gameResult) {
            case 2: // Player wins
                resultMessage = "You won $" + winnings;
                winningHandOutcome = playerOutcome;
                break;
            case 1: // Dealer wins
                resultMessage = "You lost $" + Math.abs(winnings);
                winningHandOutcome = dealerOutcome;
                break;
            case 0: // Tie
                resultMessage = "Bets Returned";
                winningHandOutcome = "Tie";
                break;
            case 3: // Dealer did not qualify
                resultMessage = "Bets Returned";
                resultMessage += winnings > 0 ? "\nYou won $" + winnings : winnings < 0 ? "\nYou lost $" + Math.abs(winnings) : "";
                winningHandOutcome = "Dealer Did Not Qualify";
                break;
            default:
                resultMessage = "Unknown Result";
                winningHandOutcome = "Unknown";
                break;
        }

        updateResultDisplay(winningHandOutcome, resultMessage, winnings);
        gameController.resetForNextRound();
        playButton.setDisable(false); // Ready for next round
    }
    
    private void handleFoldAction() {
        continueButton.setDisable(true);
        foldButton.setDisable(true);

        gameController.playerDecision(false); // Player folds
        gameController.evaluateHands();
        revealDealerCards();

        walletField.setText("$" + gameController.getPlayer().getFunds());

        int winnings = gameController.getNetChange();
        String resultMessage = "Lost $" + Math.abs(winnings);
        String winningHandOutcome = "Folded";

        updateResultDisplay(winningHandOutcome, resultMessage, winnings);

        gameController.resetForNextRound();
        playButton.setDisable(false); // Ready for next round
    }
    
    private HBox createContinueAndFoldButtons() {
        continueButton = new Button("Continue");
        continueButton.setDisable(true);
        continueButton.setOnAction(e -> handleContinueAction());
        continueButton.setMaxWidth(Double.MAX_VALUE); // Fully expand in HBox
        continueButton.getStyleClass().add("btn");

        foldButton = new Button("Fold");
        foldButton.setDisable(true);
        foldButton.setOnAction(e -> handleFoldAction());
        foldButton.setMaxWidth(Double.MAX_VALUE);
        foldButton.getStyleClass().add("btn");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(continueButton, foldButton);
        buttonBox.setStyle("-fx-padding: 5;");

        HBox.setHgrow(continueButton, Priority.SOMETIMES);
        HBox.setHgrow(foldButton, Priority.SOMETIMES);

        continueButton.setMinWidth(100);
        foldButton.setMinWidth(100);

        continueButton.setMaxWidth(300);
        foldButton.setMaxWidth(300);

        return buttonBox;
    }
    
    private Button createPlayButton() {
        playButton = new Button("Play");
        playButton.getStyleClass().add("play-btn");
        playButton.getStyleClass().add("btn");
        playButton.setOnAction(e -> playRound());  // Play button triggers playRound
        playButton.setMaxWidth(Double.MAX_VALUE);  // Allow button to expand in layout
        
        return playButton;
    }

    private void toggleNewLook() {
        isNewLookEnabled = !isNewLookEnabled;

        if (isNewLookEnabled) {
            sidebar.setStyle("-fx-background-color: #21433E;");
            walletField.setStyle("-fx-background-color: #0F2E2B;");
            anteField.setStyle("-fx-background-color: #0F2E2B;");
            pairPlusField.setStyle("-fx-background-color: #0F2E2B;");
            continueButton.setStyle("-fx-background-color: #284B47;");
            foldButton.setStyle("-fx-background-color: #284B47;");
            newLookButton.setStyle("-fx-background-color: #284B47;");
            freshStartButton.setStyle("-fx-background-color: #284B47;");
            exitButton.setStyle("-fx-background-color: #284B47;");
            mainPanel.setStyle("-fx-background-color: #0F2E2B;");
            resultDisplay.setStyle("-fx-background-color: #21433E;");
        } else {
            sidebar.setStyle("-fx-background-color: #213743;");
            walletField.setStyle("-fx-background-color: #0F212E;");
            anteField.setStyle("-fx-background-color: #0F212E;");
            pairPlusField.setStyle("-fx-background-color: #0F212E;");
            continueButton.setStyle("-fx-background-color: #283E4B;");
            foldButton.setStyle("-fx-background-color: #283E4B;");
            newLookButton.setStyle("-fx-background-color: #283E4B;");
            freshStartButton.setStyle("-fx-background-color: #283E4B;");
            exitButton.setStyle("-fx-background-color: #283E4B;");
            mainPanel.setStyle("-fx-background-color: #0F212E;");
            resultDisplay.setStyle("-fx-background-color: #213743;");
        }

        updateCardBackImage();
    }
    
    private Button createNewLookButton() {
        newLookButton = new Button("New Look");
        newLookButton.getStyleClass().add("btn");
        newLookButton.setMaxWidth(Double.MAX_VALUE);
        newLookButton.setOnAction(e -> toggleNewLook());  // Toggles theme
        return newLookButton;
    }

    private Button createFreshStartButton() {
        freshStartButton = new Button("Fresh Start");
        freshStartButton.getStyleClass().add("btn");
        freshStartButton.setMaxWidth(Double.MAX_VALUE);
        freshStartButton.setOnAction(e -> freshStart());  // Resets game
        return freshStartButton;
    }

    private Button createExitButton() {
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("btn");
        exitButton.setMaxWidth(Double.MAX_VALUE);
        exitButton.setOnAction(e -> showExitConfirmation());
        return exitButton;
    }
    
    private void showExitConfirmation() {
        Scene exitConfirmationScene = createExitConfirmationScene();
        primaryStage.setScene(exitConfirmationScene);
    }
    
    private Scene createExitConfirmationScene() {
        Label confirmationLabel = new Label("Are you sure you want to quit?");
        confirmationLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        confirmationLabel.setAlignment(Pos.CENTER);

        Button quitButton = new Button("Quit");
        quitButton.getStyleClass().add("quit-btn");
        quitButton.setOnAction(e -> System.exit(0)); // Exits application
        quitButton.setPrefWidth(200);

        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("play-btn");
        continueButton.setOnAction(e -> primaryStage.setScene(gameScene)); // Returns to game
        continueButton.setPrefWidth(200);

        HBox buttonBox = new HBox(20, continueButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, confirmationLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #213743;");
        layout.setPadding(new Insets(20));

        Scene exitConfirmationScene = new Scene(layout, 1000, 600);
        exitConfirmationScene.getStylesheets().add(getClass().getResource("/styles/appStyle.css").toExternalForm());

        return exitConfirmationScene;
    }

    private VBox createSidebar() {
        HBox walletBox = createWalletBox();
        VBox anteBox = createAnteBox();
        VBox pairPlusBox = createPairPlusBox();
        HBox continueAndFoldBox = createContinueAndFoldButtons();
        Button playBtn = createPlayButton();
        Button newLookBtn = createNewLookButton();
        Button freshStartBtn = createFreshStartButton();
        Button exitBtn = createExitButton();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        sidebar.getChildren().addAll(
                walletBox, anteBox, pairPlusBox, continueAndFoldBox, playBtn,
                spacer, newLookBtn, freshStartBtn, exitBtn
        );

        return sidebar;
    }
    
    private StackPane createCardPanel() {        
        // Configure result display area
        resultDisplay = new VBox(10);
        resultDisplay.setAlignment(Pos.CENTER);
        resultDisplay.setVisible(false);  // Initially hidden
        resultDisplay.setPrefWidth(200);
        resultDisplay.setMaxWidth(250);
        resultDisplay.setMaxHeight(100);
        resultDisplay.getStyleClass().add("game-label");

        // Set fixed height for dealer and player hand images to avoid shifting
        dealerHandImages.setAlignment(Pos.CENTER);
        dealerHandImages.setPrefHeight(150);

        playerHandImages.setAlignment(Pos.CENTER);
        playerHandImages.setPrefHeight(150);

        // Arrange dealer, result display, and player images vertically
        BorderPane cardPanelLayout = new BorderPane();
        cardPanelLayout.setTop(dealerHandImages);
        cardPanelLayout.setCenter(resultDisplay);
        cardPanelLayout.setBottom(playerHandImages);

        // Center-align and add margins
        BorderPane.setAlignment(dealerHandImages, Pos.TOP_CENTER);
        BorderPane.setAlignment(resultDisplay, Pos.CENTER);
        BorderPane.setAlignment(playerHandImages, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(dealerHandImages, new Insets(50, 0, 50, 0));
        BorderPane.setMargin(playerHandImages, new Insets(50, 0, 50, 0));

        // Position deck in the top-right
        AnchorPane.setTopAnchor(deck, -100.0);
        AnchorPane.setRightAnchor(deck, 10.0);
        AnchorPane deckPane = new AnchorPane(deck);

        // Overlay deckPane over cardPanelLayout
        mainPanel = new StackPane(cardPanelLayout, deckPane);
        mainPanel.setStyle("-fx-background-color: #0F212E;");

        return mainPanel;
    }

    private void updateCardBackImage() {
        // Selects card back image based on new look setting
        String cardBackPath = isNewLookEnabled ? "/images/red.png" : "/images/blue.png";
        Image cardBackImage = new Image(getClass().getResource(cardBackPath).toExternalForm());

        // Updates each card in the deck to the selected back image
        for (Node node : deck.getChildren()) {
            if (node instanceof ImageView) {
                ((ImageView) node).setImage(cardBackImage);
            }
        }
    }

    private StackPane createDeck() {
        deck = new StackPane();

        // Create place holders for three stacked card backs
        ImageView card1 = new ImageView();
        ImageView card2 = new ImageView();
        ImageView card3 = new ImageView();

        // Set size and stacking offsets
        card1.setFitWidth(90);
        card1.setPreserveRatio(true);
        card2.setFitWidth(90);
        card2.setPreserveRatio(true);
        card3.setFitWidth(90);
        card3.setPreserveRatio(true);

        card1.setTranslateY(0);
        card2.setTranslateY(10);
        card3.setTranslateY(20);

        deck.getChildren().addAll(card3, card2, card1);
        
        // Apply initial card back image
        updateCardBackImage();

        return deck;
    }

    private void updateResultDisplay(String winningHandOutcome, String resultMessage, int netChange) {
        resultDisplay.getChildren().clear();

        // Create label for winning hand outcome
        Label outcomeLabel = new Label();
        outcomeLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        outcomeLabel.setAlignment(Pos.CENTER);
        outcomeLabel.setTextAlignment(TextAlignment.CENTER);

        // Set outcome text based on the result
        if (winningHandOutcome.equals("Tie")) {
            outcomeLabel.setText("Tie");
        } else if (winningHandOutcome.equals("Dealer Did Not Qualify")) {
            outcomeLabel.setText("Dealer Did Not Qualify");
        } else if (winningHandOutcome.equals("Folded")) {
            outcomeLabel.setText("Folded");
        } else {
            outcomeLabel.setText(winningHandOutcome);
        }

        // Split resultMessage into lines and add each line as a label
        String[] resultLines = resultMessage.split("\\n");
        ArrayList<Label> resultLabels = new ArrayList<>();
        resultLabels.add(outcomeLabel); // Add outcome label first

        for (String line : resultLines) {
            Label lineLabel = new Label(line);
            lineLabel.setAlignment(Pos.CENTER);
            lineLabel.setTextAlignment(TextAlignment.CENTER);

            // Set text color based on netChange
            if (netChange > 0) {
                lineLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #4CAF50;"); // Green for win
            } else if (netChange < 0) {
                lineLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #FF5252;"); // Red for loss
            } else {
                lineLabel.setStyle("-fx-font-size: 14; -fx-text-fill: white;"); // Default color
            }

            resultLabels.add(lineLabel);
        }

        resultDisplay.setAlignment(Pos.CENTER);  // Center-align resultDisplay VBox
        resultDisplay.getChildren().addAll(resultLabels);
        resultDisplay.setVisible(true);
    }
    
    private void playRound() {
        resultDisplay.setVisible(false); // Hide result display at the start of each round

        // Check if player has enough funds to play
        if (gameController.getPlayer().getFunds() < 5) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You do not have enough funds to continue.");
            alert.showAndWait();
            playButton.setDisable(true);
            return;
        }

        // Get and parse ante and Pair Plus bets
        String anteInput = anteField.getText();
        String pairPlusInput = pairPlusField.getText();
        int anteBet = Integer.parseInt(anteInput);
        int pairPlusBet = Integer.parseInt(pairPlusInput);

        cardFlipSound = new AudioClip(getClass().getResource("/card.mp3").toExternalForm());
        cardFlipSound.setVolume(0.25);

        // Place bets, handle errors if funds are insufficient
        try {
            gameController.placeBets(anteBet, pairPlusBet);
            walletField.setText("$" + gameController.getPlayer().getFunds());
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Bet");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        // Deal cards
        gameController.dealCards();

        // Clear previous hand images
        dealerHandImages.getChildren().clear();
        playerHandImages.getChildren().clear();

        // Sequential animation for card deal
        SequentialTransition sequentialTransition = new SequentialTransition();

        // Animate player's cards face up
        for (Card card : gameController.getPlayer().getHand()) {
            String cardImagePath = "/images/" + card.toString() + ".png";
            ImageView cardFaceImageView = new ImageView(new Image(getClass().getResource(cardImagePath).toExternalForm()));
            animateCardToPosition(cardFaceImageView, playerHandImages, card, sequentialTransition, true);
        }

        // Animate dealer's cards face down
        for (Card card : gameController.getDealer().getDealersHand()) {
            String backColor = isNewLookEnabled ? "/images/red.png" : "/images/blue.png";
            ImageView cardBackImageView = new ImageView(new Image(getClass().getResource(backColor).toExternalForm()));
            animateCardToPosition(cardBackImageView, dealerHandImages, card, sequentialTransition, false);
        }

        sequentialTransition.play(); // Play animation

        // Disable Play button, enable Continue and Fold buttons
        playButton.setDisable(true);
        continueButton.setDisable(false);
        foldButton.setDisable(false);
    }

    private void animateCardToPosition(ImageView cardImageView, Pane destinationPane, Card card, SequentialTransition sequentialTransition, boolean faceUp) {
        // Add card to deck initially
        deck.getChildren().add(cardImageView);

        // Set initial card size for animation
        cardImageView.setFitWidth(90);
        cardImageView.setPreserveRatio(true);

        // Calculate destination position
        Bounds deckBounds = deck.localToScene(deck.getBoundsInLocal());
        Bounds destBounds = destinationPane.localToScene(destinationPane.getBoundsInLocal());
        double toX = destBounds.getCenterX() - deckBounds.getCenterX();
        double toY = destBounds.getCenterY() - deckBounds.getCenterY();

        // Animation transition from deck to destination
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), cardImageView);
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(toX);
        transition.setToY(toY);

        // Update card image on animation completion
        transition.setOnFinished(event -> {
            if (faceUp) {
                String cardValue = getCardValueString(card.getValue());
                String cardSuit = String.valueOf(card.getSuit());
                String imagePath = "/images/" + cardValue + cardSuit + ".png";
                cardImageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            }

            cardFlipSound.play();

            // Move card to destination pane
            deck.getChildren().remove(cardImageView);
            destinationPane.getChildren().add(cardImageView);

            // Reset card position and size for destination display
            cardImageView.setTranslateX(0);
            cardImageView.setTranslateY(0);
            cardImageView.setFitWidth(90);
            cardImageView.setPreserveRatio(true);
        });

        sequentialTransition.getChildren().add(transition); // Add animation to sequence
    }

    // Returns string representation of card values
    private String getCardValueString(int value) {
        switch (value) {
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            case 14: return "A";
            default: return Integer.toString(value);
        }
    }

    private void freshStart() {
        gameController.resetGame(); // Reset game state

        // Update wallet and reset input fields
        walletField.setText("$" + gameController.getPlayer().getFunds());
        anteField.setText("0");
        pairPlusField.setText("0");

        // Clear previous hand images and hide result display
        dealerHandImages.getChildren().clear();
        playerHandImages.getChildren().clear();
        resultDisplay.setVisible(false);

        // Enable Play button, disable Continue and Fold buttons
        playButton.setDisable(false);
        continueButton.setDisable(true);
        foldButton.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
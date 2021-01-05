
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tyler Mankey
 * 
 */
public class TicTacToe extends Application {

  private static final int WIDTH = 650;
  private static final int HEIGHT = 650;
  private Button[][] panes = new Button[3][3];
  private char turn;
  private char prevTurn;
  private char charX = 'X';
  private char charO = 'O';
  private Button btn;
  

 
  

  public static void main(String[] args) 
  {
	  // Launch the application. 
	  launch(args);
	    
  }
  
  @Override
  public void start(Stage stage) 
  {
	//creates stage and sets the first turn to 'X'
    stage.setTitle("Tic Tac Toe");
    stage.setScene(new Scene(createBoard(stage), WIDTH, HEIGHT));
    stage.show();
    
  }

  public GridPane createBoard(Stage stage) 
  {
	  //sets first turn to 'X'
	  turn = 'X';
	  
	  // Event Handler handles all button presses to give 'X' or 'O'
	    EventHandler<ActionEvent> handler = event ->
	    {
	      if (event.getSource() instanceof Button) {
	        Button clicked = (Button) event.getSource();
	        if (clicked.getText().length() < 1) {
	        	clicked.setText(String.format("%c", turn));
	          
	        	
	        	
	         //if turn was 'X' then its 'O' turn if not 'O' then 'X'
	          turn = turn == charX ? charO : charX;
	          
	          //prevTurn used to first player who just took a turn
	          prevTurn = turn == charO ? charX : charO; 
	      
	          //checks if there is a winner of a tie and calls a popup window
	          if(winnerFound())
	          	{
	        	  popupWindowWin(stage, prevTurn);
	          	}
	        
	          if(!winnerFound() && boardFull())
		  		{
	        	  popupWindowTie(stage);
		  		}  
	        }
	      }
	     
	    };
  
	//Creates Buttons to go inside the GridPane   
    GridPane board = new GridPane();
    for (int row = 0; row < 3; row++) 
    {
      for (int col = 0; col < 3; col++) 
      {
    	//Each Square is a Button
        btn = new Button();
        
        //Set of each Button
        btn.setPrefSize(WIDTH, HEIGHT);
        
        //Customized display settings for board
        btn.setStyle(String.format("-fx-font: %d arial;-fx-base: #ddddee;" + "-fx-border-color: black; -fx-border-style: dotted;" +
        							" -fx-font-size: 90px; -fx-background-color: cyan; -fx-font-weight: bold;"
        							, WIDTH / 7));
        //Places on grid
        GridPane.setConstraints(btn, col, row); // grid placement
        
        //adds to GridPane
        board.getChildren().add(btn); // add to GridPane
        
        //Buttons are sent to Handler
        btn.setOnAction(handler);
        
        //Each spot in the 2D array is set to a Button
        panes[row][col] = btn;
        
        
      }
    }
    return board;
  }
  
  /**
   * 	runs though board to check if all slots are full
   * 	used in the event of a "tie"
   * 
   * @return true/false
   */
  	public boolean boardFull()
  	{
  		for (int k = 0; k < 3; k++)
  		{
  			if(panes[0][k].getText().isEmpty() || panes[1][k].getText().isEmpty() || panes[2][k].getText().isEmpty())		
  			{
  				return false;
  			}
  			if(panes[k][0].getText().isEmpty() || panes[k][1].getText().isEmpty() || panes[k][2].getText().isEmpty())
  			{
  				return false;
  			}
  		}
  		
  		return true;
  	}
  	
  	/**
  	 *  calls other methods to check if a winner has been found
  	 * 
  	 * @return true/false
  	 */
	public boolean winnerFound()
	{
		if (verticalWin() || horizontalWin() || diagonalWin()){
			return true;
		}
		return false;
		
	}
	
	/**
	 * method checks for a win horizontally
	 * 
	 * 
	 * @return true/false
	 */
	private boolean horizontalWin()
	{
		for (int k = 0; k < 3; k++)
		{
			if (panes[0][k].getText().equals(panes[1][k].getText()) && 
					panes[1][k].getText().equals(panes[2][k].getText()) &&
					!panes[0][k].getText().equals("") && !panes[1][k].getText().equals("") && !panes[2][k].getText().equals(""))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method checks for a win vertically
	 * 
	 * 
	 * @return true/false
	 */
	private boolean verticalWin()
	{
		for (int k = 0; k < 3; k++)
		{
			if (panes[k][0].getText().equals(panes[k][1].getText()) && 
					panes[k][1].getText().equals(panes[k][2].getText()) &&
					!panes[k][0].getText().equals("") && !panes[k][1].getText().equals("") && !panes[k][2].getText().equals(""))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method checks for a win Diagonally
	 * 
	 * 
	 * @return true/false
	 */
	private boolean diagonalWin()
	{
		if (panes[0][0].getText().equals(panes[1][1].getText()) &&
				panes[1][1].getText().equals(panes[2][2].getText()) &&
				!panes[0][0].getText().equals("") && !panes[1][1].getText().equals("") && !panes[2][2].getText().equals(""))
		{
			return true;
		}
		else if (panes[2][0].getText().equals(panes[1][1].getText()) &&
				panes[1][1].getText().equals(panes[0][2].getText()) &&
				!panes[2][0].getText().equals("") && !panes[1][1].getText().equals("") && !panes[0][2].getText().equals(""))
		{
			return true;
		}
		return false;
	}

	/**
	 *  Creates a new stage which acts like popup window
	 * 	stage includes labels and buttons to replay or close game
	 * 
	 * @param stage
	 * @param prevTurn
	 */
	public void popupWindowWin(Stage stage, char prevTurn)
	{
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Game Message");
		window.setMinWidth(300);
		window.setMinHeight(200);
		window.setAlwaysOnTop(true);
		
		Label label = new Label();
		label.setStyle("-fx-font-size: 20px;");
		label.setText("Player ('" + prevTurn + "') has won the game!!!\n\n");
		
		Button restartButton = new Button("Play Again!");
		restartButton.setOnAction( e ->  
		{
			stage.close();
			stage.setScene(new Scene(createBoard(stage), WIDTH, HEIGHT));
			stage.show();
			window.close();
		} );
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e ->
		{
			stage.close();
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, restartButton, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();	
	}
	
	/**
	 *  Creates a new stage which acts like popup window
	 * 	stage includes labels and buttons to replay or close game
	 * 
	 * @param stage
	 */
	public void popupWindowTie(Stage stage)
	{
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Game Message");
		window.setMinWidth(300);
		window.setMinHeight(200);
		window.setAlwaysOnTop(true);
	
		Label label = new Label();
		label.setStyle("-fx-font-size: 20px;");
		label.setText("The Game Ended in a Tie\n\n");
		
		Button restartButton = new Button("Play Again!");
		restartButton.setOnAction( e ->  
		{
			stage.close();
			stage.setScene(new Scene(createBoard(stage), WIDTH, HEIGHT));
			stage.show();
			window.close();
		});
	
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e ->
		{
			stage.close();
			window.close();
		});
	
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, restartButton, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();	
	}
}
package solitaire.GameModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import solitaire.GUI.DeckView;
import solitaire.GUI.DiscardPileView;
import solitaire.internal.Card.Suit;
import solitaire.internal.SuitStackManager.SuitStack;
import solitaire.internal.WorkingStackManager.Workingstack;

public class Solitaire extends Application
{
	private static final int WIDTH = 680;
	private static final int HEIGHT = 500;
	private static final int MARGIN_OUTER = 10;
	private static final String TITLE = "Solitaire";
	private static final String VERSION = "1.0";

    private DeckView aDeckView = new DeckView();
    private DiscardPileView aDiscardPileView = new DiscardPileView();
//    private SuitStack[] aSuitStacks = new SuitStack[Suit.values().length];
//    private CardStack[] aStacks = new CardStack[StackIndex.values().length];
    
	/**
	 * Launches the application.
	 * @param pArgs This program takes no argument.
	 */
	public static void main(String[] pArgs) 
	{
        launch(pArgs);
    }
    
    @Override
    public void start(Stage pPrimaryStage) 
    {
		pPrimaryStage.setTitle(TITLE + " " + VERSION); 
           
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: green;");
        root.setHgap(MARGIN_OUTER);
        root.setVgap(MARGIN_OUTER);
        root.setPadding(new Insets(MARGIN_OUTER));
        
        root.add(aDeckView, 0, 0);
        root.add(aDiscardPileView, 1, 0);
                
//        for( SuitStackIndex index : SuitStackIndex.values() )
//        {
//        	aSuitStacks[index.ordinal()] = new SuitStack(index);
//        	root.add(aSuitStacks[index.ordinal()], 3+index.ordinal(), 0);
//        }
//      
//        for( StackIndex index : StackIndex.values() )
//        {
//        	aStacks[index.ordinal()] = new CardStack(index);
//        	root.add(aStacks[index.ordinal()], index.ordinal(), 1);
//        }
//        
//        root.setOnKeyTyped(new EventHandler<KeyEvent>()
//		{
//
//			@Override
//			public void handle(final KeyEvent pEvent)
//			{
//				if( pEvent.getCharacter().equals("\r"))
//				{
//					GameModel.instance().tryToAutoPlay();
//				}
//				else if( pEvent.getCharacter().equals("\b"))
//				{
//					GameModel.instance().undoLast();
//				}
//				pEvent.consume();
//			}
//        	
//		});
        
        pPrimaryStage.setResizable(false);
        pPrimaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        pPrimaryStage.show();
    }
}
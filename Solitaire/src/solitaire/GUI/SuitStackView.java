package solitaire.GUI;

import solitaire.GameModel.GameModel;
import solitaire.GameModel.GameModelListener;
import solitaire.internal.Card;
import solitaire.internal.SuitStackManager.SuitStack;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class SuitStackView extends StackPane implements GameModelListener
{
	private static final int PADDING = 5;
	private static final String BORDER_STYLE = "-fx-border-color: lightgray;"
			+ "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
	private static final String BORDER_STYLE_DRAGGED = "-fx-border-color: darkgray;"
			+ "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
	private static final String BORDER_STYLE_NORMAL = "-fx-border-color: lightgray;"
			+ "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
	
	private CardDragHandler aDragHandler;
	private SuitStack aIndex;
	
	public SuitStackView(SuitStack pIndex)
	{
		aIndex = pIndex;
		setPadding(new Insets(PADDING));
		setStyle(BORDER_STYLE);
		final ImageView image = new ImageView(CardImages.getBack());
    	image.setVisible(false);
       	getChildren().add(image);
    	aDragHandler = new CardDragHandler(image);
    	image.setOnDragDetected(aDragHandler);
    	setOnDragOver(createOnDragOverHandler(image));
    	setOnDragEntered(createOnDragEnteredHandler());
    	setOnDragExited(createOnDragExitedHandler());
    	setOnDragDropped(createOnDragDroppedHandler());
    	GameModel.getInstance().addListener(this);
	}
	
	@Override
	public void gameStateChanged()
	{
		if( !GameModel.getInstance().canDraw(aIndex))
		{
			getChildren().get(0).setVisible(false);
		}
		else
		{
			getChildren().get(0).setVisible(true);
			Card topCard = GameModel.getInstance().peekSuitStack(aIndex);
			ImageView image = (ImageView)getChildren().get(0);
			image.setImage(CardImages.getCard(topCard));
			aDragHandler.setCard(topCard);
		}
	}
	
	private EventHandler<DragEvent> createOnDragOverHandler(final ImageView pView)
	{
		return new EventHandler<DragEvent>()
    	{
    	    public void handle(DragEvent pEvent) 
    	    {
    	    	if(pEvent.getGestureSource() != pView && pEvent.getDragboard().hasString())
    	    	{
    	    		CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
    	    		if( transfer.size() == 1 && GameModel.getInstance().canAdd(transfer.getTop(), aIndex) )
    	    		{
    	    			pEvent.acceptTransferModes(TransferMode.MOVE);
    	    		}
    	    	}

    	    	pEvent.consume();
    	    }
    	};
	}
	
	private EventHandler<DragEvent> createOnDragEnteredHandler()
	{
		return new EventHandler<DragEvent>()
    	{
    		public void handle(DragEvent pEvent) 
    		{
    			CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
	    		if( transfer.size() == 1 && GameModel.getInstance().canAdd(transfer.getTop(), aIndex) )
    			{
    				setStyle(BORDER_STYLE_DRAGGED);
    			}
    			pEvent.consume();
    		}
    	};
	}
	
	private EventHandler<DragEvent> createOnDragExitedHandler()
	{
		return new EventHandler<DragEvent>()
    	{
    		public void handle(DragEvent pEvent)
    		{
    			setStyle(BORDER_STYLE_NORMAL);
    			pEvent.consume();
    		}
    	};
	}
	
	private EventHandler<DragEvent> createOnDragDroppedHandler()
	{
		return new EventHandler<DragEvent>() 
    	{
    		public void handle(DragEvent pEvent)
    		{
    			Dragboard db = pEvent.getDragboard();
    			boolean success = false;
    			if(db.hasString()) 
    			{
    				CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
    				GameModel.getInstance().getCardMove(transfer.getTop(), aIndex).move();		
    				success = true;
    			}
    			pEvent.setDropCompleted(success);
    			pEvent.consume();
    		}
    	};
	}
}
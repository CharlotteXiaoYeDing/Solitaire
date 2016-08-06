package solitaire.GUI;

import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import solitaire.GameModel.GameModel;
import solitaire.GameModel.GameModelListener;

/**
 * Component that shows the deck and allows clicking
 * it to draw cards. Listens to game model state changes
 * and updates itself to disappear if it is empty.
 */
public class DeckView extends HBox implements GameModelListener
{
	private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    private static final int IMAGE_NEW_LINE_WIDTH = 10;
    private static final int IMAGE_FONT_SIZE = 15;
	
	public DeckView()
	{
        final Button button = new Button();
        button.setGraphic(new ImageView(CardImages.getBack()));
        button.setStyle(BUTTON_STYLE_NORMAL);

    	button.setOnMousePressed(new EventHandler<MouseEvent>() 
    	{
    		@Override
    		public void handle(MouseEvent pEvent) 
    		{
    			((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_PRESSED);
    		}            
    	});

    	button.setOnMouseReleased(new EventHandler<MouseEvent>() 
    	{
    		@Override
    		public void handle(MouseEvent pEvent) 
    		{
    			((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_NORMAL);
    			if( GameModel.getInstance().isDeckEmpty())
    			{
    				GameModel.getInstance().reset();
    			}
    			else
    			{
    				GameModel.getInstance().discard();
    			}
    		}            
    	});
        
        getChildren().add(button);
    	GameModel.getInstance().addListener(this);
	}
	
	private Canvas createNewGameImage()
	{
		double width = CardImages.getBack().getWidth();
		double height = CardImages.getBack().getHeight();
		Canvas canvas = new Canvas( width, height );
		GraphicsContext context = canvas.getGraphicsContext2D();
		
		// The reset image
		context.setStroke(Color.DARKGREEN);
		context.setLineWidth(IMAGE_NEW_LINE_WIDTH);
		context.strokeOval(width/4, height/2-width/4 + IMAGE_FONT_SIZE, width/2, width/2);

		// The text
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		context.setFill(Color.DARKKHAKI);
		context.setFont(Font.font(Font.getDefault().getName(), IMAGE_FONT_SIZE));
		
		if( GameModel.getInstance().isCompleted() )
		{
			context.fillText("You won!", Math.round(width/2), IMAGE_FONT_SIZE);
		}
		else
		{
			context.fillText("Give up?", Math.round(width/2), IMAGE_FONT_SIZE);
		}
		context.setTextAlign(TextAlignment.CENTER);
		return canvas;
	}
	
	@Override
	public void gameStateChanged()
	{
		if( GameModel.getInstance().isDeckEmpty() )
		{
			((Button)getChildren().get(0)).setGraphic(createNewGameImage());
		}
		else
		{
			((Button)getChildren().get(0)).setGraphic(new ImageView(CardImages.getBack()));
		}
	}
	
	//???
	public void reset()
	{
		getChildren().get(0).setVisible(true);
	}
}
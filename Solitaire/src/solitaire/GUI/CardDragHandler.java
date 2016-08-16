package solitaire.GUI;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import solitaire.internal.Card;

public class CardDragHandler implements EventHandler<MouseEvent>
{
	private Card aCard;
	private ImageView aImageView;

	CardDragHandler(ImageView pView)
	{
		aImageView = pView;
	}

	void setCard(Card pCard)
	{
		aCard = pCard;
	}

	@Override
	public void handle(MouseEvent pMouseEvent)
	{
		Dragboard db = aImageView.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.putString(aCard.getIDString());
		db.setContent(content);
		pMouseEvent.consume();
	}
}
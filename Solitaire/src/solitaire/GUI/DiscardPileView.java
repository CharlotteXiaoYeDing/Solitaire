package solitaire.GUI;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import solitaire.GameModel.GameModel;
import solitaire.GameModel.GameModelListener;
import solitaire.internal.Card;

/**
 * Component that shows the state of the discard pile and allows
 * dragging cards from it.
 */
public class DiscardPileView extends HBox implements GameModelListener
{
    private static final int PADDING = 5;
    private CardDragHandler aDragHandler;
    
    public DiscardPileView()
    {
        setPadding(new Insets(PADDING));
        final ImageView image = new ImageView(CardImages.getBack());
        image.setVisible(false);
        getChildren().add(image);
        aDragHandler = new CardDragHandler(image);
        image.setOnDragDetected(aDragHandler);
        GameModel.getInstance().addListener(this);
    }
    
    @Override
    public void gameStateChanged()
    {
        if( GameModel.getInstance().isDiscardEmpty())
        {
            getChildren().get(0).setVisible(false);
        }
        else
        {
            getChildren().get(0).setVisible(true);
            Card topCard = GameModel.getInstance().peekDiscard();
            ImageView image = (ImageView)getChildren().get(0);
            image.setImage(CardImages.getCard(topCard));
            aDragHandler.setCard(topCard);
        }
    }
}
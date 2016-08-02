//package solitaire.GUI;
//
///*******************************************************************************
// * Solitaire
// *
// * Copyright (C) 2016 by Martin P. Robillard
// *
// * See: https://github.com/prmr/Solitaire
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *******************************************************************************/
//
//
//import javafx.geometry.Insets;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import solitaire.GameModel.GameModel;
//import solitaire.GameModel.GameModelListener;
//import solitaire.internal.Card;
//
///**
// * Component that shows the state of the discard pile and allows
// * dragging cards from it.
// */
//public class DiscardPileView extends HBox implements GameModelListener
//{
//    private static final int PADDING = 5;
//    private CardDragHandler aDragHandler;
//    
//    public DiscardPileView()
//    {
//        setPadding(new Insets(PADDING));
//        final ImageView image = new ImageView(CardImages.getBack());
//        image.setVisible(false);
//        getChildren().add(image);
//        aDragHandler = new CardDragHandler(image);
//        image.setOnDragDetected(aDragHandler);
//        GameModel.getInstance().addListener(this);
//    }
//    
//    @Override
//    public void gameStateChanged()
//    {
//        if( GameModel.getInstance().isDiscardEmpty())
//        {
//            getChildren().get(0).setVisible(false);
//        }
//        else
//        {
//            getChildren().get(0).setVisible(true);
//            Card topCard = GameModel.getInstance().peekDiscardPile();
//            ImageView image = (ImageView)getChildren().get(0);
//            image.setImage(CardImages.getCard(topCard));
//            aDragHandler.setCard(topCard);
//        }
//    }
//}
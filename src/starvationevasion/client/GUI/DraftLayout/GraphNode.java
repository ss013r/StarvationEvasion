package starvationevasion.client.GUI.DraftLayout;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import starvationevasion.client.GUI.GUI;

/**
 * VotingGraphNode is the GUI element responsible for allowing the player to view graphs about the selected region
 * Graphs able for view for a selected region:
 * Population
 * HDI
 * Balance of farming sector government revenue
 * Total Cost and Total Revenue of each of the 12 farm products
 *
 * Currently the Total Cost and Total Revenue Graph for each of the 12 farm products are not
 */
public class GraphNode extends BorderPane
{
  Text tempText;
  GUI gui;

  public GraphNode(GUI gui)
  {
    this.gui = gui;
    tempText = new Text("Crop Comparisons");
    tempText.setFont(Font.font(null, FontWeight.BOLD, 15));

    this.getStylesheets().add("/starvationevasion/client/GUI/DraftLayout/style.css");
    this.getStyleClass().add("graphnode");
    //this.getChildren().add(tempText);
    
    this.setCenter(tempText);
    
    ImageView crop = new ImageView(gui.getImageGetter().getCropIcon());
    this.setAlignment(crop, Pos.CENTER_RIGHT);
    this.setRight(crop);
    
    
    
    this.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        if(gui.getPopupManager().isOpen()==null)
        {
          if(gui.getPopupManager().isOpen()==null)
          {
          gui.getDraftLayout().getGraphDisplay().setDataVisMode(1);
          gui.getPopupManager().toggleGraphDisplay();
          }
        }
      }
    });
  }

}

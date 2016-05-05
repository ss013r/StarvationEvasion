package application;
  
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameCardView extends Application
{
  private double cardWidth = 370;
  private double cardHeight = 520;
  private AnchorPane polygonPane;
  private StackPane cardPane;
  private ImageView cardImage = new ImageView();
  
  private boolean mouseOverOctagon = false;
  private double textOctagonHeightModifier = 0;
  private double transparency = 0.50;
  private Double[] octagonPoints = new Double[]{ 
      (cardWidth*2/9), 0.0,
      (cardWidth*2/9), 0.0,
      (cardWidth*7/9), 0.0, 
      (cardWidth*8/9), (cardHeight/13),
      (cardWidth*8/9), (cardHeight/13)  +textOctagonHeightModifier,
      (cardWidth*7/9), (cardHeight*2/13)+textOctagonHeightModifier,
      (cardWidth*2/9), (cardHeight*2/13)+textOctagonHeightModifier,
      (cardWidth/9),   (cardHeight/13)  +textOctagonHeightModifier, 
      (cardWidth/9),   (cardHeight/13)
      };
  private String color = "0xaba9db";  

  private Polygon topTrapezoid        = new Polygon();
  private Polygon bottomTrapezoid     = new Polygon();
  private Polygon bottomLeftPentagon  = new Polygon();
  private Polygon topLeftPentagon     = new Polygon();
  private Polygon bottomRightPentagon = new Polygon();
  private Polygon topRightPentagon    = new Polygon();
  private Polygon middleTextOctagon   = new Polygon();
  private Text title, voteNumberText, voteCostText, rulesText, informationText;
  
  @Override
  public void start(Stage primaryStage)
  {
//    GameCard card = GameCard.create(null, EnumPolicy.Policy_CleanRiverIncentive);
    
    cardImage.setFitWidth(cardWidth);
    cardImage.setFitHeight(cardHeight);
    cardPane = new StackPane();
    File file = new File("Resources/Policy_CleanRiverIncentive.png");
    Image image = new Image(file.toURI().toString());
    cardImage.setImage(image);
    cardPane.getChildren().add(cardImage);
    
    //Initialize Text fields
    title = new Text("Title");
    voteNumberText = new Text("3");
    voteCostText = new Text("$200");
    rulesText = new Text("RulesText");
    informationText = new Text("Info");
    ArrayList<Text> textList = new ArrayList<Text>();
    textList.addAll(Arrays.asList(title, voteNumberText, voteCostText, rulesText, informationText));
    for(Text t : textList)
    {
      t.setStroke(Color.BLACK);
      t.setStrokeType(StrokeType.OUTSIDE);
      t.setStrokeWidth(1);
      t.setFont(Font.font("Helvetica", 18));
      t.setFill(Color.WHITE);
      t.setFontSmoothingType(FontSmoothingType.LCD);
    }
    rulesText.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
        mouseOverOctagon = true;
      }
    });
    rulesText.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
        mouseOverOctagon = false;
      }
    });
    
    //Initialize Polygons
    populateGameCard();
    updateTextOctagon();
    ArrayList<Polygon> polygonList = new ArrayList<Polygon>();
    polygonList.addAll(Arrays.asList(topTrapezoid, bottomTrapezoid, bottomLeftPentagon, topLeftPentagon, bottomRightPentagon, topRightPentagon, middleTextOctagon));
    for(Polygon p : polygonList)
    {
      p.setStrokeType(StrokeType.INSIDE);
      p.setStrokeWidth(2.0);
      p.setFill(Color.web(color, transparency));
      p.setStroke(Color.BLACK);
      p.setOnMouseEntered(new EventHandler<MouseEvent>()
      {
        public void handle(MouseEvent me) 
        {
          p.setStroke(Color.YELLOW);
        }
      });
      p.setOnMouseExited(new EventHandler<MouseEvent>()
      {  
        public void handle(MouseEvent me) 
        {
          p.setStroke(Color.BLACK);
        }
      });
    }
    middleTextOctagon.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
        mouseOverOctagon = true;
        
        middleTextOctagon.setStroke(Color.YELLOW);
      }
    });
    middleTextOctagon.setOnMouseExited(new EventHandler<MouseEvent>()
    {  
      public void handle(MouseEvent me) 
      {
        mouseOverOctagon = false;
        middleTextOctagon.setStroke(Color.BLACK);
      }
    });
    
    polygonPane = new AnchorPane();
    polygonPane.getChildren().addAll(
        topLeftPentagon, topTrapezoid, topRightPentagon, middleTextOctagon,
        bottomLeftPentagon, bottomTrapezoid, bottomRightPentagon,
        title, rulesText, voteNumberText, voteCostText, informationText
        );
    cardPane.getChildren().add(polygonPane);

    
    
    //Initialize Scene
    try 
    {
      Scene scene = new Scene(cardPane, cardWidth, cardHeight);
      scene.widthProperty().addListener(new ChangeListener<Number>()
      {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
        {
            cardWidth = newSceneWidth.doubleValue();
            cardImage.setFitWidth(cardWidth);
            populateGameCard();
            updateTextOctagon();
        }
      });
      scene.heightProperty().addListener(new ChangeListener<Number>()
      {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
        {
            cardHeight = newSceneHeight.doubleValue();
            cardImage.setFitHeight(cardHeight);
            populateGameCard();
            updateTextOctagon();
        }
      });
      primaryStage.setScene(scene);
      primaryStage.show();
    } 
    catch(Exception e) 
    {
      e.printStackTrace();
    }
    
    timer.start();
  }
  
  private void populateGameCard() 
  {
    topTrapezoid.getPoints().setAll(new Double[] {
        (cardWidth/9),   0.0,
        (cardWidth*8/9), 0.0,
        (cardWidth*7/9), cardHeight*1/13, 
        (cardWidth*2/9), cardHeight*1/13 
        });
    AnchorPane.setTopAnchor(topTrapezoid, 0.0);
    
    bottomTrapezoid.getPoints().setAll(new Double[] { 
        (cardWidth/9),   cardHeight*1/13,
        (cardWidth*8/9), cardHeight*1/13,
        (cardWidth*7/9), 0.0,
        (cardWidth*7/9), 0.0,
        (cardWidth*2/9), 0.0,
        (cardWidth*2/9), 0.0 
        });
    AnchorPane.setBottomAnchor(bottomTrapezoid, 0.0);

    bottomLeftPentagon.getPoints().setAll(new Double[] {
        0.0,           0.0,
        cardWidth/9,   0.0, 
        cardWidth*2/9, cardHeight/13,
        cardWidth/9,   cardHeight*2/13,
        0.0,           cardHeight*2/13
        });
    AnchorPane.setBottomAnchor(bottomLeftPentagon, 0.0);
    AnchorPane.setLeftAnchor(bottomLeftPentagon, 0.0);
    
    topLeftPentagon.getPoints().setAll(new Double[] {
        0.0,           0.0, 
        cardWidth/9,   0.0, 
        cardWidth*2/9, cardHeight/13, 
        cardWidth/9,   cardHeight*2/13,
        0.0,           cardHeight*2/13 
        });
    AnchorPane.setTopAnchor(topLeftPentagon, 0.0);
    AnchorPane.setLeftAnchor(topLeftPentagon, 0.0);
    
    bottomRightPentagon.getPoints().setAll(new Double[] { 
        0.0,            0.0,
        -cardWidth/9,   0.0,
        -cardWidth*2/9, cardHeight/13,
        -cardWidth/9,   cardHeight*2/13, 
        0.0,            cardHeight*2/13 
        });
    AnchorPane.setBottomAnchor(bottomRightPentagon, 0.0);
    AnchorPane.setRightAnchor(bottomRightPentagon, 0.0);

    topRightPentagon.getPoints().setAll(new Double[] { 
        0.0,            0.0, 
        -cardWidth/9,   0.0, 
        -cardWidth*2/9, cardHeight/13, -
        cardWidth/9,   cardHeight*2/13, 
        0.0,            cardHeight*2/13
        });
    AnchorPane.setTopAnchor(topRightPentagon, 0.0);
    AnchorPane.setRightAnchor(topRightPentagon, 0.0);
    
    middleTextOctagon.getPoints().setAll(octagonPoints);
    AnchorPane.setBottomAnchor(middleTextOctagon, (cardHeight / 13));
    
    AnchorPane.setTopAnchor(title, cardHeight/36);
    AnchorPane.setLeftAnchor(title, cardWidth/4);
    
    AnchorPane.setTopAnchor(voteNumberText, cardHeight/18);
    AnchorPane.setRightAnchor(voteNumberText, cardWidth/18);
    
    AnchorPane.setBottomAnchor(voteCostText, cardHeight/18);
    AnchorPane.setRightAnchor(voteCostText, cardWidth/18);
    
    AnchorPane.setBottomAnchor(rulesText, cardHeight*2/13+textOctagonHeightModifier);
    AnchorPane.setLeftAnchor(rulesText, cardWidth/4);

    AnchorPane.setBottomAnchor(informationText, cardHeight/18);
    AnchorPane.setLeftAnchor(informationText, cardWidth/18);
   
  }
  
  //Handles Animation of the Text area
  AnimationTimer timer = new AnimationTimer()
  {
    double speed = 5;
    @Override
    public void handle(long now) 
    {
      if(mouseOverOctagon && textOctagonHeightModifier < cardHeight*9/13)
      {
        textOctagonHeightModifier+=speed;
        updateTextOctagon();
      }
      else if(!mouseOverOctagon && textOctagonHeightModifier > 0)
      {
        textOctagonHeightModifier-=speed;
        updateTextOctagon();
      }
    }
  };
  
  private void updateTextOctagon()
  {
    Double[] octagonPoints = new Double[]{ 
        (cardWidth*2/9), 0.0,
        (cardWidth*2/9), 0.0,
        (cardWidth*7/9), 0.0, 
        (cardWidth*8/9), (cardHeight/13),
        (cardWidth*8/9), (cardHeight/13)  +textOctagonHeightModifier,
        (cardWidth*7/9), (cardHeight*2/13)+textOctagonHeightModifier,
        (cardWidth*2/9), (cardHeight*2/13)+textOctagonHeightModifier,
        (cardWidth/9),   (cardHeight/13)  +textOctagonHeightModifier, 
        (cardWidth/9),   (cardHeight/13)
        };
    middleTextOctagon.getPoints().setAll(octagonPoints);
    AnchorPane.setBottomAnchor(middleTextOctagon, (cardHeight / 13));
    AnchorPane.setBottomAnchor(rulesText, cardHeight*2/13+textOctagonHeightModifier);
  }
  
  public static void main(String[] args) 
  {
    launch(args);
  }

}

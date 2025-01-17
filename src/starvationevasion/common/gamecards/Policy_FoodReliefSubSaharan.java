package starvationevasion.common.gamecards;

import java.util.EnumSet;

import starvationevasion.common.EnumFood;
import starvationevasion.server.model.State;

/**
 * Title: {@value #TITLE}<br><br>
 * Game Text: {@value #TEXT}<br><br>
 *
 * Draft Affects: When drafting this policy, player
 * selects a food to send 5 thousand tons to Central Asia. <br><br>
 *
 * Votes Required: Automatic<br><br>
 * Eligible Regions: All U.S.<br><br>
 *
 * Model Effects: Commodity food is distributed to relieve world hunger
 * in Sub Saharan Africa.  Import penalty costs apply to the aid.<br><br>
 *
 * Food purchased for relief inflates the global sell foodPrice of the food type by a
 * direct reduction of supply without effect on demand (since those to whom the
 * relief is delivered are presumed to lack the resources to have been part of the demand).
*/
public class Policy_FoodReliefSubSaharan extends GameCard
{ 
  public static final String TITLE = 
	"Food Relief in Sub Saharan Africa";

  public static final String TEXT = 
	"This region sends 5 thousand tons of target food to Sub Saharan Africa";

  public static final EnumSet<State> PLAY_STATES = //when the card can be used
      EnumSet.of(State.DRAFTING);

  /**
   *  {@inheritDoc}
   */
  @Override
  public boolean voteWaitForAll() {return true;}

  /**
   *  {@inheritDoc}
   */
  @Override
  public String getTitle(){ return TITLE;}

  /**
   *  {@inheritDoc}
   */
  @Override
  public String getGameText(){ return TEXT;}

  
  /**
   * {@inheritDoc}
   */
  @Override
  public EnumSet<State> getUsableStates()
  {
    return PLAY_STATES;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EnumFood[] getValidTargetFoods() {return EnumFood.ALL_FOODS;}
}

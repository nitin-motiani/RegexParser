package motiani.regex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class State {
	private Map<Character, List<State>> transitionMap;

	State(Map<Character, List<State>> tMap)
	{
		transitionMap = tMap;
	}
	
	public List<State> statesReachableByEmptyPath()
	{
		/* 
		 * Is this a good way of storing empty moves. Also is explicitly creating a linked list here smart too.
		 * Since this is a public function which returns list, but due to recursive call we are also relying on 
		 * the knowledge that we'll get a linked list. Should there be a private helper function to do recursion 
		 */
		List<State> emptyCharMoves = transitionMap.get('\0');
		if(emptyCharMoves == null)
			return emptyCharMoves;
		
		List<State> possibleStates = new LinkedList<State>(emptyCharMoves);
		
		for(State emptyCharMove : emptyCharMoves )
		{
			List<State> possibleStatesAfterMove = emptyCharMove.statesReachableByEmptyPath();
			if(possibleStatesAfterMove != null)
			{
				possibleStates.addAll(possibleStatesAfterMove);
			}
		}
		
		return possibleStates;
	}
	
	public List<State> makeMove(Character c)
	{
		List<State> possibleStatesByChar = transitionMap.get(c);
		if(possibleStatesByChar == null)
			return possibleStatesByChar;
		
		/* Same issue here. Should this new LinkedList() part go in a private function? */
		List<State> possibleStates = new LinkedList<State>(possibleStatesByChar);
		
		for(State possibleStateByChar : possibleStatesByChar)
		{
			List<State> emptyMoveStates = possibleStateByChar.statesReachableByEmptyPath();
			
			/* 
			 * Using list is bad idea. Now how are we going to handle dups. 
			 * Also function to get all empty path states need to keep track of seen states or it 
			 * might cause stack overflow. 
			 */
			if(emptyMoveStates != null)
			{
				possibleStates.addAll(emptyMoveStates);
			}
		}
		
		return possibleStates;
		
	}
}

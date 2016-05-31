package motiani.regex;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class State {
	private Map<Character, Set<State>> transitionMap;

	State(Map<Character, Set<State>> tMap)
	{
		transitionMap = tMap;
	}
	
	private Set<State> emptyPathMoveHelper(Set<State> seenStates)
	{
		Set<State> emptyCharMoves = transitionMap.get('\0');
		seenStates.add(this);
		if(emptyCharMoves == null)
			return emptyCharMoves;
		
		Set<State> possibleStates = new HashSet<State>(emptyCharMoves);
		
		for(State emptyCharMove : emptyCharMoves )
		{
			if(seenStates.contains(emptyCharMove))
				continue;
			Set<State> possibleStatesAfterMove = emptyCharMove.emptyPathMoveHelper(seenStates);
			if(possibleStatesAfterMove != null)
			{
				possibleStates.addAll(possibleStatesAfterMove);
			}
		}
		
		return possibleStates;
	}
	
	public Set<State> emptyPathMove()
	{
		Set<State> seenStates = new HashSet<State>();
		return emptyPathMoveHelper(seenStates);
	}
	
	public Set<State> makeMove(Character c)
	{
		Set<State> possibleStatesByChar = transitionMap.get(c);
		if(possibleStatesByChar == null)
			return possibleStatesByChar;
		
		/* Should this new HashSet() part go in a private function? */
		Set<State> possibleStates = new HashSet<State>(possibleStatesByChar);
		
		for(State possibleStateByChar : possibleStatesByChar)
		{
			Set<State> emptyMoveStates = possibleStateByChar.emptyPathMove();
			
			if(emptyMoveStates != null)
			{
				possibleStates.addAll(emptyMoveStates);
			}
		}
		
		return possibleStates;
		
	}
}

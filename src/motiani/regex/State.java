package motiani.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Should it be an inner class */
public class State {
	private Map<Character, Set<State>> transitionMap;

	State()
	{
		transitionMap = new HashMap<Character, Set<State>>();
	}
	
	State(Map<Character, Set<State>> tMap)
	{
		transitionMap = tMap;
	}
	
	void addMove(Character key, Set<State> value)
	{
		if(transitionMap.containsKey(key))
			value.addAll(transitionMap.get(key));
			
		transitionMap.put(key, value);
		
	}
	
	void addEmptyMove(Set<State> value)
	{
		addMove('\0', value);
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
	
	/* 
	 * Should we return null or empty set in case of no moves. 
	 */
	public Set<State> makeMove(Character c)
	{
		Set<State> possibleStatesByChar = transitionMap.get(c);
		if(possibleStatesByChar == null)
			return possibleStatesByChar;
		
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
	
	
	public static void main(String[] args)
	{
		State state1 = new State();
		State state2 = new State(); 
		State state3 = new State();
		State state4 = new State();
		State state5 = new State();
		State state6 = new State();
		
		System.out.println("State 1 : " + state1);
		System.out.println("State 2 : " + state2);
		System.out.println("State 3 : " + state3);
		System.out.println("State 4 : " + state4);
		System.out.println("State 5 : " + state5);
		System.out.println("State 6 : " + state6);
		
		/*Set<State> moves1 = new HashSet<State>();
		moves1.add(state2);
		
		state1.addMove('b', moves1);
		
		Set<State> moves2 = new HashSet<State>();
		moves2.add(state2);
		moves2.add(state3);
		
		state2.addMove('b', moves2);
		
		Set<State> moves = state1.makeMove('b');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);
				
		moves = state2.makeMove('b');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);
		
		moves = state2.makeMove('c');
		if(moves == null)
			System.out.println("No move" );
		else
		{
			System.out.println(moves.size());
		
			for(State move : moves)
				System.out.println(move);
		}
		
		Set<State> emptyMove = new HashSet<State>();
		emptyMove.add(state1);
		
		state3.addEmptyMove(emptyMove);
				
		moves = state2.makeMove('b');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);*/
		
		Set<State> moves1 = new HashSet<State>();
		moves1.add(state2);
		moves1.add(state3);
		
		state1.addMove('a', moves1);
		
		Set<State> moves2b = new HashSet<State>();
		moves2b.add(state4);
		
		state2.addMove('b', moves2b);
		
		Set<State> moves2 = new HashSet<State>();
		moves2.add(state4);
		
		state2.addEmptyMove(moves2);
		
		Set<State> moves3c = new HashSet<State>();
		moves3c.add(state6);
		
		state3.addMove('c', moves3c);
		
		Set<State> moves3 = new HashSet<State>();
		moves3.add(state6);
		
		state3.addEmptyMove(moves3);
		
		Set<State> moves4a = new HashSet<State>();
		moves4a.add(state4);
		
		state4.addMove('a', moves4a);
		
		Set<State> moves4 = new HashSet<State>();
		moves4.add(state5);
		
		state4.addEmptyMove(moves4);
		
		Set<State> moves5 = new HashSet<State>();
		moves5.add(state2);
		moves5.add(state1);
		
		state5.addEmptyMove(moves5);
		
		Set<State> moves6 = new HashSet<State>();
		moves6.add(state5);
		
		state6.addMove('d', moves6);
		
		Set<State> moves = state1.makeMove('a');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);
		
		moves = state3.makeMove('c');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);
		
		moves = state4.makeMove('a');
		System.out.println(moves.size());
		
		for(State move : moves)
			System.out.println(move);
	}
}

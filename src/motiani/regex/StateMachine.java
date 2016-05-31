package motiani.regex;

import java.util.HashSet;
import java.util.Set;

public class StateMachine {
	private State[] states;
	private State initialState;
	private Set<State> finalStates;
	
	private void initializeStates(int numStates)
	{
		states = new State[numStates];
		for(int i = 0; i < numStates; i++)
		{
			states[i] = new State();
		}
	}
	
	public StateMachine(State[] allStates, int initialStateIndex, int[] finalStateIndices)
	{
		states = allStates;
		setInitialState(initialStateIndex);
		addFinalStates(finalStateIndices);
	}
	
	/* Should the option to create machine without initial and final states be provided at all? */
	public StateMachine(int numStates)
	{
		initializeStates(numStates);
		initialState = null; 
		finalStates = null;
	}
	
	public StateMachine(int numStates, int initialStateIndex, int[] finalStateIndices)
	{
		initializeStates(numStates);
		setInitialState(initialStateIndex);
		addFinalStates(finalStateIndices);
	}
	
	/* 
	 * Does it make sense to make it private which would make any change to initial state 
	 * impossible after initialization
	 */
	public void setInitialState(int initialStateIndex)
	{
		initialState = states[initialStateIndex];
	}
	
	public void addFinalStates(int[] finalStateIndices)
	{
		/*
		 * If we don't provide any constructor which can make finalStates null, we won't 
		 * have to do this check, and another new HashSet. Having another new HashSet makes
		 * changing to a different Set class implementation difficult too. 
		 */
		if(finalStates == null)
			finalStates = new HashSet<State>();
		
		for(int finalStateIndex : finalStateIndices)
			finalStates.add(states[finalStateIndex]);
	}
	
	public void addFinalState(int finalStateIndex)
	{
		addFinalStates( new int[] {finalStateIndex});
	}
	
	public void addMove(int fromStateIndex, int toStateIndex, Character c ) throws Exception
	{
		if(fromStateIndex < 0 || fromStateIndex >= states.length
		|| toStateIndex < 0 || toStateIndex >= states.length)
			throw new Exception("Invalid state index.");
		
		Set<State> moves = new HashSet<State>();
		moves.add(states[toStateIndex]);
		
		states[fromStateIndex].addMove(c, moves);
	}
	
	public void addEmptyMove(int fromStateIndex, int toStateIndex) throws Exception
	{
		if(fromStateIndex < 0 || fromStateIndex >= states.length
		|| toStateIndex < 0 || toStateIndex >= states.length)
			throw new Exception("Invalid state index.");
				
		Set<State> moves = new HashSet<State>();
		moves.add(states[toStateIndex]);
		
		states[fromStateIndex].addEmptyMove(moves);
	}
	
	private boolean matchHelper(String s, int index, Set<State> currentStates)
	{
		if(index == s.length())
		{
			for(State currentState : currentStates)
			{
				if(finalStates.contains(currentState))
					return true;
			}
			
			return false;
		}
		Set<State> nextStates = new HashSet<State>();
		for(State currentState : currentStates)
		{
			Set<State> statesAfterMove = currentState.makeMove(s.charAt(index));
			if(statesAfterMove != null)
			{
				nextStates.addAll(statesAfterMove);
			}
		}
		
		if(nextStates.size() == 0)
			return false;
		
		return matchHelper(s, index + 1, nextStates);
	}
	
	public boolean match(String s)
	{
		Set<State> initialStates = new HashSet<State>();
		initialStates.add(initialState);
		
		Set<State> emptyMoveStates = initialState.emptyPathMove();
		if(emptyMoveStates != null)
		{
			initialStates.addAll(emptyMoveStates);
		}
		
		return matchHelper(s, 0, initialStates);
	}
	
	public static void main(String[] args) throws Exception
	{
		StateMachine stateMachine = new StateMachine(6, 0, new int[]{4});
		
		stateMachine.addMove(0, 1, 'a');
		stateMachine.addMove(0, 2, 'a');
		stateMachine.addMove(1, 3, 'b');
		stateMachine.addEmptyMove(1, 3);
		stateMachine.addMove(2, 5, 'c');
		stateMachine.addMove(3, 3, 'a');
		stateMachine.addEmptyMove(3, 4);
		stateMachine.addMove(5, 4, 'd');
		stateMachine.addEmptyMove(4, 1);
		stateMachine.addEmptyMove(4, 0);
		
		if(stateMachine.match("acdaac"))
			System.out.println("Yay");
		else
			System.out.println("Nay");
			
	}
	
}

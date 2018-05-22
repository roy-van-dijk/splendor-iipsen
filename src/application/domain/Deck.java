package application.domain;

import java.util.Stack;

/*
 * So far a Deck seems to just wrap around a List. Seems almost useless.
*/

public interface Deck<T> 
{
	public void add(T item);
	public T pull();
	public T top();
	public Stack<T> getAll();
	
}

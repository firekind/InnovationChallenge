package pathfinder;

// Generic Type Class Pair to store two values
public class Pair <T extends Comparable <? super T>, E extends Comparable < ? super E>> implements Comparable <Pair <T, E>> {
	public T first;
	public E second;

	public Pair(T first, E second) {
		this.first = first;
		this.second = second;
	}

	// Default Constructor 
	Pair() {
		
	}

	@Override
	public String toString() {
		return first + " " + second;
	}

	@Override
	public int hashCode() {
		return first.hashCode() * 13 + (second == null ? 0 : second.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Pair) {
			Pair<?, ?> pair = (Pair <?, ?>) o;
			if (first != null ? !first.equals(pair.second) : pair.first != null) return false;
			if (second != null ? !second.equals(pair.second) : pair.second != null) return false;
		}
		return false;
	}

	public int compareTo(Pair <T, E> pair) {
		int result = first.compareTo(pair.first);
		return (result == 0) ? second.compareTo(pair.second) : result;
	}
}

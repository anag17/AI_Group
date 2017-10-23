package searchclient;

import java.util.Comparator;

import searchclient.NotImplementedException;

public abstract class Heuristic implements Comparator<Node> {

	int[] goalPos;

	public Heuristic(Node initialState) {
		// Here's a chance to pre-process the static parts of the level.
		char[][] goals = SearchClient.goals;
		goalPos = new int[2];
		for(int i = 0; i < goals.length; i++) {
			for(int j = 0; j < goals[0].length; j++) {
				if('a' <= goals[i][j] && goals[i][j] <= 'z') {
					goalPos[0] = i;
					goalPos[1] = j;
				}
			}
		}
	}

	public int h(Node n) {
		return 4;
	}

	public abstract int f(Node n);

	@Override
	public int compare(Node n1, Node n2) {
		return this.f(n1) - this.f(n2);
	}

	public static class AStar extends Heuristic {
		public AStar(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return n.g() + this.h(n);
		}

		@Override
		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private int W;

		public WeightedAStar(Node initialState, int W) {
			super(initialState);
			this.W = W;
		}

		@Override
		public int f(Node n) {
			return n.g() + this.W * this.h(n);
		}

		@Override
		public String toString() {
			return String.format("WA*(%d) evaluation", this.W);
		}
	}

	public static class Greedy extends Heuristic {
		public Greedy(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return this.h(n);
		}

		@Override
		public String toString() {
			return "Greedy evaluation";
		}
	}
}

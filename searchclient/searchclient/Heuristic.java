package searchclient;

import java.util.Comparator;

import searchclient.NotImplementedException;

public abstract class Heuristic implements Comparator<Node> {

	private int goalPosX;
	private int goalPosY;

	public Heuristic(Node initialState) {
		// Here's a chance to pre-process the static parts of the level.
		char[][] goals = SearchClient.goals;
		for(int i = 0; i < goals.length; i++) {
			for(int j = 0; j < goals[0].length; j++) {
				if('a' <= goals[i][j] && goals[i][j] <= 'z') {
					goalPosY = i;
					goalPosX = j;
				}
			}
		}
	}

	public int h(Node n) {
		char[][] boxes = n.boxes;
		int boxX = 0;
		int boxY = 0;
		for(int i = 0; i < boxes.length; i++) {
			for(int j = 0; j < boxes[0].length; j++) {
				if('A' <= boxes[i][j] && boxes[i][j] <= 'Z') {
					boxY = i;
					boxX = j;
				}
			}
		}
		return distance(boxX, boxY, goalPosX, goalPosY);
	}

	private int distance(int x1, int y1, int x2, int y2) {
		double dist = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
		return (int)dist;
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

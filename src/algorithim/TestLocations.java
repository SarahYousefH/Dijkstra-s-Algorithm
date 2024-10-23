package algorithim;

import DataStructures.SingleLinkedList;

public class TestLocations {

	public static void main(String[] args) {

		System.out.println("Implementation 2 \n\n");

		
		/**
		 * test case 1
		 */
		Graph graph1 = new Graph(7);

		int city0 = graph1.addVertex("city0", 100, 100, true);

		int city1 = graph1.addVertex("city1", 5, 8, true);
		int city2 = graph1.addVertex("city2", -4, 8, true);
		int city3 = graph1.addVertex("city3", -2, 5, true);
		int city4 = graph1.addVertex("city4", 5, 4, true);
		int city5 = graph1.addVertex("city5", -4, 3, true);
		int city6 = graph1.addVertex("city6", -2, -2, true);

		// add adjacents

		graph1.addAdjacent(city1, city2);
		graph1.addAdjacent(city1, city4);

		graph1.addAdjacent(city2, city3);
		graph1.addAdjacent(city2, city5);

		graph1.addAdjacent(city3, city4);
		graph1.addAdjacent(city3, city6);
		graph1.addAdjacent(city3, city5);

		graph1.addAdjacent(city4, city6);

		graph1.addAdjacent(city5, city6);

		/*System.out.println(graph1);
		System.out.println("\n*********\n");

		double result = graph1.dijkstra("city1", "city6");
		
		System.out.println("\n*********\n");

		SingleLinkedList<Integer> pathResult = graph1.dijkstraPath("city1", "city6");

		System.out.println("\nshortest distance is " + result);
		System.out.println("\n*********\n");

		System.out.println("\nshortest path is " + pathResult.toString());

		
		
		System.out.println("\n*********\n");
		
		result= graph1.dijkstra("city5", "city1");
		
		System.out.println("\nshortest distance is " + result);*/

		
		/**
		 * test case 2
		 */
		
		
		System.out.println("\n*********\n");

		
		System.out.println("\nGraph 3 \n\n");


		Graph graph3 = new Graph(10);

		//graph3.addVertex("city0", 100, 100, true);

	
		int cityB = graph3.addVertex("CityB", 2, 3, true);
	
		int cityD = graph3.addVertex("CityD", -3, 1, true);
		int cityE = graph3.addVertex("CityE", 1, -2, true);
		int cityF = graph3.addVertex("CityF", 5, 5, true);
	
		int cityH = graph3.addVertex("CityH", 3, -3, true);	
		int cityC = graph3.addVertex("CityC", -1, 4, true);
		
		int cityG = graph3.addVertex("CityG", -4, 2, true);
		

		int cityI = graph3.addVertex("CityI", -2, -4, true);
		int cityJ = graph3.addVertex("CityJ", 6, 0, true);
	
		int cityA = graph3.addVertex("CityA", 0, 0, true);
		
		// Add adjacents
		graph3.addAdjacent(cityA, cityB);
		graph3.addAdjacent(cityA, cityC);
		
		graph3.addAdjacent(cityB, cityD);
		graph3.addAdjacent(cityB, cityE);
		graph3.addAdjacent(cityB, cityF);

		
		graph3.addAdjacent(cityC, cityD);
		graph3.addAdjacent(cityC, cityB);

		
		graph3.addAdjacent(cityD, cityE);
		graph3.addAdjacent(cityD, cityG);

		
		graph3.addAdjacent(cityE, cityH);

		
		graph3.addAdjacent(cityF, cityG);
		graph3.addAdjacent(cityF, cityH);
		graph3.addAdjacent(cityF, cityB);

		
		graph3.addAdjacent(cityG, cityH);
		
		graph3.addAdjacent(cityH, cityI);
		
		graph3.addAdjacent(cityI, cityJ);
		graph3.addAdjacent(cityI, cityF);

		graph3.addAdjacent(cityJ, cityI);
		graph3.addAdjacent(cityJ, cityB);


		System.out.println(graph3);
		System.out.println("\n*********\n");

		double result2 = graph3.dijkstra("CityI", "CityG");
		
		System.out.println("\n*********\n");

		//SingleLinkedList<Integer> pathResult2 = graph3.dijkstraPath("CityI", "CityG");

		System.out.println("\nshortest distance is " + result2);
		System.out.println("\n*********\n");

		//System.out.println("\nshortest path is " + pathResult2.toString());

	}
}

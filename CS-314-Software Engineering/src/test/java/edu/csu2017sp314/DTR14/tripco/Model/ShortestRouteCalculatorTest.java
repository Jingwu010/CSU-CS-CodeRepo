package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.Arrays;

public class ShortestRouteCalculatorTest{
	
	private LocationList locList;

	@Test
	public void testConstructor() {
		locList = new LocationList();
		new ShortestRouteCalculator(locList, 0, false);
	}

	@Test
	public void testFindBestNNTour() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1", "Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2", "", ""));
		locList.addLocation(new Location("C", "20", "30", "3","", ""));
		
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		
		assertTrue("wrong route", tests[0][0] == 0);
		assertTrue("wrong route", tests[1][0] == 2);
		assertTrue("wrong route", tests[2][0] == 1);
		assertTrue("wrong route", tests[3][0] == 0);
	}
	
	@Test
	public void testGetFinalRoute() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1","Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2","", ""));
		locList.addLocation(new Location("C", "20", "30", "3","", ""));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		assertTrue(tests.length == 4);
		assertTrue(tests[0].length == 2);
	}

	@Test
	public void testGetFinalDis() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1","Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2","", ""));
		locList.addLocation(new Location("C", "20", "30", "3", "", ""));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		assertTrue((int)Math.ceil(tests[tests.length - 1][tests[0].length - 1]) == src.getFinalDis());
	}

	@Test
	public void testFindBest2Opt() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "10", "10", "1","Hello", ""));
		locList.addLocation(new Location("B", "20.1", "20", "1","", ""));
		locList.addLocation(new Location("C", "19.9", "20", "1","", ""));
		locList.addLocation(new Location("D", "30", "30", "1","", ""));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] test1 = src.getFinalRoute();
		ShortestRouteCalculator src2 = new ShortestRouteCalculator(locList, 0, false);
		src2.findBestNearestNeighbor(true, false);
		
		int[][] test2 = src2.getFinalRoute();

		assertTrue(test1[2][1] != test2[2][1]);

	}
	
	@Test
	public void testFind2OptSwapDistance() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);

		src.debug = true;
		src.findBestNearestNeighbor(false, false);
		
		src.copyRoute(src.final_route, src.test_route);
		assertFalse(src.find2OptSwapDistance(1, 3));
		int dis = src.debugVal;
		int correctDis = 0;
		
		correctDis += src.dis_matrix[0][4];
		correctDis += src.dis_matrix[4][1];
		correctDis += src.dis_matrix[1][2];
		correctDis += src.dis_matrix[2][3];
		correctDis += src.dis_matrix[3][0];

		assertTrue((dis - correctDis) == 0);

		locList = new LocationList();
		locList.addLocation(new Location("A", "10", "10", "1","Hello", ""));
		locList.addLocation(new Location("B", "20.1", "20", "1","", ""));
		locList.addLocation(new Location("C", "19.9", "20", "1","", ""));
		locList.addLocation(new Location("D", "30", "30", "1","", ""));

		src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		src.copyRoute(src.final_route, src.test_route);
		assertTrue(src.find2OptSwapDistance(2, 4));
	}
	
	@Test
	public void testDo2OptSwap() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		src.opt_route = new int[6][2];
		src.test_route = new int[6][2];
		
		for (int i = 0; i < 5; i++) {
			src.test_route[i][0] = i;
		}
		src.test_route[5][0] = 0;
		
		src.do2OptSwap(1, 5);
		assertTrue(src.opt_route[0][0] == 0);
		assertTrue(src.opt_route[1][0] == 4);
		assertTrue(src.opt_route[2][0] == 3);
		assertTrue(src.opt_route[3][0] == 2);
		assertTrue(src.opt_route[4][0] == 1);
		assertTrue(src.opt_route[5][0] == 0);
	}
	
	@Test
	public void testRebuildDistances() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "0"));
		locList.addLocation(new Location("B", "1", "0"));
		locList.addLocation(new Location("C", "2", "0"));
		locList.addLocation(new Location("D", "3", "0"));
		locList.addLocation(new Location("E", "4", "0"));
		locList.addLocation(new Location("F", "5", "0"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		for (int i = 0; i < 6; i++) {
			src.test_route[i][0] = i;
		}
		src.test_route[6][0] = 0;
		int incr = 69;
		int dist = 69;
		src.rebuildDistances(src.test_route);
		assertArrayEquals(new int[][] {{0, 0}, {1, dist}, {2, dist + incr},
				{3, dist + incr * 2}, {4, dist + incr * 3}, {5, dist + incr * 4},
				{0, dist + incr * 4 + src.getDistance(5, 0)}}, src.test_route);
	}

	@Test
	public void testCopyElements() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);

		src.findBestNearestNeighbor(false, false);

		src.copyElements(3, 0, 3);
		src.copyElements(0, 3, 3);

		assertArrayEquals(new int[] {2, 3, 0, 0, 1, 4}, src.temp);

		src.copyElements(0, 2, 4);

		assertArrayEquals(new int[] {2, 3, 0, 1, 4, 2}, src.temp);
	}

	@Test
	public void testReverseElements() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);

		src.findBestNearestNeighbor(false, false);

		src.copyElements(0, 0, src.temp.length);
		src.temp[0] = 9;
		assertArrayEquals(new int[] {9, 1, 4, 2, 3, 0}, src.temp);
		src.reverseElements(0, src.temp.length);
		assertArrayEquals(new int[] {0, 3, 2, 4, 1, 9}, src.temp);
		src.reverseElements(1, src.temp.length);
		assertArrayEquals(new int[] {0, 9, 1, 4, 2, 3}, src.temp);
		src.reverseElements(1, 2);
		assertArrayEquals(new int[] {0, 9, 1, 4, 2, 3}, src.temp);
		src.reverseElements(1, 3);
		assertArrayEquals(new int[] {0, 1, 9, 4, 2, 3}, src.temp);
		src.reverseElements(0, src.temp.length - 1);
		assertArrayEquals(new int[] {2, 4, 9, 1, 0, 3}, src.temp);
	}

	@Test
	public void testCheckPossibilities() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "6"));
		locList.addLocation(new Location("B", "1", "6"));
		locList.addLocation(new Location("C", "2", "6"));
		locList.addLocation(new Location("D", "3", "6"));
		locList.addLocation(new Location("E", "4", "6"));
		locList.addLocation(new Location("F", "5", "6"));
		locList.addLocation(new Location("G", "6", "6"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);

		src.findBestNearestNeighbor(false, false);
		int compare = 3 * 69;
		assertTrue(compare == src.checkPossibility0(0, 2, 4));
	}

	@Test
	public void testDo3OptSwap1() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "6"));
		locList.addLocation(new Location("B", "1", "6"));
		locList.addLocation(new Location("C", "2", "6"));
		locList.addLocation(new Location("D", "3", "6"));
		locList.addLocation(new Location("E", "4", "6"));
		locList.addLocation(new Location("F", "5", "6"));
		locList.addLocation(new Location("G", "6", "6"));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		src.do3OptSwap(0, 3, 5, 0);
		assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0}, src.temp);
		src.do3OptSwap(0, 3, 5, 5);
		assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0}, src.temp);
		src.do3OptSwap(0, 3, 5, 1);
		assertArrayEquals(new int[] {0, 3, 2, 1, 5, 4, 6, 0}, src.temp);
		assertArrayEquals(new int[][] {{0, 0}, {3, 207}, {2, 276}, {1, 345}, {5, 621}, {4, 690}, {6, 828}, {0, 1243}}, src.test_route);
		System.out.println(Arrays.deepToString(src.test_route));
	}

	@Test
	public void testDo3OptSwap2() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "6"));
		locList.addLocation(new Location("B", "1", "6"));
		locList.addLocation(new Location("C", "2", "6"));
		locList.addLocation(new Location("D", "3", "6"));
		locList.addLocation(new Location("E", "4", "6"));
		locList.addLocation(new Location("F", "5", "6"));
		locList.addLocation(new Location("G", "6", "6"));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);

		src.do3OptSwap(0, 3, 5, 2);
		System.out.println(Arrays.toString(src.temp));
		assertArrayEquals(new int[] {0, 4, 5, 1, 2, 3, 6, 0}, src.temp);
	}

	@Test
	public void testDo3OptSwap3() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "6"));
		locList.addLocation(new Location("B", "1", "6"));
		locList.addLocation(new Location("C", "2", "6"));
		locList.addLocation(new Location("D", "3", "6"));
		locList.addLocation(new Location("E", "4", "6"));
		locList.addLocation(new Location("F", "5", "6"));
		locList.addLocation(new Location("G", "6", "6"));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);

		src.do3OptSwap(0, 3, 5, 3);
		System.out.println(Arrays.toString(src.temp));
		assertArrayEquals(new int[] {0, 4, 5, 3, 2, 1, 6, 0}, src.temp);
	}

	@Test
	public void testDo3OptSwap4() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "0", "6"));
		locList.addLocation(new Location("B", "1", "6"));
		locList.addLocation(new Location("C", "2", "6"));
		locList.addLocation(new Location("D", "3", "6"));
		locList.addLocation(new Location("E", "4", "6"));
		locList.addLocation(new Location("F", "5", "6"));
		locList.addLocation(new Location("G", "6", "6"));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);

		src.do3OptSwap(0, 3, 5, 4);
		System.out.println(Arrays.toString(src.temp));
		assertArrayEquals(new int[] {0, 5, 4, 1, 2, 3, 6, 0}, src.temp);
	}
	
	@Test
	public void testWorldWrapping() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-180"));
		locList.addLocation(new Location("B", "39.1177", "180"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		double dis = src.getDistance(0, 1);
		assertTrue(dis < 0.0001 && dis > -0.0001);
	}
}


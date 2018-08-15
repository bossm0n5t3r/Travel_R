package main;

import service.SearchService;

public class DirectSearch {
	public static void main(String[] args) throws Exception {
		SearchService ss = new SearchService();
		ss.searchDirect("교통", "식당");
	}
}

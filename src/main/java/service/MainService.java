package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dbConnect.ConnBlogs;

public class MainService {
	static ConnBlogs cb = new ConnBlogs();
	static ArrayList <ArrayList <String>> TRAFF = cb.blogsOrderByType("traff");
	static ArrayList <ArrayList <String>> FOOD = cb.blogsOrderByType("food");
	static ArrayList <ArrayList <String>> TOUR = cb.blogsOrderByType("tour");
	static ArrayList <ArrayList <String>> STAY = cb.blogsOrderByType("stay");
	private SearchService ss = new SearchService();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private int n;
	private String [] nArr;
	private String [] strArr;

	public void service() throws Exception {
		boolean flag = true;
		while (flag) {
			System.out.println("=======================================================");
			System.out.println("환영합니다!");
			System.out.println("1. 키워드별 출력\t2.키워드 연관 출력\t3.종료");
			System.out.print("입력 : ");
			n = Integer.parseInt(br.readLine());
			switch (n) {
			case 1:
				printByKeywords();
				break;
			case 2:
				printRelatedByKeywords();
				break;
			case 3:
				System.out.println("종료합니다.");
				flag = false;
				br.close();
				break;
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
	}

	public void printByKeywords() throws Exception {
		boolean flag = true;
		while (flag) {
			System.out.println("[키워드별 출력]");
			System.out.println("관심있는 키워드를 숫자로 입력해주세요.");
			System.out.println("1. 교통  2.음식  3.관광지  4.숙박  5.직접 검색  6.종료");
			System.out.print("입력 : ");
			n = Integer.parseInt(br.readLine());
			switch(n) {
			case 1:
				printBlogs("traff", TRAFF);
				break;
			case 2:
				printBlogs("food", FOOD);
				break;
			case 3:
				printBlogs("tour", TOUR);
				break;
			case 4:
				printBlogs("stay", STAY);
				break;
			case 5:
				boolean tempFlag = true;
				while (tempFlag) {
					System.out.println("[직접 검색]\n1.단어 입력  2.종료");
					n = Integer.parseInt(br.readLine());
					switch (n) {
					case 1:
						System.out.println("[직접 검색] 찾고싶은 단어를 띄어쓰기로 구분해서 입력해주세요.");
						System.out.print("입력 : ");
						strArr = br.readLine().trim().split(" ");
						ss.searchDirect(strArr);
						break;
					case 2:
						tempFlag = false;
						break;
					default:
						System.out.println("잘못 입력하셨습니다.");
					}
				}
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못입력하셨습니다.");
			}
		}
	}
	
	public void printRelatedByKeywords() throws Exception {
		boolean flag = true;
		while (flag) {
			System.out.println("키워드 연관 출력입니다.");
			System.out.println("1.입력  2.되돌아가기");
			n = Integer.parseInt(br.readLine());
			switch (n) {
			case 1:
				System.out.println("관심있는 키워드들을 순서대로 입력해주세요. ex) 3 1 2");
				System.out.println("1.교통  2.음식  3.관광지  4.숙박  5.종료");
				System.out.print("입력 : ");
				nArr = br.readLine().trim().split(" ");
				strArr = new String [nArr.length];
				for (int i = 0; i < nArr.length; i++) {
					int temp = Integer.parseInt(nArr[i]);
					if (temp == 1)
						strArr[i] = "traff";
					else if (temp == 2)
						strArr[i] = "food";
					else if (temp == 3)
						strArr[i] = "tour";
					else if (temp == 4)
						strArr[i] = "stay";
				}
				ArrayList <ArrayList <String>> result = cb.blogsOrderBySpecialType(strArr);
				printBlogs("result", result);
				break;
			case 2:
				flag = false;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	public void printBlogs(String dataName, ArrayList <ArrayList <String>> data) throws Exception {
		int index = 0;
		ArrayList <String> subject = data.get(0);
		ArrayList <String> url = data.get(1);
		boolean tempFlag = true;
		boolean lastFlag = false;
		while (tempFlag) {
			System.out.println("[" + dataName + "]");
			System.out.println("전체 블로그는 " + url.size() + "개 입니다.");
			System.out.println("현재 페이지는 " + (index/3 + 1) + "페이지 입니다.");
			if (lastFlag)
				System.out.println("마지막 페이지 입니다.");
			System.out.println("1. " + subject.get(index));
			System.out.println("2. " + subject.get(index + 1));
			System.out.println("3. " + subject.get(index + 2));
			System.out.println("4. 더보기");
			System.out.println("5. 앞으로 가기");
			System.out.println("6. 돌아가기");
			System.out.print("입력 : ");
			n = Integer.parseInt(br.readLine());
			switch (n) {
			case 1:
				ss.searchUrl(url.get(index));
				break;
			case 2:
				ss.searchUrl(url.get(index + 1));
				break;
			case 3:
				ss.searchUrl(url.get(index + 2));
				break;
			case 4:
				if (index < url.size() - 5)
					index += 3;
				else {
					index = url.size() - 3;
					lastFlag = true;
				}
				break;
			case 5:
				if (index == 0)
					System.out.println("가장 첫 페이지입니다.");
				else
					index -= 3;
				break;
			case 6:
				tempFlag = false;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
}

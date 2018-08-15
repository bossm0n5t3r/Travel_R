package classifyBlog;

import java.util.ArrayList;

import dbConnect.ConnKeywords;
import dbConnect.ConnRawKeywords;
import vo.Blogs;
import vo.RawBlogs;

public class ClassifyBlog {
	private Blogs b = new Blogs();
	private ConnRawKeywords crk = new ConnRawKeywords();
	static ConnKeywords ck = new ConnKeywords();
	static ArrayList <String> TRAFF = ck.keywordsSet("traff");
	static ArrayList <String> FOOD = ck.keywordsSet("food");
	static ArrayList <String> TOUR = ck.keywordsSet("tour");
	static ArrayList <String> STAY = ck.keywordsSet("stay");
	static ArrayList <String> ETC = ck.keywordsSet("etc");
	
	public Blogs blogAnalysis(RawBlogs r) throws Exception {
		b.setBlog_num(r.getBlog_num());
		b.setR_date(r.getR_date());
		b.setSubject(r.getSubject());
		b.setUrl(r.getUrl());
		b.setWriter(r.getWriter());
		b.setContent1(r.getContent1());
		int [] scores = keywordsScore(crk.selectRawKeywords(r.getBlog_num()));
		System.out.println(crk.selectRawKeywords(r.getBlog_num()));
		b.setTraff(scores[0]);
		b.setFood(scores[1]);
		b.setTour(scores[2]);
		b.setStay(scores[3]);
		b.setEtc(scores[4]);
		return b;
	}
	
	public int [] keywordsScore(ArrayList <String> keywords) {
		int [] scores = new int [5];
		for (int index = 0; index < keywords.size(); index++) {
			String keyword = keywords.get(index);
			if (TRAFF.contains(keyword)) {
				scores[0] += (10 - index);
				System.out.println(keyword + " : " + (10-index));
			} else if (FOOD.contains(keyword)) {
				scores[1] += (10 - index);
				System.out.println(keyword + " : " + (10-index));
			} else if (TOUR.contains(keyword)) {
				scores[2] += (10 - index);
				System.out.println(keyword + " : " + (10-index));
			} else if (STAY.contains(keyword)) {
				scores[3] += (10 - index);
				System.out.println(keyword + " : " + (10-index));
			} else if (ETC.contains(keyword)) {
				scores[4] += (10 - index);
				System.out.println(keyword + " : " + (10-index));
			}
		}
		return scores;
	}
}

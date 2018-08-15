package main;

import java.util.ArrayList;

import classifyBlog.ClassifyBlog;
import dbConnect.ConnBlogs;
import dbConnect.ConnRawBlogs;
import vo.Blogs;
import vo.RawBlogs;

public class InsertBlogs {
	public static void main(String[] args) throws Exception {
		ConnBlogs cb = new ConnBlogs();
		ConnRawBlogs crb = new ConnRawBlogs();
		ClassifyBlog csb = new ClassifyBlog();
		
		ArrayList <RawBlogs> tempData = crb.selectAll();
		System.out.println("raw_data ok!");
				
		for (RawBlogs r : tempData) {
			Blogs b = csb.blogAnalysis(r);
			cb.insert(b);
			System.out.println("+++");
		}
	}
}

package blogCrawling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbConnect.ConnRawBlogs;
import vo.RawBlogs;

/**
 * Actual crawling test for keywords
 * 
 * Separation complete for url, writer, r_date, subject and contents
 * 
 * selectContents(fileName) -> All contents are saved in fileName.txt file
 * 
 * Reference -> https://blog.naver.com/tndyd5390/220996222164
 * 
 * @author hoon
 * 
 */

//네이버 블로그 더보기했을때 블로그들 가져오기
public class BlogCrawler {
	private RawBlogs raw;
	private ConnRawBlogs cr = new ConnRawBlogs();

	public void naverBlogCrawling(int startPage, int numOfPage, String... keywords) throws Exception {
		String searchWord = "";
		for (int i = 0; i < keywords.length; i++) {
			if (i == keywords.length - 1) {
				searchWord += keywords[i];
				break;
			}
			searchWord += keywords[i] + "+";
		}
		String naverFront = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=";
		String naverEnd = "&sm=tab_pge&srchby=all&st=sim&where=post&start=";
		int page = startPage;
		while (page <= numOfPage) {
			String n_url = naverFront + searchWord + naverEnd + page;
			Document naver_blog = Jsoup.connect(n_url).header("Accept", "text/html, application/javascript,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "ko-KR")
					.header("Connection", "Keep-Alive").get();
			Elements blog_link = naver_blog.select("a.sh_blog_title._sp_each_url._sp_each_title");
			for (Element element : blog_link) {
				String n = element.attr("href");
				if (n.contains("blog.naver.com")) {
					Document doc = Jsoup.connect(n).get();
					Elements frame = doc.select("frame#mainFrame");
					String f = frame.attr("src");
					Document blog = Jsoup.connect("http://blog.naver.com" + f).get();
					String blognum[] = f.split("&");
					String num[] = blognum[1].split("=");

					raw = new RawBlogs();

					// url
					raw.setUrl("http://blog.naver.com" + f);

					// writer
					Elements nickname = blog.select("div#buddyRecommendLayer .nick");
					raw.setWriter(nickname.text());

					// r_date
					Elements time = blog.select("div.blog2_container > span.se_publishDate.pcol2");
					if (time.text().length() == 0) {
						time = blog.select(
								"#printPost1 > tbody > tr > td.bcc > table > tbody > tr > td > p.date.fil5.pcol2._postAddDate");
					}
					raw.setR_date(time.text());

					// subject
					Elements title = blog.select("head > title");
					raw.setSubject(title.text());

					// contents
					Elements body = blog.select("div#post-view" + num[1]);
					String[] contentsArr = getMaxByteStringArray(body.text(), 3500, 3);

					raw.setContent1(contentsArr[0]);
					raw.setContent2(contentsArr[1]);
					raw.setContent3(contentsArr[2]);

					System.out.println(raw.toString());
					cr.insert(raw);
					System.out.println("+++" + page);
				}

			}
			page += 10;
		}
	}

	public static String[] getMaxByteStringArray(String str, int maxLen, int maxArrays) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> strList = new ArrayList<String>();
		int curLen = 0;
		String curChar;
		for (int i = 0; i < str.length(); i++) {
			curChar = str.substring(i, i + 1);
			curLen += curChar.getBytes().length;
			if (curLen > maxLen) {
				if (maxArrays == -1 || strList.size() <= maxArrays - 1) {
					strList.add(sb.toString());
					sb = new StringBuilder();
					curLen = 0;
					i--;
				} else
					break;
			} else
				sb.append(curChar);
		}
		strList.add(sb.toString());
		String[] strArr = new String[maxArrays];
		for (int i = 0; i < strList.size(); i++) {
			if (i == maxArrays)
				break;
			strArr[i] = strList.get(i).toString();
		}
		return strArr;
	}

	public void selectContents(String fileName) throws Exception {
		String filePath = "src/main/resources/" + fileName + ".txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
		HashMap<Integer, String> contentsAll = cr.selectContents();
		for (String data : contentsAll.values()) {
			bw.write(data + "\n");
			System.out.println("+++");
		}
		bw.close();
		System.out.println("End");
	}
}

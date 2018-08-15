package vo;

public class RawBlogs {
	private int blog_num;
	private String r_date;
	private String subject;
	private String url;
	private String writer;
	private String content1;
	private String content2;
	private String content3;

	public RawBlogs() {
		super();
	}

	public RawBlogs(int blog_num, String r_date, String subject, String url, String writer, String content1,
			String content2, String content3) {
		super();
		this.blog_num = blog_num;
		this.r_date = r_date;
		this.subject = subject;
		this.url = url;
		this.writer = writer;
		this.content1 = content1;
		this.content2 = content2;
		this.content3 = content3;
	}

	public int getBlog_num() {
		return blog_num;
	}

	public void setBlog_num(int blog_num) {
		this.blog_num = blog_num;
	}

	public String getR_date() {
		return r_date;
	}

	public void setR_date(String r_date) {
		this.r_date = r_date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String content3) {
		this.content3 = content3;
	}

	@Override
	public String toString() {
		return "RawBlogs [blog_num=" + blog_num + ", r_date=" + r_date + ", subject=" + subject + ", url=" + url
				+ ", writer=" + writer + ", content1=" + content1 + ", content2=" + content2 + ", content3=" + content3
				+ "]";
	}

}

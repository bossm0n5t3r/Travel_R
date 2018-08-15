package vo;

public class Blogs {
	private int blog_num;
	private String r_date;
	private String subject;
	private String url;
	private String writer;
	private String content1;
	private int traff = 0;
	private int food = 0;
	private int tour = 0;
	private int stay = 0;
	private int etc = 0;

	public Blogs() {
		super();
	}

	public Blogs(int blog_num, String r_date, String subject, String url, String writer, String content1, int traff,
			int food, int tour, int stay, int etc) {
		super();
		this.blog_num = blog_num;
		this.r_date = r_date;
		this.subject = subject;
		this.url = url;
		this.writer = writer;
		this.content1 = content1;
		this.traff = traff;
		this.food = food;
		this.tour = tour;
		this.stay = stay;
		this.etc = etc;
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

	public int getTraff() {
		return traff;
	}

	public void setTraff(int traff) {
		this.traff = traff;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public int getStay() {
		return stay;
	}

	public void setStay(int stay) {
		this.stay = stay;
	}

	public int getEtc() {
		return etc;
	}

	public void setEtc(int etc) {
		this.etc = etc;
	}

	@Override
	public String toString() {
		return "Blogs [blog_num=" + blog_num + ", r_date=" + r_date + ", subject=" + subject + ", url=" + url
				+ ", writer=" + writer + ", content1=" + content1 + ", traff=" + traff + ", food=" + food + ", tour="
				+ tour + ", stay=" + stay + ", etc=" + etc + "]";
	}

}

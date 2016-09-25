package helpers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MetaParser {

	private String image;
	private String video;
	private String title;
	private String description;
	private Document doc;
	private boolean hasDoc;
	private boolean twitterOnly;
	private String url;
	
	public MetaParser(String url) {
		this.url = url;
		fetchDoc();
	}
	
	public void fetchDoc() {
		try {
			doc = Jsoup.connect(url).get();
			hasDoc = true;
		} catch (IOException e) {
			hasDoc = false;
		}
	}

	public boolean hasMeta() {
		return doc.select("meta").hasAttr("property");
	}
	
	public String parseVideo() {
		video = doc.select("meta[property=og:video]")
				   .attr("content");
		return video;
	}

	public String parseDescription() {
		description = doc.select("meta[property=og:description]")
				.attr("content");
		return description;
	}
	
	public String parseTitle() {
		return doc.title();
	}
	public String parseImage() {
		image = doc.select("meta[property=og:image]")
				   .attr("content");
		return image;
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public boolean isHasDoc() {
		return hasDoc;
	}

	public void setHasDoc(boolean hasDoc) {
		this.hasDoc = hasDoc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void main(String[] args) {
		MetaParser mp = new MetaParser("https://www.edx.org/course/agile-development-using-ruby-rails-uc-berkeleyx-cs169-1x");
//		System.out.println();
//		System.out.println(mp.hasMeta());
//		mp.parseVideo();
//		System.out.println("VIDEO:"+mp.getVideo()+"...");
//		System.out.println(mp.parseDescription());
		System.out.println(mp.parseTitle());
//		System.out.println(mp.parseImage());
	}
}

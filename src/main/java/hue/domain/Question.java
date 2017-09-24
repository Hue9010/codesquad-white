package hue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=20)
	private String writer;
	
	@Column(nullable=false, length=20)
	private String title;

	@Column(nullable=false, length=20)
	private String contents;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void update(Question question) {
		contents = question.getContents();
		title = question.getTitle();
	}
}

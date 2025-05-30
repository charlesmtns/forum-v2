package br.com.alura.forum.controller.dto.output;

import java.time.Instant;

import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;

public class TopicOutputDto {

	private Long id;
	private String shortDescription;
	private String content;
	private TopicStatus status;
	private int numberOfResponses;
	private Instant creationInstant;
	private Instant lastUpdate;

	private String ownerName;
	private String courseName;
	private String subcategoryName;
	private String categoryName;

	public TopicOutputDto(Topic topic) {

		this.id = topic.getId();
		this.shortDescription = topic.getShortDescription();
		this.content = topic.getContent();
		this.status = topic.getStatus();
		this.numberOfResponses = topic.getNumberOfAnswers();
		this.creationInstant = topic.getCreationInstant();
		this.lastUpdate = topic.getLastUpdate();
		this.ownerName = topic.getOwner().getName();
		this.courseName = topic.getCourse().getName();
		this.subcategoryName = topic.getCourse().getSubcategoryName();
		this.categoryName = topic.getCourse().getCategoryName();
	}

	public Long getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getContent() {
		return content;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public int getNumberOfResponses() {
		return numberOfResponses;
	}

	public Instant getCreationInstant() {
		return creationInstant;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
}

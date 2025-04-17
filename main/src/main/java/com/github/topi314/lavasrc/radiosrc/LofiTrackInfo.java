package com.github.topi314.lavasrc.radiosrc;

import java.time.Instant;
import java.time.OffsetDateTime;

public class LofiTrackInfo {
	private final String id;
	private final String slug;
	private final String url;
	private final String artists;
	private final String title;
	private final String image;
	private final String iSrc;
	private final double duration;
	private final OffsetDateTime startTime;
	private final OffsetDateTime endTime;

	public LofiTrackInfo(String id, String slug, String url, String artists, String title, String image, String iSrc, double duration, OffsetDateTime startTime, OffsetDateTime endTime) {
		this.id = id;
		this.slug = slug;
		this.url = url;
		this.artists = artists;
		this.title = title;
		this.image = image;
		this.duration = duration;
		this.iSrc = iSrc;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public String getSlug() {
		return slug;
	}

	public String getUrl() {
		return url;
	}

	public String getArtists() {
		return artists;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public double getDuration() {
		return duration;
	}

	public String getISrc() {
		return iSrc;
	}

	public OffsetDateTime getStartTime() {
		return startTime;
	}

	public OffsetDateTime getEndTime() {
		return endTime;
	}

	public long getCurrentTime() {
		return Instant.now().minusSeconds(startTime.toEpochSecond()).toEpochMilli();
	}

	@Override
	public String toString() {
		return "PlaylistItem{" +
			"id='" + id + '\'' +
			", slug='" + slug + '\'' +
			", url='" + url + '\'' +
			", artists='" + artists + '\'' +
			", title='" + title + '\'' +
			", image='" + image + '\'' +
			", duration=" + duration +
			", startTime=" + startTime +
			", endTime=" + endTime +
			'}';
	}
}
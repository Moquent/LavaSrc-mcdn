package com.github.topi314.lavasrc.radiosrc;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpClientTools;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpConfigurable;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterfaceManager;
import com.sedmelluq.discord.lavaplayer.track.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class LofiRadioSrcAudioManager implements HttpConfigurable, AudioSourceManager {
	public static final String PREFIX = "lofiradio:";
	private String audioUrl, allUrl, stationUrl;
	private String name;

	private HttpInterfaceManager httpInterfaceManager;
	private static final Logger log = LoggerFactory.getLogger(LofiRadioSrcAudioManager.class);
	private LofiRadioService service;

	public LofiRadioSrcAudioManager(String audioUrl, String allUrl, String stationUrl, @Nullable String name, String user, String pass) {
		this.name = name;
		this.audioUrl = audioUrl;
		this.allUrl = allUrl;
		this.stationUrl = stationUrl;
		this.httpInterfaceManager = HttpClientTools.createCookielessThreadLocalManager();
		this.httpInterfaceManager.setHttpContextFilter(new LofiHttpContextFilter(audioUrl, user, pass));
		service = new LofiRadioService(allUrl, stationUrl, this);
	}

	@Override
	public String getSourceName() {
		return name != null ? name : "lofiradio";
	}

	@Override
	public AudioItem loadItem(AudioPlayerManager audioPlayerManager, AudioReference audioReference) {
		return this.loadItem(audioReference.identifier);
	}

	@Override
	public boolean isTrackEncodable(AudioTrack audioTrack) {
		return true;
	}

	@Override
	public void encodeTrack(AudioTrack audioTrack, DataOutput dataOutput) throws IOException {
		// Nothing to do
	}

	public AudioItem loadItem(String identifier) {
		try {
			if (identifier.startsWith(PREFIX)) {
				return this.getTrackByStationId(identifier.substring(PREFIX.length()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private AudioItem getTrackByStationId(String id) throws IOException {
		LofiRadioStation toPlay = service.getStation(id);

		return this.parseTrack(toPlay.getNowPlaying());
	}

	private AudioTrack parseTrack(LofiTrackInfo trackInfo) {
		var track = new AudioTrackInfo(
			trackInfo.getTitle(),
			trackInfo.getArtists(),
			(long) (trackInfo.getDuration() * 1000),
			trackInfo.getSlug(),
			false,
			audioUrl + trackInfo.getSlug() + ".mp3",
			trackInfo.getImage(),
			trackInfo.getISrc()
		);
		return new LofiRadioSrcAudioTrack(track, this);
	}

	@Override
	public AudioTrack decodeTrack(AudioTrackInfo audioTrackInfo, DataInput dataInput) throws IOException {
		return new LofiRadioSrcAudioTrack(audioTrackInfo, this);
	}

	@Override
	public void shutdown() {
		try {
			this.httpInterfaceManager.close();
		} catch (IOException e) {
			log.error("Failed to close HTTP interface manager", e);
		}
	}

	@Override
	public void configureRequests(Function<RequestConfig, RequestConfig> function) {
		this.httpInterfaceManager.configureRequests(function);
	}

	@Override
	public void configureBuilder(Consumer<HttpClientBuilder> consumer) {
		this.httpInterfaceManager.configureBuilder(consumer);
	}

	public HttpInterface getHttpInterface() {
		return this.httpInterfaceManager.getInterface();
	}
}

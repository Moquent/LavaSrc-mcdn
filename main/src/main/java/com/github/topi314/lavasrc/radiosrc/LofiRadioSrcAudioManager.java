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
	private String baseUrl;
	private String name;

	private HttpInterfaceManager httpInterfaceManager;
	private static final Logger log = LoggerFactory.getLogger(LofiRadioSrcAudioManager.class);

	public LofiRadioSrcAudioManager(String baseUrl, @Nullable String name) {
		this.name = name;
		this.baseUrl = baseUrl;
		this.httpInterfaceManager = HttpClientTools.createCookielessThreadLocalManager();
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
				return this.getTrackById(identifier.substring(PREFIX.length()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private AudioItem getTrackById(String id) throws IOException {
		return this.parseTrack(id);
	}

	private AudioTrack parseTrack(String id) {
		var track = new AudioTrackInfo(
			"Lofi Radio",
			"N/A",
			10000,
			id,
			false,
			baseUrl + id,
			null,
			null
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

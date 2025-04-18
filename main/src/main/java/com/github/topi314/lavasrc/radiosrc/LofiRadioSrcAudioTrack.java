package com.github.topi314.lavasrc.radiosrc;

import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.PersistentHttpStream;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.DelegatedAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor;

import java.net.URI;

public class LofiRadioSrcAudioTrack extends DelegatedAudioTrack {

	private final LofiRadioSrcAudioManager audioManager;

	public LofiRadioSrcAudioTrack(AudioTrackInfo trackInfo, LofiRadioSrcAudioManager manager) {
		super(trackInfo);
		this.audioManager = manager;
	}

	@Override
	public void process(LocalAudioTrackExecutor localAudioTrackExecutor) throws Exception {
		var downloadLink = this.trackInfo.uri;
		try (var httpInterface = this.audioManager.getHttpInterface()) {
			try (var stream = new PersistentHttpStream(httpInterface, new URI(downloadLink), this.trackInfo.length)) {
				processDelegate(new Mp3AudioTrack(this.trackInfo, stream), localAudioTrackExecutor);
			}
		}
	}

	@Override
	public AudioSourceManager getSourceManager() {
		return this.audioManager;
	}
}

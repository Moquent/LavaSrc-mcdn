package com.github.topi314.lavasrc.radiosrc;

import com.sedmelluq.discord.lavaplayer.tools.http.HttpContextFilter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LofiHttpContextFilter implements HttpContextFilter {
	private String audioUrl;
	private String user;
	private String pass;

	LofiHttpContextFilter(String audioUrl, String user, String pass) {
		this.audioUrl = audioUrl;
		this.user = user;
		this.pass = pass;
	}

	@Override
	public void onContextOpen(HttpClientContext httpClientContext) {

	}

	@Override
	public void onContextClose(HttpClientContext httpClientContext) {

	}

	@Override
	public void onRequest(HttpClientContext httpClientContext, HttpUriRequest httpUriRequest, boolean b) {
		if (httpUriRequest.getURI().toString().contains(audioUrl)) {
			String credentials = user + ":" + pass;
			String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
			httpUriRequest.addHeader(new BasicHeader("Authorization", "Basic " + encoded));
		}
	}

	@Override
	public boolean onRequestResponse(HttpClientContext httpClientContext, HttpUriRequest httpUriRequest, HttpResponse httpResponse) {
		return false;
	}

	@Override
	public boolean onRequestException(HttpClientContext httpClientContext, HttpUriRequest httpUriRequest, Throwable throwable) {
		return false;
	}
}

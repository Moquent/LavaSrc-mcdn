package com.github.topi314.lavasrc.plugin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "plugins.lavasrc.lofiradio")
@Component
public class LofiRadioConfig {
	private String baseUrl;
	private String name;
	private String allUrl;
	private String stationUrl;
	private String user;
	private String pass;

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAllUrl() {
		return allUrl;
	}

	public void setAllUrl(String allUrl) {
		this.allUrl = allUrl;
	}

	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}

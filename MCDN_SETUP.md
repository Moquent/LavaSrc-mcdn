# MCDN Source Manager Setup Guide

This guide explains how to set up and use the MCDN source manager with Lavalink.

## Installation

### 1. Copy the Built JARs

The LavaSrc plugin with MCDN support has been built. You need to copy these JAR files to your Lavalink server:

- `lavasrc-8e86008.jar` - Main library
- `lavasrc-plugin-8e86008.jar` - Lavalink plugin

### 2. Configure Lavalink

Edit your Lavalink `application.yml` file to include the LavaSrc plugin with MCDN support:

```yaml
lavalink:
  plugins:
    - dependency: "com.github.topi314.lavasrc:lavasrc-plugin:8e86008"
      repository: "https://maven.lavalink.dev/releases"
      # If you are manually installing the JARs, comment out the lines above and use this:
      # - file: "/path/to/lavasrc-plugin-8e86008.jar"

plugins:
  lavasrc:
    sources:
      mcdn: true  # Enable MCDN source
    mcdn:
      apiKey: "your_api_key_here"  # Your CDN API key
      baseUrl: "https://your-cdn-api-url.com"  # Your CDN API URL
      userAgent: "Lavasrc"  # Optional user agent
```

### 3. Restart Lavalink

After configuring the `application.yml`, restart your Lavalink server to load the plugin.

## Usage

### Using with Discord Music Bots

#### Search by Query

To search for tracks by query, use:

```
mcdnsearch:your search query
```

Example:
```
mcdnsearch:Rick Astley Never Gonna Give You Up
```

This will return a playlist with all matching tracks.

#### Search by ISRC

To search for tracks by ISRC code, use:

```
mcdnisrc:ISRC_CODE
```

Example:
```
mcdnisrc:GBAYE0601498
```

This will return a single track matching the ISRC.

### Using with Programming Libraries

Here are examples for how to use MCDN source with popular Discord libraries:

#### Java (JDA-Utilities)

```java
// Using query search
String query = "mcdnsearch:Rick Astley Never Gonna Give You Up";
audioPlayerManager.loadItem(query, new AudioLoadResultHandler() {
    // Handle result...
});

// Using ISRC search
String isrcQuery = "mcdnisrc:GBAYE0601498";
audioPlayerManager.loadItem(isrcQuery, new AudioLoadResultHandler() {
    // Handle result...
});
```

#### JavaScript (Discord.js with erela.js)

```javascript
// Using query search
const res = await player.search("mcdnsearch:Rick Astley Never Gonna Give You Up");
player.play(res.tracks[0]);

// Using ISRC search
const track = await player.search("mcdnisrc:GBAYE0601498");
player.play(track.tracks[0]);
```

#### Python (Wavelink)

```python
# Using query search
tracks = await wavelink.YouTubeTrack.search("mcdnsearch:Rick Astley Never Gonna Give You Up")
player.play(tracks[0])

# Using ISRC search
track = await wavelink.YouTubeTrack.search("mcdnisrc:GBAYE0601498")
player.play(track)
```

## Troubleshooting

### Plugin Not Loading

- Ensure the plugin JARs are in the correct location or properly referenced in `application.yml`
- Check Lavalink logs for any errors related to plugin loading

### Search Not Working

- Verify your CDN API is running and accessible
- Check that the API key and base URL are correctly configured
- Look for any error messages in the Lavalink logs

### Track Playback Issues

- Make sure the URLs returned by your CDN API are directly accessible
- Check that the audio format is compatible with Lavalink (MP3, FLAC, etc.)
- Verify the track data structure matches the expected format 
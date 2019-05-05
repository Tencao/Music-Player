package info.u_team.music_player.musicplayer;

import info.u_team.music_player.lavaplayer.api.*;
import info.u_team.music_player.util.WrappedObject;

public class LoadedTracks {
	
	private final WrappedObject<String> uri;
	
	private String errorMessage;
	
	private String title;
	private IAudioTrack track;
	private IAudioTrackList trackList;
	
	/**
	 * Dummy
	 * 
	 * @param uri
	 */
	public LoadedTracks(WrappedObject<String> uri) {
		this.uri = uri;
		errorMessage = "Not loaded yet";
	}
	
	public LoadedTracks(WrappedObject<String> uri, ISearchResult result) {
		this.uri = uri;
		if (result.hasError()) {
			this.errorMessage = result.getErrorMessage();
		} else {
			if (!result.isList()) {
				track = result.getTrack();
				title = track.getInfo().getTitle();
			} else {
				trackList = result.getTrackList();
				title = trackList.getName();
			}
		}
	}
	
	public LoadedTracks(WrappedObject<String> uri, IAudioTrack track) {
		this.uri = uri;
		this.track = track;
		title = track.getInfo().getTitle();
	}
	
	public LoadedTracks(WrappedObject<String> uri, IAudioTrackList trackList) {
		this.uri = uri;
		this.trackList = trackList;
		title = trackList.getName();
	}
	
	public WrappedObject<String> getUri() {
		return uri;
	}
	
	public boolean hasError() {
		return errorMessage != null;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean isTrack() {
		return trackList == null && track != null;
	}
	
	public boolean isTrackList() {
		return trackList != null && track == null;
	}
	
	public IAudioTrack getTrack() {
		return track;
	}
	
	public IAudioTrackList getTrackList() {
		return trackList;
	}
	
}
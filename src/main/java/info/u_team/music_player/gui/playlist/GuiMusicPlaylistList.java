package info.u_team.music_player.gui.playlist;

import java.util.*;

import info.u_team.music_player.gui.playlist.GuiMusicPlaylistListEntry.*;
import info.u_team.music_player.gui.playlist.GuiMusicPlaylistListEntry.Error;
import info.u_team.music_player.musicplayer.*;
import info.u_team.to_export_to_u_team_core.gui.GuiScrollableList;

public class GuiMusicPlaylistList extends GuiScrollableList<GuiMusicPlaylistListEntry> {
	
	private final Playlist playlist;
	
	private boolean tracksLoaded;
	
	private boolean canSelectNext;
	
	public GuiMusicPlaylistList(Playlist playlist) {
		super(0, 0, 0, 0, 0, 0, 40, 20, 5);
		this.playlist = playlist;
		canSelectNext = true;
		addEntry(new Loading());
	}
	
	private void addLoadedTrackToGui(LoadedTracks tracks) {
		List<GuiMusicPlaylistListEntry> list = new ArrayList<>();
		if (tracks.hasError()) {// Add error gui element
			list.add(new Error(this, playlist, tracks.getUri(), tracks.getErrorMessage()));
		} else if (tracks.isTrack()) { // Add track gui element
			list.add(new MusicTrack(this, playlist, tracks.getUri(), tracks.getTrack()));
		} else { // Add playlist start element and all track sub elements
			PlaylistStart start = new PlaylistStart(this, playlist, tracks.getUri(), tracks.getTitle());
			list.add(start);
			tracks.getTrackList().getTracks().forEach(track -> {
				PlaylistTrack trackentry = new PlaylistTrack(start, track);
				start.addEntry(trackentry);
				list.add(trackentry);
			});
		}
		list.forEach(this::addEntry);
	}
	
	public void addAllEntries() {
		if (!playlist.isLoaded()) {
			return;
		}
		if (!tracksLoaded) {
			clearEntries();
			playlist.getLoadedTracks().forEach(this::addLoadedTrackToGui);
			tracksLoaded = true;
		}
	}
	
	public void removeAllEntries() {
		clearEntries();
		tracksLoaded = false;
	}
	
	public void updateAllEntries() {
		removeAllEntries();
		addAllEntries();
	}
	
	@Override
	protected boolean isSelected(int index) {
		return index == selectedElement;
	}
	
	public void setSelectedEntryWhenMove(int index) {
		if (index >= 0 || index < getSize()) {
			super.setSelectedEntry(index);
			canSelectNext = false;
		}
	}
	
	@Override
	public void setSelectedEntry(int index) {
		if (canSelectNext) {
			super.setSelectedEntry(index);
		} else {
			canSelectNext = true;
		}
	}
	
}
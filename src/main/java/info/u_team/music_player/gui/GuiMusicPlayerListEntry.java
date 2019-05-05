package info.u_team.music_player.gui;

import info.u_team.music_player.gui.playlist.GuiMusicPlaylist;
import info.u_team.music_player.init.MusicPlayerResources;
import info.u_team.music_player.musicplayer.*;
import info.u_team.to_export_to_u_team_core.gui.*;

class GuiMusicPlayerListEntry extends GuiScrollableListEntry<GuiMusicPlayerListEntry> {
	
	private final Playlists playlists;
	private final Playlist playlist;
	
	private final GuiButtonClickImageToggle playPlaylistButton;
	private final GuiButtonClickImage openPlaylistButton;
	private final GuiButtonClickImage deletePlaylistButton;
	
	public GuiMusicPlayerListEntry(GuiMusicPlayerList gui, Playlists playlists, Playlist playlist) {
		this.playlists = playlists;
		this.playlist = playlist;
		
		playPlaylistButton = addButton(new GuiButtonClickImageToggle(0, 0, 20, 20, MusicPlayerResources.texturePlay, MusicPlayerResources.textureStop));
		playPlaylistButton.toggle(playlist.equals(playlists.getPlaying()));
		playPlaylistButton.setToggleClickAction((play) -> {
			gui.getChildren().stream().filter(entry -> entry != this).forEach(entry -> entry.playPlaylistButton.toggle(false)); // Reset all playlist buttons except this one
			
			// Start playlist
			if (play) {
				playlists.setPlaying(playlist);
			}
		});
		
		openPlaylistButton = addButton(new GuiButtonClickImage(0, 0, 20, 20, MusicPlayerResources.textureOpen));
		openPlaylistButton.setClickAction(() -> mc.displayGuiScreen(new GuiMusicPlaylist(playlists, playlist)));
		
		deletePlaylistButton = addButton(new GuiButtonClickImage(0, 0, 20, 20, MusicPlayerResources.textureClear));
		deletePlaylistButton.setClickAction(() -> gui.removePlaylist(this));
	}
	
	@Override
	public void drawEntry(int entryWidth, int entryHeight, int mouseX, int mouseY, boolean mouseInList, float partialTicks) {
		String name = playlist.getName();
		if (name.isEmpty()) {
			name = "\u00A7oNo name";
		}
		mc.fontRenderer.drawString(name, getX() + 5, getY() + 5, playlist.equals(playlists.getPlaying()) ? 0x0083FF : 0xFFF00F);
		mc.fontRenderer.drawString(playlist.getEntrySize() + " Entries", getX() + 5, getY() + 30, 0xFFFFFF);
		
		playPlaylistButton.x = entryWidth - 80;
		playPlaylistButton.y = getY() + 12;
		playPlaylistButton.render(mouseX, mouseY, partialTicks);
		
		openPlaylistButton.x = entryWidth - 50;
		openPlaylistButton.y = getY() + 12;
		openPlaylistButton.render(mouseX, mouseY, partialTicks);
		
		deletePlaylistButton.x = entryWidth - 20;
		deletePlaylistButton.y = getY() + 12;
		deletePlaylistButton.render(mouseX, mouseY, partialTicks);
	}
	
	public Playlist getPlaylist() {
		return playlist;
	}
}
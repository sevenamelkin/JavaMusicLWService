package Service;

import Entities.Album;
import Entities.Track;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface MusicServiceInterface {
    public Album getAlbum()throws UnsupportedAudioFileException, IOException;

    public void saveAlbumByPath(Album album) throws IOException;

    public void rearrangeAlbum();

    public Track getTrackByName(Album album, String Name);
}

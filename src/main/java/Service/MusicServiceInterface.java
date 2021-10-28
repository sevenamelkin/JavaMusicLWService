package Service;

import Entities.Album;
import Entities.Track;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface MusicServiceInterface {

    /**
     * Get album from folder with audio files
     * @return - music album.
     */
    public Album getAlbum()throws UnsupportedAudioFileException, IOException;

    /**
     * Write music album to path folder
     * @param album - music album.
     */
    public void saveAlbumByPath(Album album) throws IOException;

    /**
     * Rearrange disc compositions based on one of the parameters.
     */
    public void rearrangeAlbum();

    /**
     * Return Track by Name
     * @param album - music album.
     * @param Name - track name.
     * @return Track by name
     */
    public Track getTrackByName(Album album, String Name);
}

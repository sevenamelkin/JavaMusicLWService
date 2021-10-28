import Entities.Album;
import Entities.Track;
import Service.MusicService;
import Service.MusicServiceInterface;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static void main(String[] Args)
    {
        MusicServiceInterface musicService = new MusicService();
        try {
            Album album = musicService.getAlbum();
            musicService.saveAlbumByPath(album);
            Track track = musicService.getTrackByName(album, "honesty");
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

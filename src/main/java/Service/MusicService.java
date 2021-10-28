package Service;

import Entities.Album;
import Entities.Band;
import Entities.Track;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.sql.Time;
import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

public class MusicService implements MusicServiceInterface{

    private String FilePath = "src/main/resources/Tracks/";

    private MpegAudioFileReader mpegAudioFileReader;

    private String[] ArrayTrackPaths = new String[]{
            FilePath + "billyjoel-honesty.mp3",
            FilePath + "BonJovi-SaturdayNightGaveMeSundayMorning.mp3",
            FilePath + "Brainstorm-Maybe.mp3",
            FilePath + "BrunoMars-Grenade.mp3",
            FilePath + "BryanAdams-HereIAm.mp3"};

    public MusicService() {
        mpegAudioFileReader = new MpegAudioFileReader();
    }

    public Album getAlbum() throws UnsupportedAudioFileException, IOException {
        Album album = new Album();
        album.Name = "River of dreams";
        album.Tracks = new Track[5];

        for (int i = 0; i < ArrayTrackPaths.length; i++) {
            Track track = new Track();
            File file = new File(ArrayTrackPaths[i]);
            track.Track = mpegAudioFileReader.getAudioInputStream(file);
            String trackName = file.getName().replace(FilePath, "");
            Pattern patternFormat = Pattern.compile("\\-");//до символа
            String audioFormat = trackName.substring(trackName.lastIndexOf(".") + 1); // после символа
            track.Format = audioFormat;
            String trackNameWithoutFormat = trackName.replace("." + audioFormat, "");
            Band band = new Band();
            band.Name = patternFormat.split(trackNameWithoutFormat)[0];
            track.Name = trackNameWithoutFormat.substring(trackName.lastIndexOf("-") + 1);
            track.Band = band;
            album.Band = band;
            track.Duration = getTrackDuration(file);
            album.Tracks[i] = track;
        }
        return album;
    }

    public void saveAlbumByPath(Album album) throws IOException {
        for (int i = 0; i < ArrayTrackPaths.length; i++) {
            var audioInputStream = album.Tracks[i].Track;
            File file = new File(album.Tracks[i].Band.Name + "-" + album.Tracks[i].Name + "." + album.Tracks[i].Format);
            OutputStream outstream = new FileOutputStream(file);

            var buffer = audioInputStream.readAllBytes();
            InputStream inputStream = new FileInputStream(ArrayTrackPaths[i]);
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
                System.out.println(MessageFormat.format("Трек по имени {0} записан на диск", file.getName()));
            }
            outstream.close();
        }
    }

    public void rearrangeAlbum() {
        //не реализован
    }

    public Track getTrackByName(Album album, String Name) {
        for (int i = 0; i < album.Tracks.length; i++) {
            if (album.Tracks[i].Name.equals(Name)) {
                System.out.println(MessageFormat.format("Трэк по имени: {0}, найден", Name));
                return album.Tracks[i];
            }
        }
        return null;
    }

    private Time getTrackDuration(File file) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat baseFileFormat = mpegAudioFileReader.getAudioFileFormat(file);
        Map properties = baseFileFormat.properties();
        var microseconds = (Long) properties.get("duration");
        int mili = (int) (microseconds / 1000);
        int sec = (mili / 1000) % 60;
        int min = (mili / 1000) / 60;
        System.out.println("time = " + min + ":" + sec);
        return new Time(0, min, sec);
    }
}
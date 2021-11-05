package entity;

public class MusicEntity {
    private String durationtime;
    private String downcount;
    private String favcount;
    private String singer;
    private String album;
    private String author;
    private String composer;
    private String musicpath;
    private String name;
    private String albumpic;
    private int id;

    public MusicEntity(String durationtime, String downcount, String favcount, String singer, String album, String author, String composer, String musicpath, String name, String albumpic, int id) {
        this.durationtime = durationtime;
        this.downcount = downcount;
        this.favcount = favcount;
        this.singer = singer;
        this.album = album;
        this.author = author;
        this.composer = composer;
        this.musicpath = musicpath;
        this.name = name;
        this.albumpic = albumpic;
        this.id = id;
    }

    public void setDurationtime(String durationtime) {
        this.durationtime = durationtime;
    }

    public String getDurationtime() {
        return durationtime;
    }

    public void setDowncount(String downcount) {
        this.downcount = downcount;
    }

    public String getDowncount() {
        return downcount;
    }

    public void setFavcount(String favcount) {
        this.favcount = favcount;
    }

    public String getFavcount() {
        return favcount;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getComposer() {
        return composer;
    }

    public void setMusicpath(String musicpath) {
        this.musicpath = musicpath;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAlbumpic(String albumpic) {
        this.albumpic = albumpic;
    }

    public String getAlbumpic() {
        return albumpic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

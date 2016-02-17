package com.example.administrator.codingmusic.vo;

/**
 * Mp3Info
 *
 * @author: Xingkai Ren
 * @time: 2016/2/5  23:38
 */
public class Mp3Info {

    private static final String TAG="Mp3Info";

    private long id;
    private String title;
    private String artist;
    private String album;
    private long albumId;
    private long duration;
    private long size;
    private String url;
    private int isMusic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsMusic() {
        return isMusic;
    }

    public void setIsMusic(int isMusic) {
        this.isMusic = isMusic;
    }

    @Override
    public String toString() {
        return "Mp3Info[id="+id+",title="+title+".artist="+artist+",album="+album+",duration"+duration+
                ",size"+size+",url"+url+",isMusic"+isMusic+"]";

    }
}

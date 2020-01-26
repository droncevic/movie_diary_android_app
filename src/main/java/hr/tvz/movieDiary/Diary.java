package hr.tvz.movieDiary;
import java.io.Serializable;

public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String title;
    private String year;
    private String genre;
    private String writer;
    private String actors;
    private String plot;
    private String imdbrating;
    private String impression;
    private String myrating;

    public Diary(Long id, String type, String title, String year, String genre, String writer,
                 String actors, String plot, String imdbrating, String impression, String myrating) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.imdbrating = imdbrating;
        this.impression = impression;
        this.myrating = myrating;
    }

    public Diary () {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Diary title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public Diary year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public Diary genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public Diary writer(String writer) {
        this.writer = writer;
        return this;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public Diary actors(String actors) {
        this.actors = actors;
        return this;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public Diary plot(String plot) {
        this.plot = plot;
        return this;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbrating() {
        return imdbrating;
    }

    public Diary imdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
        return this;
    }

    public void setImdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
    }

    public String getImpression() {
        return impression;
    }

    public Diary impression(String impression) {
        this.impression = impression;
        return this;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getMyrating() {
        return myrating;
    }

    public Diary myrating(String myrating) {
        this.myrating = myrating;
        return this;
    }

    public void setMyrating(String myrating) {
        this.myrating = myrating;
    }

    @Override
    public String toString() {
        return  "\nEntry ID: " + getId() +
                "\n\nTitle: " + getTitle() +
                "\n\nType: " + getType() +
                "\n\nYear: " + getYear() +
                "\n\nGenre: " + getGenre() +
                "\n\nWriters:\n" + getWriter() +
                "\n\nActors:\n" + getActors() +
                "\n\nPlot:\n" + getPlot() +
                "\n\nIMDB rating: " + getImdbrating() +
                "\n\nImpression:\n" + getImpression() +
                "\n\nMy rating: " + getMyrating() + "\n";

    }
}

package at.ac.tuwien.sepm.groupphase.backend.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<EventDate> date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private List<News> newsEntries = new LinkedList<>();

    private LocalTime startTime;

    private String cityname;

    private int areaCode;

    private LocalTime duration;

    private String category;

    private String address;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String image;

    public Event() {
    }

    public Event(
        Long id,
        String title,
        List<EventDate> date,
        LocalTime startTime,
        String cityname,
        int areaCode,
        LocalTime duration,
        String category,
        String address,
        String description,
        String image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.cityname = cityname;
        this.areaCode = areaCode;
        this.duration = duration;
        this.category = category;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EventDate> getDate() {
        return date;
    }

    public void setDate(List<EventDate> date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<News> getNewsEntries() {
        return newsEntries;
    }

    public void setNewsEntries(List<News> newsEntries) {
        this.newsEntries = newsEntries;
    }

    public void addNewsEntry(News newsEntry) {
        this.newsEntries.add(newsEntry);
    }
}

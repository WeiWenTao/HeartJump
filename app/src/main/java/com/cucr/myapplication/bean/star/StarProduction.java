package com.cucr.myapplication.bean.star;

/**
 * Created by cucr on 2018/5/25.
 */

public class StarProduction {

    /**
     * lemmaid : 19815747
     * name : 我们的少年时代
     * role : 陶西
     * actors : TFBOYS, 李小璐, 唐禹哲
     * director : 成志超
     * date : 2017-7-9
     */

    private String lemmaid;
    private String name;
    private String role;
    private String actors;
    private String director;
    private String date;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public StarProduction(int type) {
        this.type = type;
    }

    public String getLemmaid() {
        return lemmaid;
    }

    public void setLemmaid(String lemmaid) {
        this.lemmaid = lemmaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StarProduction{" +
                "lemmaid='" + lemmaid + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", actors='" + actors + '\'' +
                ", director='" + director + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

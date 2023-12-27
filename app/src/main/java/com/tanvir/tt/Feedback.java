package com.tanvir.tt;

public class Feedback {
    String dayNight;
    String ratings;
    String route;
    String season;
    String seat1;
    String seat2;
    String seat3;
    String totalSeat;


    public Feedback(){

    }

    public Feedback(String dayNight, String ratings, String route, String season, String seat1, String seat2, String seat3, String totalSeat) {
        this.dayNight = dayNight;
        this.ratings = ratings;
        this.route = route;
        this.season = season;
        this.seat1 = seat1;
        this.seat2 = seat2;
        this.seat3 = seat3;
        this.totalSeat = totalSeat;


    }
    public String getRoute() {
        return route;
    }

    public String getSeason() {
        return season;
    }

    public String getDayNight() {
        return dayNight;
    }

    public String getTotalSeat() {
        return totalSeat;
    }

    public String getSeat1() {
        return seat1;
    }

    public String getSeat2() {
        return seat2;
    }

    public String getSeat3() {
        return seat3;
    }

    public String getRatings() {
        return ratings;
    }


}

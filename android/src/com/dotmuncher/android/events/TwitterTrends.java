package com.dotmuncher.android.events;
import java.util.Date;
import java.util.List;


public class TwitterTrends {

    private String as_of;
    private List<TwitterTrend> trends;
   
    public String getAs_of() {
        return as_of;
    }
    public void setAs_of(String asOf) {
        as_of = asOf;
    }
    public List<TwitterTrend> getTrends() {
        return trends;
    }
    public void setTrends(List<TwitterTrend> trends) {
        this.trends = trends;
    }
   
   
   
}
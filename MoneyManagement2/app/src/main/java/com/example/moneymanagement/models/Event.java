package com.example.moneymanagement.models;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event {

    private int id;
    private String price;
    private String place_name;
    private String date;
    private String category;

    public Event(int id, String price, String place_name, String date, String category) {
        this.id = id;
        this.price = price;
        this.place_name = place_name;
        this.date = date;
        this.category = category;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String curr_date = format_date.format(cal.getTime());

        DecimalFormat decim = new DecimalFormat("0.00");
        String final_price = decim.format(Double.parseDouble(price));

        return "--- Quản lý tài chính cá nhân ---\n" +
                "Giá: " + final_price + " VNĐ\n" +
                "Tên giao dịch: " + place_name + "\n" +
                "Hạng mục: " + category + "\n" +
                "Ngày: " + date + "\n" +
                "Báo cáo: " + curr_date;
    }
}

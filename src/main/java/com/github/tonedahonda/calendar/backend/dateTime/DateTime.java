package com.github.tonedahonda.calendar.backend.dateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTime {

    Time time;
    Date date;

    public DateTime(long second, long minute, long hour,
                    long day, long week, long month, long year) {
        setTime(new Time(second, minute, hour));
        setDate(new Date(day, week, month, year));

    }

    public DateTime(Date date, Time time) {
        setDate(new Date(date));
        setTime(new Time(time));
    }

    public DateTime(DateTime dateTime) {
        this(dateTime.getDate(), dateTime.getTime());
    }

    public DateTime(LocalDateTime dateTime) {
        this(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(),
                dateTime.getDayOfMonth(), (long) Math.ceil((double) dateTime.getDayOfMonth() / 7.0), dateTime.getMonthValue(), dateTime.getYear());
    }

    public DateTime() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    // Method to out put the day
    public String toString() {
        return
                "{"
                        + getMonth() + "."
                        + getDay() + "."
                        + getYear() + " "
                        + getHour() + ":"
                        + getMinute() + ":"
                        + getSecond() + ""
                        + "}";
    }

    public Time getTime() {
        return time;
    }

    public long getSecond() {
        return time.getSecond();
    }

    public long getMinute() {
        return time.getMinute();
    }

    public long getHour() {
        return time.getHour();
    }

    public Date getDate() {
        return date;
    }

    public long getDay() {
        return date.getDay();
    }

    public long getWeek() {
        return date.getWeek();
    }

    public long getMonth() {
        return date.getMonth();
    }

    public long getYear() {
        return date.getYear();
    }

	/*
     * Setters for date parameters.
	 */

    public void setTime(Time time) {
        this.time = time;
    }

    public void setSecond(long second) {
        time.setSecond(second);
    }

    public void setMinute(long minute) {
        time.setMinute(minute);
    }

    public void setHour(long hour) {
        time.setHour(hour);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDay(long day) {
        date.setDay(day);
    }

    public void setWeek(long week) {
        date.setWeek(week);
    }

    public void setMonth(long month) {
        date.setMonth(month);
    }

    public void setYear(long year) {
        date.setYear(year);
    }

    public LocalDateTime toLocalDateTime() {
        LocalTime localTime = LocalTime.of((int) getHour(), (int) getMinute(), (int) getSecond());
        LocalDate localDate = LocalDate.of((int) getYear(), (int) getMonth(), (int) getDay());

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        return localDateTime;
    }


    public boolean isPending() {
        return false;
    }

    public boolean isOver() {
        return false;
    }

}

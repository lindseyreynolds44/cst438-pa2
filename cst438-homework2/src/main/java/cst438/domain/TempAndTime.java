package cst438.domain;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TempAndTime {
  private double temp;
  private long time;
  private int timezone;
  private final int OFFSET = 7200000;

  public TempAndTime(double temp, long time, int timezone) {
    this.temp = temp;
    this.time = time;
    this.timezone = timezone;
  }

  public String getTemp() {
    temp = (temp - 273.15) * 9.0 / 5.0 + 32.0;
    DecimalFormat df = new DecimalFormat("#.##");
    String tempStr = df.format(temp) + " °F";
    return tempStr;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public String getTime() {
    time = time - OFFSET;
    Date date = new Date(time);
    DateFormat sdf = new SimpleDateFormat("h:mm a");
    TimeZone tz = TimeZone.getTimeZone("UTC");
    tz.setRawOffset(timezone * 1000);
    sdf.setTimeZone(tz);
    return sdf.format(date);
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getTimezone() {
    return timezone;
  }

  public void setTimezone(int timezone) {
    this.timezone = timezone;
  }
}

package com.android.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;

import com.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    private static String TAG = "DateTimeUtils";
    private static DateTimeUtils instance;

    private DateTimeUtils() {

    }

    public static DateTimeUtils getInstance() {
        if (instance == null) {
            instance = new DateTimeUtils();
        }
        return instance;
    }

    /**
     * enum - date & time formats
     */
    public enum DateFormats {
        dd("dd"),
        MM("MM"),
        MMM("MMM"),
        MMMM("MMMM"),
        yy("yy"),
        yyyy("yyyy"),
        HH("HH"),
        mm("mm"),
        ss("ss"),
        EEEE("EEEE"),
        HHmmsssss("HH:mm:ss.SSS'Z'"),
        HHmmss("HH:mm:ss"),
        hmm("h:mm"),
        hmma("h:mm a"),
        HHmm("HH:mm"),
        a("a"),
        ddMMMMyyyy("dd MMMM yyyy"),
        ddMMyyyy("dd/MM/yyyy"),
        yyyyMMdd("yyyy-MM-dd"),
        ddMMMyyyy("dd MMM yyyy"),
        ddMMMyyyyhmma("dd MMM yyyy" + " 'at' " + hmma.getLabel()),
        yyyyMMddHHmmss(yyyyMMdd.getLabel() + " " + HHmmss.getLabel()),
        ddMMyyyyhmma("dd/MM/yyyy" + ", " + hmma.getLabel()),
        ddMyyhmma("dd/M/yy" + ", " + hmma.getLabel());

        private String label;

        DateFormats(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * convert date&time to UTC
     *
     * @param sourceString
     * @param sourceDateFormat
     * @param targetDateFormat
     * @param timeZone
     * @return
     */
    public String formatDateTimeToUTC(String sourceString, String sourceDateFormat, String targetDateFormat, String timeZone) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat) || TextUtils.isEmpty(targetDateFormat) || TextUtils.isEmpty(timeZone))
            return "";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
            sourceFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            Date date = sourceFormat.parse(sourceString);

            SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
            targetFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
            String targetString = targetFormat.format(date);
            return targetString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * convert date&time to UTC
     *
     * @param sourceString
     * @param sourceDateFormat
     * @param targetDateFormat
     * @return
     */
    public String formatDateTimeToUTC(String sourceString, String sourceDateFormat, String targetDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat) || TextUtils.isEmpty(targetDateFormat))
            return "";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
            sourceFormat.setTimeZone(TimeZone.getDefault());
            Date date = sourceFormat.parse(sourceString);

            SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
            targetFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
            String targetString = targetFormat.format(date);
            return targetString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * convert date&time to UTC
     *
     * @param sourceDate
     * @param targetDateFormat
     * @return
     */
    public String formatDateTimeToUTC(Date sourceDate, String targetDateFormat) {
        if (TextUtils.isEmpty(targetDateFormat))
            return "";

        try {
            SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
            targetFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
            String targetString = targetFormat.format(sourceDate);
            return targetString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * convert UTC into date
     *
     * @param sourceString
     * @param sourceDateFormat
     * @param targetDateFormat
     * @param timeZone
     * @return
     */
    public String formatUTCToDate(String sourceString, String sourceDateFormat, String targetDateFormat, String timeZone) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat) || TextUtils.isEmpty(targetDateFormat) || TextUtils.isEmpty(timeZone))
            return "";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
            sourceFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
            Date date = sourceFormat.parse(sourceString);

            SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
            targetFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            String targetString = targetFormat.format(date);
            return targetString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * convert UTC into date
     *
     * @param sourceString
     * @param sourceDateFormat
     * @param targetDateFormat
     * @return
     */
    public String formatUTCToDate(String sourceString, String sourceDateFormat, String targetDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat) || TextUtils.isEmpty(targetDateFormat))
            return "";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
            sourceFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
            Date date = sourceFormat.parse(sourceString);

            SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
            String targetString = targetFormat.format(date);
            return targetString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * convert string into particular date&time format
     *
     * @param sourceString
     * @param sourceDateFormat
     * @param targetDateFormat
     * @return
     */
    public String formatDateTime(String sourceString, String sourceDateFormat, String targetDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat) || TextUtils.isEmpty(targetDateFormat)) {
            return "";
        }
        SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
        Date date = null;
        try {
            date = sourceFormat.parse(sourceString);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
        String targetString = targetFormat.format(date);
        return targetString;
    }

    /**
     * convert date into particular date&time format
     *
     * @param sourceDate
     * @param targetDateFormat
     * @return
     */
    public String formatDateTime(Date sourceDate, String targetDateFormat) {
        if (sourceDate == null || TextUtils.isEmpty(targetDateFormat)) {
            return "";
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat(targetDateFormat, Locale.US);
        String targetString = targetFormat.format(sourceDate);
        return targetString;
    }

    /**
     * convert string into date
     *
     * @param sourceString
     * @param sourceDateFormat
     * @return
     */
    public Date formatDateTime(String sourceString, String sourceDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat)) {
            return null;
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
        Date targetDate = null;
        try {
            targetDate = targetFormat.parse(sourceString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetDate;
    }

    /**
     * check datetime string is in given format
     *
     * @param sourceString
     * @param sourceDateFormat
     * @return
     */
    public boolean validFormat(String sourceString, String sourceDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat)) {
            return false;
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat(sourceDateFormat, Locale.US);
        try {
            targetFormat.parse(sourceString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * get date from time in millisecond
     *
     * @return
     */
    public Date getDateFromMillisecond(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);

        return calendar.getTime();
    }

    /**
     * get date from time in millisecond
     *
     * @return
     */
    public long getMillisecondFromDate(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        return calendar.getTimeInMillis();
    }

    /**
     * get time difference into minutes
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public long timeDifferenceInMinutes(Date sourceDate, Date targetDate) {
        long difference = sourceDate.getTime() - targetDate.getTime();
        Debugger.logE(TAG, "Time difference " + TimeUnit.MILLISECONDS.toMinutes(difference));
        return TimeUnit.MILLISECONDS.toMinutes(difference);
    }

    /**
     * get time difference into seconds
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public long timeDifferenceInSeconds(Date sourceDate, Date targetDate) {
        long difference = sourceDate.getTime() - targetDate.getTime();
        Debugger.logE(TAG, "Time difference " + TimeUnit.MILLISECONDS.toSeconds(difference));
        return TimeUnit.MILLISECONDS.toSeconds(difference);
    }

    /**
     * get time difference into hours
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public long timeDifferenceInHours(Date sourceDate, Date targetDate) {
        long difference = sourceDate.getTime() - targetDate.getTime();
        Debugger.logE(TAG, "Time difference " + TimeUnit.MILLISECONDS.toHours(difference));
        return TimeUnit.MILLISECONDS.toHours(difference);
    }

    /**
     * get time difference into days
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public long timeDifferenceInDays(Date sourceDate, Date targetDate) {
        long difference = sourceDate.getTime() - targetDate.getTime();
        Debugger.logE(TAG, "Time difference " + TimeUnit.MILLISECONDS.toDays(difference));
        return TimeUnit.MILLISECONDS.toDays(difference);
    }

    /**
     * check given date to today
     *
     * @param sourceDate
     * @return
     */
    public boolean isToday(Date sourceDate) {
        if (sourceDate == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTime().equals(todayCalendar.getTime())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check given date to today
     *
     * @param sourceDate
     * @return
     */
    public boolean isTomorrow(Date sourceDate) {
        if (sourceDate == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.add(Calendar.DAY_OF_YEAR, 1);
        tomorrowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowCalendar.set(Calendar.MINUTE, 0);
        tomorrowCalendar.set(Calendar.SECOND, 0);
        tomorrowCalendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTime().equals(tomorrowCalendar.getTime())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get day getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getDay(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.dd.getLabel(), sourceDate);
    }

    /**
     * get month getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getMonth(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.MM.getLabel(), sourceDate);
    }

    /**
     * get year getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getYear(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.yyyy.getLabel(), sourceDate);
    }

    /**
     * get year getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getYearinTwoDigit(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.yy.getLabel(), sourceDate);
    }

    /**
     * get weekday getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getWeekDay(Date sourceDate) {
        return ((String) DateFormat.format(DateFormats.EEEE.getLabel(), sourceDate));
    }

    /**
     * get time getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getTime(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.hmma.getLabel(), sourceDate);
    }

    /**
     * get hour getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getHour(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.HH.getLabel(), sourceDate);
    }

    /**
     * get minute getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getMinute(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.mm.getLabel(), sourceDate);
    }

    /**
     * get second getFileFromContentProvider date
     *
     * @param sourceDate
     * @return
     */
    public String getSecond(Date sourceDate) {
        return (String) DateFormat.format(DateFormats.ss.getLabel(), sourceDate);
    }

    /**
     * get minute - with in 15 minute time slot
     *
     * @param minute
     * @return
     */
    public int getRoundedMinute(int minute) {
        if (minute >= 0 && minute < 15) {
            return 15;
        } else if (minute >= 15 && minute < 30) {
            return 30;
        } else if (minute >= 30 && minute < 45) {
            return 45;
        } else {
            return 0;
        }
    }

    /**
     * get timestamp with particular timezone
     *
     * @param sourceString
     * @param sourceDateFormat
     * @return
     */
    public String getTimeStampWithTimezone(String sourceString, String sourceDateFormat) {
        if (TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceDateFormat)) {
            return "";
        }

        Date pickUpDateTime = formatDateTime(sourceString.trim(), sourceDateFormat);
        String targetString = "/Date(" + pickUpDateTime.getTime() + "+0530" + ")/";
        return targetString;
    }

    /**
     * get last seen time getFileFromContentProvider date
     *
     * @param mActivity
     * @param sourceDate
     * @return
     */
    public String getLastSeenTiming(Activity mActivity, Date sourceDate) {
        if (sourceDate == null) {
            return "";
        }

        long current_milis = System.currentTimeMillis();
        long timeDifInMilliSec = current_milis - sourceDate.getTime();

        long milisOfsec = 1000;
        long milisOfminute = 60 * milisOfsec;
        long milisOfhour = 60 * milisOfminute;
        long milisOfday = 24 * milisOfhour;
        long milisOfweek = 7 * milisOfday;
        long milisOfmonth = 30 * milisOfday;
        long milisOfyear = 12 * milisOfmonth;

        long timeDifSeconds = timeDifInMilliSec / milisOfsec;
        long timeDifMinutes = timeDifInMilliSec / milisOfminute;
        long timeDifHours = timeDifInMilliSec / milisOfhour;
        long timeDifDays = timeDifInMilliSec / milisOfday;
        long timeDifWeeks = timeDifInMilliSec / milisOfweek;
        long timeDifMonths = timeDifInMilliSec / milisOfmonth;
        long timeDifYears = timeDifInMilliSec / milisOfyear;

        String time = null;

        if (timeDifYears > 0) {
            if (timeDifYears == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_year);
            } else {
                time = timeDifYears + " " + mActivity.getResources().getString(R.string.diff_years);
            }
        } else if (timeDifMonths > 0) {
            if (timeDifMonths == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_month);
            } else {
                time = timeDifMonths + " " + mActivity.getResources().getString(R.string.diff_months);
            }
        } else if (timeDifWeeks > 0) {
            if (timeDifWeeks == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_week);
            } else {
                time = timeDifWeeks + " " + mActivity.getResources().getString(R.string.diff_weeks);
            }
        } else if (timeDifDays > 0) {
            if (timeDifDays == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_day);
            } else {
                time = timeDifDays + " " + mActivity.getResources().getString(R.string.diff_days);
            }
        } else if (timeDifHours > 0) {
            if (timeDifHours == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_hour);
            } else {
                time = timeDifHours + " " + mActivity.getResources().getString(R.string.diff_hours);
            }
        } else if (timeDifMinutes > 0) {
            if (timeDifMinutes == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_minute);
            } else {
                time = timeDifMinutes + " " + mActivity.getResources().getString(R.string.diff_minutes);
            }
        } else if (timeDifSeconds > 0) {
            if (timeDifSeconds == 1) {
                time = "1 " + mActivity.getResources().getString(R.string.diff_second);
            } else {
                time = timeDifSeconds + " " + mActivity.getResources().getString(R.string.diff_seconds);
            }
        }

        return String.format(mActivity.getResources().getString(R.string.ago), time);
    }
}

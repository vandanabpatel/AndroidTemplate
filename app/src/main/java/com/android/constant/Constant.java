package com.android.constant;

import java.util.regex.Pattern;

public class Constant {
    /**
     * format
     */
    public static final String phone_empty = "0000 00 00";
    public static final String phone_formatter = "### ### ####";
    public static final String next = " 》";

    /**
     * pattern
     */
    public static final Pattern[] idPatterns = {
            Pattern.compile(".*/video/([^_&]+).*"),
            Pattern.compile("/swf/([^&]+).*")};
    public static final String pattern_video_youtube = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
    public static final String pattern_video_vimeo = "^(http:\\/\\/|https:\\/\\/)(player.vimeo.com|vimeo\\.com)\\/([\\w\\/]+)([\\?].*)?$";
    public static final String pattern_video_dailymotion = "^https?:\\/\\/(?:www\\.)?(?:dai\\.ly\\/|dailymotion\\.com\\/(?:.+?video=|(?:video|hub)\\/))([a-z0-9]+)$";

    public static final String thumbnail_youtube = "https://img.youtube.com/vi/id/maxresdefault.jpg";
    public static final String thumbnail_dailymotion = "https://api.dailymotion.com/video/_id_?fields=thumbnail_large_url";

    /**
     * delay in second
     */
    public static final int DELAY_SCROLL = 300;
    public static final int DELAY_SPLASH = 3000;
    public static final int DELAY_3000 = 3000;
    public static final int DELAY_1000 = 1000;

    /**
     * receiver ACTION
     */
    public static final String ACTION_PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
    public static final String ACTION_PUSH_NOTIFICATION_CAMERA_CHECK = "PUSH_NOTIFICATION_CAMERA_CHECK";
    public static final String ACTION_PUSH_NOTIFICATION_BADGE_REFRESH = "PUSH_NOTIFICATION_BADGE_REFRESH";

    public static final String ACTION_CALL = "CALL";
    public static final String ACTION_EMAIL = "EMAIL";
    public static final String ACTION_GOTO_LINK = "GOTO_LINK";
    public static final String ACTION_CAMERA = "CAMERA";
    public static final String ACTION_RATE_US = "RATE_US";
    public static final String ACTION_CLOSE = "CLOSE";

    /**
     * static value
     */
    public static final String const_package = "package";
    public static final String const_usertype_coach = "coach";
    public static final String const_usertype_pi = "pi";
    public static final String const_usertype_principal = "principal";
    public static final String const_usertype_trusty = "trusty";

    public static final String const_status_accept = "Accept";
    public static final String const_status_reject = "Reject";
    public static final String const_status_verified = "Verified";

    public static final String const_status_present = "Present";
    public static final String const_status_absent = "Absent";

    public static final String const_time = "00:00:00";

    /**
     * recyclerview pagination
     */
    public static final int PAGE_PROGRESS_THRESHOLD = 1;
    public static final int PAGE_GRID_SPAN = 2;
    public static final boolean PAGE_ISLOADED = true;
    public static final int PAGE_LIMIT = 10;

    /**
     * keys
     */
    public static final String id = "id";
    public static final String username = "username";
    public static final String password = "password";
    public static final String oldpassword = "oldpassword";
    public static final String newpassword = "newpassword";
    public static final String type = "type";
    public static final String user_id = "user_id";
    public static final String school_id = "school_id";
    public static final String office_id = "office_id";
    public static final String medium_id = "medium_id";
    public static final String coach_id = "coach_id";
    public static final String category_id = "category_id";
    public static final String subcategory_array = "subcategory_id[%s]";
    public static final String class_id = "class_id";
    public static final String section_id = "section_id";
    public static final String image = "image";
    public static final String date = "date";
    public static final String time = "time";
    public static final String task = "task";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String year = "year";
    public static final String month = "month";
    public static final String from_date = "from_date";
    public static final String to_date = "to_date";
    public static final String reason = "reason";
    public static final String status = "status";
    public static final String subject = "subject";
    public static final String message = "message";
    public static final String to_whom = "to_whom";
    public static final String mobile = "mobile";
    public static final String amount = "amount";
    public static final String note = "note";
    public static final String name = "name";
    public static final String established_date = "established_date";
    public static final String area = "area";
    public static final String open_time = "open_time";
    public static final String closing_time = "closing_time";
    public static final String activity = "activity";
    public static final String activity_array = "activity[%s]";
    public static final String days = "days";
    public static final String email = "email";
    public static final String dob = "dob";
    public static final String doj = "doj";
    public static final String total_student = "total_student";
    public static final String standard = "standard";
    public static final String contract = "contract";
    public static final String charges = "charges";
    public static final String contact_person = "contact_person";
    public static final String mobile_no = "mobile_no";
    public static final String flag = "flag";
}

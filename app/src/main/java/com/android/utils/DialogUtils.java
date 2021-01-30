package com.android.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.R;
import com.android.ui.App;
import com.android.widget.CustomTFSpan;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

import java.util.Calendar;
import java.util.Date;

public class DialogUtils {
    private static String TAG = "DialogUtils";
    private static DialogUtils instance;

    private Typeface typefaceRegular;
    public CustomTFSpan tfSpanRegular;
    private AlertDialog alertDialog;

    private DialogUtils() {
        typefaceRegular = ResourcesCompat.getFont(App.getAppContext(), R.font.font_regular);
        tfSpanRegular = new CustomTFSpan(typefaceRegular);
    }

    public static DialogUtils getInstance() {
        if (instance == null) {
            instance = new DialogUtils();
        }
        return instance;
    }

    /**
     * get Dialog Width
     *
     * @param mContext
     * @return
     */
    public static int getDialogWidth(Context mContext) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((AppCompatActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return (int) (displayMetrics.widthPixels * 0.5);
        } catch (Exception e) {
            e.printStackTrace();
            return 700;
        }
    }

    /**
     * create progress dialog
     *
     * @param mContext
     */
    public Dialog createProgressDialog(Context mContext) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_progressbar);
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * @param mContext
     * @return
     */
    public Dialog createDialog(Context mContext) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * @param mContext
     * @param style
     * @return
     */
    public Dialog createDialog(Context mContext, int style) {
        Dialog dialog = new Dialog(mContext, style);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * show custom alert dialog
     *
     * @param mContext
     * @param isCancelable
     * @param title
     * @param message
     * @param yesButtonString
     * @param noButtonString
     * @param listener
     */
    public void showCustomYesNoAlertDialog(Context mContext, boolean isCancelable, String title, String message, String yesButtonString, String noButtonString, final OnDialogOkCancelButtonClickListener listener) {
        final SpannableString messageSpan = new SpannableString(message);
        messageSpan.setSpan(tfSpanRegular, 0, messageSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnYesSpan = new SpannableString(yesButtonString);
        btnYesSpan.setSpan(tfSpanRegular, 0, btnYesSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnNoSpan = new SpannableString(noButtonString);
        btnNoSpan.setSpan(tfSpanRegular, 0, btnNoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(messageSpan);

        builder.setPositiveButton(btnYesSpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onOkButtonClick();
                }
            }
        });

        builder.setNegativeButton(btnNoSpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onCancelButtonClick();
                }
            }
        });

        builder.setCancelable(isCancelable);
        show(mContext, builder);
    }

    /**
     * show custom alert dialog
     *
     * @param mContext
     * @param isCancelable
     * @param message
     * @param yesButtonString
     * @param noButtonString
     * @param listener
     */
    public void showCustomYesNoAlertDialog(Context mContext, boolean isCancelable, String message, String yesButtonString, String noButtonString, final OnDialogOkCancelButtonClickListener listener) {
        final SpannableString messageSpan = new SpannableString(message);
        messageSpan.setSpan(tfSpanRegular, 0, messageSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnYesSpan = new SpannableString(yesButtonString);
        btnYesSpan.setSpan(tfSpanRegular, 0, btnYesSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnNoSpan = new SpannableString(noButtonString);
        btnNoSpan.setSpan(tfSpanRegular, 0, btnNoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(messageSpan);

        builder.setPositiveButton(btnYesSpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onOkButtonClick();
                }
            }
        });

        builder.setNegativeButton(btnNoSpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onCancelButtonClick();
                }
            }
        });

        builder.setCancelable(isCancelable);
        show(mContext, builder);
    }

    /**
     * show alert dialog with Ok button
     *
     * @param mContext
     * @param title
     * @param message
     */
    public void showAlertDialog(Context mContext, String title, String message) {
        showAlertDialog(mContext, true, title, message, null);
    }

    /**
     * show alert dialog with Ok button with Callback
     *
     * @param mContext
     * @param isCancelable
     * @param title
     * @param message
     * @param listener
     */
    public void showAlertDialog(Context mContext, boolean isCancelable, String title, String message, final OnDialogOkButtonClickListener listener) {
        final SpannableString titleSpan = new SpannableString(title);
        titleSpan.setSpan(tfSpanRegular, 0, titleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString messageSpan = new SpannableString(message);
        messageSpan.setSpan(tfSpanRegular, 0, messageSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnOkSpan = new SpannableString(mContext.getResources().getString(R.string.ok));
        btnOkSpan.setSpan(tfSpanRegular, 0, btnOkSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(titleSpan);
        }
        builder.setMessage(messageSpan);

        builder.setPositiveButton(btnOkSpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onOkButtonClick();
                }
            }
        });

        builder.setCancelable(isCancelable);
        show(mContext, builder);
    }

    /**
     * show alert dialog with Ok button with Callback
     *
     * @param mContext
     * @param isCancelable
     * @param title
     * @param listener
     */
    public void showArrayDialog(Context mContext, boolean isCancelable, String title, String[] array, final OnDialogClickListener listener) {
        final SpannableString titleSpan = new SpannableString(title);
        titleSpan.setSpan(tfSpanRegular, 0, titleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnOkSpan = new SpannableString(mContext.getResources().getString(R.string.ok));
        btnOkSpan.setSpan(tfSpanRegular, 0, btnOkSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(titleSpan);
        }

        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                listener.onClick(array[position]);
            }
        });

        builder.setCancelable(isCancelable);
        show(mContext, builder);
    }

    /**
     * show permission alert dialog
     *
     * @param mContext
     * @param isCancelable
     * @param message
     * @param listener
     */
    public void showPermissionAlertDialog(Context mContext, boolean isCancelable, String message, final OnPermissionDialogButtonClickListener listener) {
        final SpannableString messageSpan = new SpannableString(message);
        messageSpan.setSpan(tfSpanRegular, 0, messageSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnRetrySpan = new SpannableString(mContext.getResources().getString(R.string.retry));
        btnRetrySpan.setSpan(tfSpanRegular, 0, btnRetrySpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString btnDenySpan = new SpannableString(mContext.getResources().getString(R.string.deny));
        btnDenySpan.setSpan(tfSpanRegular, 0, btnDenySpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(messageSpan);

        builder.setPositiveButton(btnDenySpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDenyButtonClick();
                }
            }
        });

        builder.setNegativeButton(btnRetrySpan, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onRetryButtonClick();
                }
            }
        });

        builder.setCancelable(isCancelable);
        show(mContext, builder);
    }

    /**
     * ask to pick image sourse - for camera
     *
     * @param mContext
     * @param title
     * @return
     */
    public AlertDialog.Builder showCameraPickerDialog(Context mContext, String title) {
        final SpannableString titleSpan = new SpannableString(title);
        titleSpan.setSpan(tfSpanRegular, 0, titleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(titleSpan);
        return builder;
    }

    /**
     * display datepicker - for birthdate
     *
     * @param mActivity
     * @param editText
     */
    public void showBirthDatePickerDialog(final Activity mActivity, final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    editText.getText().toString().trim(),
                    DateTimeUtils.DateFormats.ddMMMMyyyy.getLabel());
            calendar.setTime(currentDate);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener onDateSetListener = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editText.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.ddMMMMyyyy.getLabel()));
            }
        };

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog picker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setThemeDark(false);

        // set maximum date
        final Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.YEAR, -18);
        picker.setMaxDate(maxCalendar);

        picker.show(mActivity.getFragmentManager(), "Datepickerdialog");

        picker.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     * display datepicker - for birthdate
     *
     * @param mActivity
     * @param editText
     */
    public void showMinDatePickerDialog(final Activity mActivity, final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    editText.getText().toString().trim(),
                    DateTimeUtils.DateFormats.yyyyMMdd.getLabel());
            calendar.setTime(currentDate);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener onDateSetListener = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editText.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));
                editText.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));
            }
        };

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog picker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setThemeDark(false);

        // set minimum date
        final Calendar maxCalendar = Calendar.getInstance();
        picker.setMinDate(maxCalendar);

        picker.show(mActivity.getFragmentManager(), "Datepickerdialog");

        picker.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     * display datepicker - for date
     *
     * @param mActivity
     * @param isDisablePast
     * @param isDisableFuture
     * @param textview
     * @param listener
     */
    public void showDatePickerDialog(final Activity mActivity, boolean isDisablePast, boolean isDisableFuture, final EditText textview, final OnDateTimePickerDialogClickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(textview.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    textview.getText().toString().trim(),
                    DateTimeUtils.DateFormats.yyyyMMdd.getLabel());
            calendar.setTime(currentDate);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener onDateSetListener = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                textview.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));
                textview.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));

                if (listener != null) {
                    listener.onOkButtonClick(view.getView(), calendar);
                }
            }
        };

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog picker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setThemeDark(false);

        // set minimum date
        if (isDisablePast) {
            final Calendar minCalendar = Calendar.getInstance();
            picker.setMinDate(minCalendar);
        }

        // set maximum date
        if (isDisableFuture) {
            final Calendar maxCalendar = Calendar.getInstance();
            picker.setMaxDate(maxCalendar);
        }

        picker.show(mActivity.getFragmentManager(), "Datepickerdialog");

        picker.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     * display datepicker - for date
     *
     * @param mActivity
     * @param textview_min
     * @param textview
     * @param listener
     */
    public void showDatePickerDialog(final Activity mActivity, final EditText textview_min, final EditText textview, final OnDateTimePickerDialogClickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(textview.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    textview.getText().toString().trim(),
                    DateTimeUtils.DateFormats.yyyyMMdd.getLabel());
            calendar.setTime(currentDate);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener onDateSetListener = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                textview.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));
                textview.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.yyyyMMdd.getLabel()));

                if (listener != null) {
                    listener.onOkButtonClick(view.getView(), calendar);
                }
            }
        };

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog picker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setThemeDark(false);

        // set minimum date
        if (!TextUtils.isEmpty(textview_min.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    textview_min.getText().toString().trim(),
                    DateTimeUtils.DateFormats.yyyyMMdd.getLabel());

            final Calendar minCalendar = Calendar.getInstance();
            minCalendar.setTime(currentDate);
            picker.setMinDate(minCalendar);
        }

        picker.show(mActivity.getFragmentManager(), "Datepickerdialog");

        picker.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     * display timepicker - for time
     *
     * @param mActivity
     * @param textview
     * @param listener
     */
    public void showTimePickerDialog(final Activity mActivity, final EditText textview, final OnDateTimePickerDialogClickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(textview.getText().toString().trim())) {
            Date currentDate = DateTimeUtils.getInstance().formatDateTime(
                    textview.getText().toString().trim(),
                    DateTimeUtils.DateFormats.HHmm.getLabel());
            calendar.setTime(currentDate);
        }

        OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, second);

                textview.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.HHmm.getLabel()));
                textview.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.HHmm.getLabel()));

                if (listener != null) {
                    listener.onOkButtonClick(view.getView(), calendar);
                }
            }
        };

        TimePickerDialog picker = TimePickerDialog.newInstance(
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                true);
        picker.setThemeDark(false);
        picker.setTimeInterval(1, 15);

        picker.show(mActivity.getFragmentManager(), "Datepickerdialog");

        picker.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     * show
     *
     * @param mContext
     * @param builder
     */
    private void show(Context mContext, AlertDialog.Builder builder) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            alertDialog = builder.create();
            alertDialog.show();

            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setAllCaps(false);
            positiveButton.setTextColor(mContext.getResources().getColor(R.color.theme));

            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setAllCaps(false);
            negativeButton.setTextColor(mContext.getResources().getColor(R.color.theme));

            Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            neutralButton.setAllCaps(false);
            neutralButton.setTextColor(mContext.getResources().getColor(R.color.theme));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dialog Ok button click listener
     */
    public interface OnDialogOkButtonClickListener {
        void onOkButtonClick();
    }

    /**
     * dialog Ok-Cancel button click listener
     */
    public interface OnDialogOkCancelButtonClickListener {
        void onOkButtonClick();

        void onCancelButtonClick();
    }

    /**
     * dialog Ok button click listener
     */
    public interface OnDialogClickListener {
        void onClick(String item);
    }

    /**
     * dialog Ok button click listener
     */
    public interface OnDateTimePickerDialogClickListener {
        void onOkButtonClick(View view, Calendar calendar);
    }

    /**
     * Permission dialog button click listener
     */
    public interface OnPermissionDialogButtonClickListener {
        void onRetryButtonClick();

        void onDenyButtonClick();
    }
}
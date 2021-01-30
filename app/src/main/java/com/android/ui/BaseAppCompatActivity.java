package com.android.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.android.R;
import com.android.listener.OnSnakbarListener;
import com.android.model.ResponseModel;
import com.android.utils.DialogUtils;
import com.android.utils.IntentUtils;
import com.android.utils.PreferenceUtils;
import com.android.utils.Utility;
import com.android.widget.snakbar.TSnackbar;

import java.io.IOException;

import okhttp3.ResponseBody;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = BaseAppCompatActivity.this;

    private Dialog pdProgress;
    private int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * check internet connection and show error dialog if not available
     *
     * @param mContext
     * @param showErrorDialog
     * @return
     */
    public boolean isNetworkAvailable(Context mContext, boolean showErrorDialog) {
        boolean networkStatus = false;
        if (Utility.isNetworkAvailable(mContext)) {
            networkStatus = true;
        } else {
            // show error
            if (showErrorDialog) {
                DialogUtils.getInstance().showAlertDialog(mContext,
                        mContext.getResources().getString(R.string.app_name),
                        mContext.getResources().getString(R.string.error_no_internet));
            }
        }

        return networkStatus;
    }

    /**
     * show progress
     *
     * @param mActivity
     */
    public void showProgress(Activity mActivity) {
        try {
            if (pdProgress == null) {
                pdProgress = DialogUtils.getInstance().createProgressDialog(mActivity);
            }
            if (counter == 0 && !mActivity.isFinishing()) {
                pdProgress.show();
            }
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
            pdProgress = null;
        }
    }

    /**
     * hide progress
     */
    public void hideProgress() {
        try {
           /* final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {*/
            if (counter > 0) counter--;
            if (pdProgress != null && pdProgress.isShowing() && counter == 0 && !mActivity.isFinishing())
                pdProgress.dismiss();
//                }
//            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
            pdProgress = null;
        }
    }

    /**
     * get root view
     *
     * @return
     */
    public View getRootView() {
        View rootview = findViewById(R.id.view_root);
        if (rootview == null) {
            rootview = getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * get root view
     *
     * @param rootview
     * @return
     */
    public View getRootView(View rootview) {
        if (rootview == null) {
            rootview = getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     * @param listener
     */
    public void showSnackbarSuccess(Activity mActivity, String message, OnSnakbarListener listener) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green), listener);
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, String message) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarError(Activity mActivity, String message) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarError(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbarForDialog(Activity mActivity, View rootview, String message, int color) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbar(Activity mActivity, View rootview, String message, int color) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     * @param listener
     */
    public void showSnackbar(Activity mActivity, View rootview, String message, int color, final OnSnakbarListener listener) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.setCallback(new TSnackbar.Callback() {
                @Override
                public void onDismissed(TSnackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    listener.onSnakbarClose();
                }
            });
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     */
    protected void showExceptionError(Activity mActivity) {
        try {
            showSnackbarError(mActivity, mActivity.getResources().getString(R.string.standard_error_0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     * @param status
     * @param responseBody
     */
    protected void showExceptionError(Activity mActivity, int status, ResponseBody responseBody) {
        try {
            ResponseModel response = (ResponseModel) Utility.jsonToPojo(responseBody.string(), ResponseModel.class);

            if (response.getMessage() != null) {
                showSnackbarError(mActivity, response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showExceptionError(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
            showExceptionError(mActivity);
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     * @param rootview
     * @param status
     * @param responseBody
     */
    protected void showExceptionError(Activity mActivity, View rootview, int status, ResponseBody responseBody) {
        try {
            ResponseModel response = (ResponseModel) Utility.jsonToPojo(responseBody.string(), ResponseModel.class);

            if (response.getMessage() != null) {
                showSnackbarError(mActivity, rootview, response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.android.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.listener.OnSnakbarListener;
import com.android.model.ResponseModel;
import com.android.ui.activity.MainActivity;
import com.android.utils.DialogUtils;
import com.android.utils.IntentUtils;
import com.android.utils.PreferenceUtils;
import com.android.utils.Utility;
import com.android.widget.snakbar.TSnackbar;

import java.io.IOException;

import okhttp3.ResponseBody;

public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public MainActivity mainActivity;

    private Dialog pdProgress;
    public FragmentNavigation mFragmentNavigation;

    private int counter = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

    /**
     * check internet connection and show error dialog if not available
     *
     * @param mActivity
     * @param showErrorDialog
     * @return
     */
    public boolean isNetworkAvailable(Activity mActivity, boolean showErrorDialog) {
        boolean networkStatus = false;
        if (Utility.isNetworkAvailable(mActivity)) {
            networkStatus = true;
        } else {
            // show error
            if (showErrorDialog) {
                DialogUtils.getInstance().showAlertDialog(mActivity,
                        mActivity.getResources().getString(R.string.app_name),
                        mActivity.getResources().getString(R.string.error_no_internet));
            }
        }

        return networkStatus;
    }

    /**
     * show progress
     *
     * @param mActivity
     */
    protected void showProgress(Activity mActivity) {
        try {
            if (pdProgress == null) {
                pdProgress = DialogUtils.getInstance().createProgressDialog(mActivity);
            }
            if (counter == 0) {
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
    protected void hideProgress() {
        try {
            if (counter > 0) counter--;
            if (pdProgress != null && pdProgress.isShowing() && counter == 0)
                pdProgress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            pdProgress = null;
        }
    }

    /**
     * get root view
     *
     * @param mActivity
     * @return
     */
    private View getRootView(Activity mActivity) {
        View rootview = mActivity.findViewById(R.id.view_root);
        if (rootview == null) {
            rootview = mActivity.getWindow().getDecorView().getRootView();
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
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.green), listener);
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, String message) {
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarError(Activity mActivity, String message) {
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbar(View rootview, String message, int color) {
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
     * @param rootview
     * @param message
     * @param color
     * @param listener
     */
    public void showSnackbar(View rootview, String message, int color, final OnSnakbarListener listener) {
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
}
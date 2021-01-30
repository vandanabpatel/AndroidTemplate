package com.android.utils;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.R;
import com.android.constant.Constant;

public class PermissionUtils {
    // permission result code
    public static final int REQUEST_LOCATION_SETTING = 1000;
    public static final int RESULTCODE_PERMISSION_LOCATION = 1001;
    public static final int RESULTCODE_PERMISSION_CAMERA = 1002;
    public static final int RESULTCODE_PERMISSION_STORAGE = 1003;
    public static final int RESULTCODE_PERMISSION_CONTACT = 1004;
    public static final int RESULTCODE_PERMISSION_CALL = 1005;
    public static final int RESULTCODE_PERMISSION_SEND_SMS = 1006;
    public static final int RESULTCODE_PERMISSION_RECORD_AUDIO = 1007;
    // permission
    private static final String PERM_COARSE_LOCATION = permission.ACCESS_COARSE_LOCATION;
    private static final String PERM_FINE_LOCATION = permission.ACCESS_FINE_LOCATION;
    private static final String PERM_CAMERA = permission.CAMERA;
    private static final String PERM_WRITE_EXTERNAL_STORAGE = permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERM_READ_CONTACTS = permission.READ_CONTACTS;
    private static final String PERM_CALL_PHONE = permission.CALL_PHONE;
    private static final String PERM_SEND_SMS = permission.SEND_SMS;
    private static final String PERM_RECORD_AUDIO = permission.RECORD_AUDIO;
    private static String TAG = "PermissionUtils";
    private static PermissionUtils instance;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    /**
     * navigate to app setting - enable permission
     *
     * @param mActivity
     * @param message
     * @param requestCode
     */
    public void navigateToSettingPermissionDialog(final Activity mActivity, Fragment fragment, String message, final int requestCode) {
        DialogUtils.getInstance().showCustomYesNoAlertDialog(mActivity, false,
                message,
                mActivity.getString(R.string.go_to_setting),
                mActivity.getString(R.string.no),
                new DialogUtils.OnDialogOkCancelButtonClickListener() {
                    @Override
                    public void onOkButtonClick() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts(Constant.const_package, mActivity.getPackageName(), null);
                        intent.setData(uri);

                        if (fragment != null) {
                            fragment.startActivityForResult(intent, requestCode);
                        } else {
                            mActivity.startActivityForResult(intent, requestCode);
                        }
                    }

                    @Override
                    public void onCancelButtonClick() {

                    }
                });
    }

    /**
     * check location permission is granted, or not
     *
     * @param mActivity
     * @return
     */
    public boolean checkLocationPermission(Activity mActivity) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLocationPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_COARSE_LOCATION, PERM_FINE_LOCATION}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_COARSE_LOCATION, PERM_FINE_LOCATION}, requestCode);
            }
            return false;
        }
    }

    public boolean checkLocationPermissionNeverAskAgain(Activity mActivity) {
        return checkLocationPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkLocationPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_COARSE_LOCATION)
                    && mFragment.shouldShowRequestPermissionRationale(PERM_FINE_LOCATION);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_COARSE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_FINE_LOCATION);
        }
    }

    /**
     * check camera permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkCameraPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CAMERA}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CAMERA}, requestCode);
            }
            return false;
        }
    }

    public boolean checkCameraPermissionNeverAskAgain(Activity mActivity) {
        return checkCameraPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkCameraPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_CAMERA);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_CAMERA);
        }
    }

    /**
     * check camera permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkCameraStoragePermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CAMERA, PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CAMERA, PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            return false;
        }
    }

    public boolean checkCameraStoragePermissionNeverAskAgain(Activity mActivity) {
        return checkCameraStoragePermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkCameraStoragePermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_CAMERA)
                    && mFragment.shouldShowRequestPermissionRationale(PERM_WRITE_EXTERNAL_STORAGE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_CAMERA)
                    && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * check storage permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkStoragePermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            return false;
        }
    }

    public boolean checkStoragePermissionNeverAskAgain(Activity mActivity) {
        return checkStoragePermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkStoragePermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_WRITE_EXTERNAL_STORAGE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * check contact permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkContactsPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_READ_CONTACTS}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_READ_CONTACTS}, requestCode);
            }
            return false;
        }
    }

    public boolean checkContactsPermissionNeverAskAgain(Activity mActivity) {
        return checkContactsPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkContactsPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_READ_CONTACTS);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_READ_CONTACTS);
        }
    }

    /**
     * check call permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkCallPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CALL_PHONE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CALL_PHONE}, requestCode);
            }
            return false;
        }
    }

    public boolean checkCallPermissionNeverAskAgain(Activity mActivity) {
        return checkCallPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkCallPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_CALL_PHONE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_CALL_PHONE);
        }
    }

    /**
     * check sms permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkSmsPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_SEND_SMS}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_SEND_SMS}, requestCode);
            }
            return false;
        }
    }

    public boolean checkSmsPermissionNeverAskAgain(Activity mActivity) {
        return checkSmsPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkSmsPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_SEND_SMS);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_SEND_SMS);
        }
    }

    /**
     * check record audio permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkRecordAudioPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_RECORD_AUDIO}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_RECORD_AUDIO}, requestCode);
            }
            return false;
        }
    }

    public boolean checkRecordAudioPermissionNeverAskAgain(Activity mActivity) {
        return checkRecordAudioPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkRecordAudioPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_RECORD_AUDIO);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_RECORD_AUDIO);
        }
    }
}
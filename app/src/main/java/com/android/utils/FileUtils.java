package com.android.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.android.R;
import com.android.ui.App;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.provider.ContactsContract.AUTHORITY;

public class FileUtils {
    private static String TAG = "FileUtils";
    private static FileUtils instance;

    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static final String CAMERA = App.getAppContext().getResources().getString(R.string.app_name) + "/camera";

    public static final List<String> list_fileTypeImage = new ArrayList<String>(Arrays.asList("image/png", "image/jpg", "image/jpeg"));
    public static final List<String> list_fileTypeVideo = new ArrayList<String>(Arrays.asList("video/mp4"));

    public static final String JPG_TYPE = ".jpg";
    public static final String MP4_TYPE = ".mp4";
    public static final String MP3_TYPE = ".mp3";

    public FileUtils() {

    }

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public void copyFile(Context mContext, File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            out.close();
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(dst.getAbsoluteFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * store file
     *
     * @param mContext
     * @param sourceuri
     */
    public Uri storeFile(Context mContext, Uri sourceuri) {
        // Find the dir to save cached images
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), FileUtils.CAMERA);
        } else {
            file = mContext.getCacheDir();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        File destinationFile = null;
        if (FileUtils.list_fileTypeVideo.contains(FileUtils.getInstance().getMimeType(mContext, sourceuri))) {
            destinationFile = new File(file, System.currentTimeMillis() + FileUtils.MP4_TYPE);
        } else {
            destinationFile = new File(file, System.currentTimeMillis() + FileUtils.JPG_TYPE);
        }

        copyFile(mContext, new File(sourceuri.getPath()), destinationFile);

        /*try {
            InputStream inputStream = mContext.getContentResolver().openInputStream(sourceuri);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(destinationFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                copy(inputStream, out);
                inputStream.close();
            }

            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return Uri.parse(destinationFile.getAbsolutePath());
    }

    /**
     * store file
     *
     * @param mContext
     */
    public int storeFileIntoTemp(Context mContext, List<File> list_sourceuri) {
        int count = 0;

        // Find the dir to save cached images
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), CAMERA);
        } else {
            file = mContext.getCacheDir();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 0; i < list_sourceuri.size(); i++) {
            if (FileUtils.list_fileTypeImage.contains(FileUtils.getInstance().getMimeType(mContext, Uri.fromFile(list_sourceuri.get(i))))) {
                count++;

                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {
                    File destinationFile = new File(file, (count) + FileUtils.JPG_TYPE);

                    bis = new BufferedInputStream(new FileInputStream(list_sourceuri.get(i)));
                    bos = new BufferedOutputStream(new FileOutputStream(destinationFile.getPath(), false));
                    byte[] buf = new byte[1024];
                    bis.read(buf);
                    do {
                        bos.write(buf);
                    } while (bis.read(buf) != -1);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bis != null) bis.close();
                        if (bos != null) bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return count;
    }

    /**
     * store file
     *
     * @param mContext
     */
    public String storeRowFileIntoTemp(Context mContext, int rawFile, String format) {
        // Find the dir to save cached images
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), CAMERA);
        } else {
            file = mContext.getCacheDir();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        File destinationFile = new File(file, System.currentTimeMillis() + format);

        try {
            InputStream in = mContext.getResources().openRawResource(rawFile);
            FileOutputStream out = new FileOutputStream(destinationFile.getPath());
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destinationFile.getAbsolutePath();
    }

    /**
     * create temp file - image
     *
     * @param mContext
     * @return
     * @throws IOException
     */
    public void removeTempFolder(Context mContext) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File tempfile = new File(Environment.getExternalStorageDirectory(), CAMERA);

                String[] children = tempfile.list();
                if (children != null && children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        new File(tempfile, children[i]).delete();
                    }
                    if (tempfile.isDirectory()) {
                        tempfile.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * create temp file - image
     *
     * @param mContext
     * @return
     * @throws IOException
     */
    public File createTempCameraImageFile(Context mContext) throws IOException {
        File tempFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tempFile = new File(Environment.getExternalStorageDirectory(), CAMERA);
        } else {
            tempFile = mContext.getCacheDir();
        }

        // remove folder
        String[] children = tempFile.list();
        if (children != null && children.length > 0) {
            for (int i = 0; i < children.length; i++) {
                new File(tempFile, children[i]).delete();
            }
            if (tempFile.isDirectory()) {
                tempFile.delete();
            }
        }

        // create folder
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        return new File(tempFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
    }

    /**
     * get bitmap from path
     *
     * @param path
     * @return
     */
    public Bitmap convertToBitmap(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    /**
     * get real path getFileFromContentProvider uri
     *
     * @param mContext
     * @param uri
     * @return
     */
    public String getPathFromUri(Context mContext, Uri uri) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return uri.getPath();
        }
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param mContext The context.
     * @param uri      The Uri to query.
     */
    public String getPath(final Context mContext, final Uri uri) {
        String absolutePath = getLocalPath(mContext, uri);
        return absolutePath != null ? absolutePath : uri.toString();
    }

    private String getLocalPath(final Context mContext, final Uri uri) {
        Debugger.logD(TAG + " File -",
                "Authority: " + uri.getAuthority() +
                        ", Fragment: " + uri.getFragment() +
                        ", Port: " + uri.getPort() +
                        ", Query: " + uri.getQuery() +
                        ", Scheme: " + uri.getScheme() +
                        ", Host: " + uri.getHost() +
                        ", Segments: " + uri.getPathSegments().toString());

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(mContext, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if ("home".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/documents/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4);
                }

                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };

                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                        String path = getDataColumn(mContext, contentUri, null, null);
                        if (path != null) {
                            return path;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(mContext, uri);
                File cacheDir = getDocumentCacheDir(mContext);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(mContext, uri, destinationPath);
                }

                return destinationPath;
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is local.
     */
    public boolean isLocalStorageDocument(Uri uri) {
        return AUTHORITY.equals(uri.getAuthority());
    }

    /**
     * generate file name
     *
     * @param name
     * @param directory
     * @return
     */
    @Nullable
    public File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Debugger.logE(TAG, e.getMessage());
            return null;
        }

        return file;
    }

    /**
     * save file from uri
     *
     * @param mContext
     * @param uri
     * @param destinationPath
     */
    private void saveFileFromUri(Context mContext, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = mContext.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * shrink bitmap
     *
     * @param file
     * @return
     */
    public Bitmap ShrinkBitmap(String file) {
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

            bmpFactoryOptions.inSampleSize = 1;
            bmpFactoryOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

            ExifInterface exif = new ExifInterface(file);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Debugger.logE(TAG, "orientation : " + orientation);

            // create a matrix object
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                matrix.postRotate(-90); // anti-clockwise by 90 degrees
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                matrix.postRotate(180); // clockwise by 180 degrees
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                matrix.postRotate(90); // clockwise by 90 degrees
            else
                matrix.postRotate(0); // clockwise by 0 degrees

            // create a new bitmap getFileFromContentProvider the original using the matrix to transform the result
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return rotatedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get mime type
     *
     * @param mContext
     * @param uri
     * @return
     */
    public String getMimeType(Context mContext, Uri uri) {
        File file = new File(getPath(mContext, uri));

        String extension = getExtension(file.getName());
        if (extension.length() > 0) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));
        }

        return "application/octet-stream";
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * get path getFileFromContentProvider uri
     *
     * @param mActivity
     * @param uri
     * @return
     */
    public String getFilePath(Activity mActivity, Uri uri) {
        try {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            if (isKitKat && DocumentsContract.isDocumentUri(mActivity, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(mActivity, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(mActivity, contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
                return getDataColumn(mActivity, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param uri
     * @return
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param mContext
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public String getDataColumn(Context mContext, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = mContext.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return null;
    }

    /**
     * get file from content provider
     *
     * @param context
     * @param uri
     * @return
     * @throws IOException
     */
    public File getFileFromContentProvider(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String fileName = getFileName(context, uri);
        String[] splitName = splitFileName(fileName);
        File tempFile = File.createTempFile(splitName[0], splitName[1]);
        tempFile = rename(tempFile, fileName);
        tempFile.deleteOnExit();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            copy(inputStream, out);
            inputStream.close();
        }

        if (out != null) {
            out.close();
        }
        return tempFile;
    }

    /**
     * split filename
     *
     * @param fileName
     * @return
     */
    public String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }

    /**
     * get filename
     *
     * @param context
     * @param uri
     * @return
     */
    public String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    /**
     * rename file
     *
     * @param file
     * @param newName
     * @return
     */
    public File rename(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.equals(file)) {
            if (newFile.exists() && newFile.delete()) {
                Log.d("FileUtil", "Delete old " + newName + " file");
            }
            if (file.renameTo(newFile)) {
                Log.d("FileUtil", "Rename file to " + newName);
            }
        }
        return newFile;
    }

    /**
     * copy file
     *
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public long copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public boolean exist(Context context, String path) {
        File file = new File(path);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete file
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        new File(filePath).delete();
    }

    /**
     * save bitmap to file
     *
     * @param mContext
     * @param bitmap
     * @return
     */
    public File saveBitmapToFile(Context mContext, Bitmap bitmap) {
        final File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), CAMERA);
        } else {
            file = mContext.getCacheDir();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        final File destFile = new File(file, System.currentTimeMillis() + FileUtils.JPG_TYPE);
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destFile;
    }

    /**
     * get document cache directory directory
     *
     * @param context
     * @return
     */
    public File getDocumentCacheDir(@NonNull Context context) {
        File dir = context.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * create thumbnail from video file
     *
     * @param imageView
     */
    public void createThumbnailVideo(String filePath, ImageView imageView) {
        try {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            imageView.setImageBitmap(thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the Intent for camera
     *
     * @return The intent for opening a camera
     */
    public Intent intentForCamera(Context mContext, File photoFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(mContext,
                mContext.getPackageName() + ".provider",
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }

    /**
     * Get the Intent for selecting image to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     */
    public Intent intentForGallery() {
        String[] mimeTypes = {"image/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

    /**
     * compressed bitmap
     *
     * @param bitmap
     * @return
     */
    public Bitmap getResizedBitmap(Activity mContext, Bitmap bitmap) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int newWidth = displayMetrics.widthPixels;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = (scaleWidth) * height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleWidth);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}

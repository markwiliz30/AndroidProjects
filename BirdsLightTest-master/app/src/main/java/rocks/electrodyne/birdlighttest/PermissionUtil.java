package rocks.electrodyne.birdlighttest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PermissionUtil {
    /*
     * Check if version is marshmallow and above.
     * Used in deciding to ask runtime permission
     * */

    static final String PREFS_FILE_NAME = "permissions";

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime){
        SharedPreferences sharedPreference = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission){
        return context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }

    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }
    private static boolean shouldAskPermission(Context context, String permission){
        if (shouldAskPermission()) {
            int permissionResult = ActivityCompat.checkSelfPermission(context, permission);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
    public static void checkPermission(Activity context, String permission, PermissionAskListener listener){

       // String[] unApprovedPermissions = new String[]{};
       // ArrayList<String> unApprovedList = new ArrayList<>();

      //  for (int i = 0; i < permission.length; i++) {
     //       unApprovedList.add(permission[i]);
            /*
             * If permission is not granted
             * */
            if (shouldAskPermission(context, permission)) {
                /*
                 * If permission denied previously
                 * */
                if (context.shouldShowRequestPermissionRationale(permission)) {
                    listener.onPermissionPreviouslyDenied();
                } else {
                    /*
                     * Permission denied or first time requested
                     * */
                    if (isFirstTimeAskingPermission(context, permission)) {
                        firstTimeAskingPermission(context, permission, false);
                        listener.onNeedPermission();
                    } else {
                        /*
                         * Handle the feature without permission or ask user to manually allow permission
                         * */
                        listener.onPermissionDisabled();
                    }
                }
            } else {
                listener.onPermissionGranted();
            }

    }
    /*
     * Callback on various cases on checking permission
     *
     * 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
     *     If permission is already granted, onPermissionGranted() would be called.
     *
     * 2.  Above M, if the permission is being asked first time onNeedPermission() would be called.
     *
     * 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
     *     would be called.
     *
     * 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
     *     check box on previous request permission, onPermissionDisabled() would be called.
     * */
    public interface PermissionAskListener {
        /*
         * Callback to ask permission
         * */
        void onNeedPermission();
        /*
         * Callback on permission denied
         * */
        void onPermissionPreviouslyDenied();
        /*
         * Callback on permission "Never show again" checked and denied
         * */
        void onPermissionDisabled();
        /*
         * Callback on permission granted
         * */
        void onPermissionGranted();
    }
}

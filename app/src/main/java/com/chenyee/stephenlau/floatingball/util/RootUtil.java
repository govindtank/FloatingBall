package com.chenyee.stephenlau.floatingball.util;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by stephenlau on 18-3-13.
 */

public final class RootUtil {

    private static final String TAG = RootUtil.class.getSimpleName();

    /**
     * Contains all possible places to check binaries
     */
    private static final String[] pathList;

    /**
     * The binary which grants the root privileges
     */
    private static final String KEY_SU = "su";

    static {
        pathList = new String[]{
                "/sbin/",
                "/system/bin/",
                "/system/xbin/",
                "/data/local/xbin/",
                "/data/local/bin/",
                "/system/sd/xbin/",
                "/system/bin/failsafe/",
                "/data/local/"
        };
    }

    public static boolean isDeviceRooted() {
        return doesFileExists(KEY_SU);
    }

    /**
     * Checks the all path until it finds it and return immediately.
     *
     * @param value must be only the binary name
     * @return if the value is found in any provided path
     */
    private static boolean doesFileExists(String value) {
        boolean result = false;
        for (String path : pathList) {
            File file = new File(path + "/" + value);
            result = file.exists();
            if (result) {
                Log.d(TAG, path + " contains su binary");
                break;
            }
        }
        return result;
    }
    public static boolean rootCommand(final String command){
        new Thread(new Runnable() {
            @Override
            public void run() {

//                try {
//                    Runtime.getRuntime().exec(new String[]{"su", "-c", command});
////                    Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
////                    process.waitFor();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                try{
                    Process su = Runtime.getRuntime().exec("su");
                    DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

                    outputStream.writeBytes(command + "\n");
                    outputStream.flush();

                    outputStream.writeBytes("exit\n");
                    outputStream.flush();
                    try {
                        su.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();


        return true;
    }
}

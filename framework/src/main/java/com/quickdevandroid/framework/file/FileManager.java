package com.quickdevandroid.framework.file;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.quickdevandroid.framework.QDFApplication;
import com.quickdevandroid.framework.permission.PermissionManager;

import java.io.File;

/**
 * 文件管理封装，提供常见文件管理功能
 * Created by linxiao on 2016/12/14.
 */
public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();

    private FileManager() {}

    /**
     * 复制文件操作
     * @param src 源文件
     * @param targetPath 目标路径
     * @return
     */
    public static FileCopyTask copyFileOperate(File src, String targetPath) {
        return new FileCopyTask().addSrc(src).setTargetPath(targetPath);
    }
    
    /**
     * 移动文件操作
     * @param src 源文件
     * @param targetPath 目标路径
     * @return
     */
    public static FileMoveTask moveFileOperate(File src, String targetPath) {
        return new FileMoveTask().addSrc(src).setTargetPath(targetPath);
    }

    /**
     * 删除文件操作
     * @param src 源文件
     * @return
     */
    public static FileDeleteTask deleteFileOperate(File src) {
        return new FileDeleteTask().addSrc(src);
    }

    /**
     * 是否挂载sd卡
     * @return true 挂载; false 未挂载
     */
    public static boolean existExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查是否有文件操作权限
     * */
    public static boolean hasFileOperatePermission() {
        boolean hasPermission = PermissionManager.checkPermissionsGranted(QDFApplication.getAppContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasPermission) {
            Log.e(TAG, "can't operate files, permission denied");
        }
        return hasPermission;
    }

    /**
     * 检查是否为有效的路径字符串
     * */
    public static boolean checkIsAvailablePathString(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        String ext = getExternalStorageRootString();
        if (ext.length() > path.length()) {
            return false;
        }
        if (path.substring(0, ext.length()).equals(ext)) {
            return true;
        }
        String intern = getInternalStorageRootString();
        if (intern.length() > path.length()) {
            return false;
        }
        if (path.substring(0, intern.length()).equals(intern)) {
            return true;
        }
        // 如果有使用系统路径的可能则添加此判断
//        if (path.indexOf("/") == 0) {
//            return true;
//        }
        Log.i(TAG, "unknown path string : " + path);
        return false;
    }

    /**
     * 获取当前SD卡的根路径
     * */
    @NonNull
    public static String getExternalStorageRootString() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取当前SD卡的根路径
     * */
    @NonNull
    public static File getExternalStorageRoot() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取内部存储路径
     * */
    @NonNull
    public static String getInternalStorageRootString() {
        return QDFApplication.getAppContext().getFilesDir().getPath();
    }

    /**
     * 获取内部存储路径
     * */
    @NonNull
    public static File getInternalStorageRoot() {
        return QDFApplication.getAppContext().getFilesDir();
    }

    /**
     * 通过路径字符串生成File对象，包含安全检查
     *
     * */
    public static File pathStringToFile(String path) {
        if (!hasFileOperatePermission()) {
            return null;
        }
        if (checkIsAvailablePathString(path)) {
            return new File(path);
        } else {
            return null;
        }
    }

    /**
     * 检查文件是否存在
     * */
    public static boolean isExist(String dir) {
        return hasFileOperatePermission() && new File(dir).exists();
    }

    /**
     * 重命名文件或文件夹
     * */
    public static boolean rename(String filePath, String oldName, String newName) {
        if (!hasFileOperatePermission()) {
            return false;
        }
        File renameFile = new File(filePath + File.separator + newName);
        if (renameFile.exists()) {
            Log.e(TAG, "rename failed, new name");
            return false;
        }
        return renameFile.renameTo(new File(filePath + newName));
    }

    /**
     * 新建文件夹
     * */
    public static boolean mkdir(String path) {
        if (!hasFileOperatePermission()) {
            return false;
        }
        File file = new File(path);
        return file.exists() || file.mkdirs();
    }

}

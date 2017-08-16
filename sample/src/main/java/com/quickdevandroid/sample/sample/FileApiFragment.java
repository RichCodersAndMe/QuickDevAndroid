package com.quickdevandroid.sample.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quickdevandroid.framework.dialog.AlertDialogManager;
import com.quickdevandroid.framework.file.FileCountListener;
import com.quickdevandroid.framework.file.FileManager;
import com.quickdevandroid.framework.file.FileSizeListener;
import com.quickdevandroid.framework.fragment.BaseFragment;
import com.quickdevandroid.framework.permission.PermissionManager;
import com.quickdevandroid.framework.permission.RequestPermissionCallback;
import com.quickdevandroid.sample.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileApiFragment extends BaseFragment {
    private String totalFilePath = FileManager.getExternalStorageRoot() + File.separator + "QuickDevFramework";

    @BindView(R.id.tvTotalSize)
    TextView tvTotalSize;
    @BindView(R.id.tvCurrentSize)
    TextView tvCurrentSize;
    @BindView(R.id.tvTotalSum)
    TextView tvTotalSum;
    @BindView(R.id.tvCurrentSum)
    TextView tvCurrentSum;

    @BindView(R.id.tvHasSDCard)
    TextView tvHasSDCard;
    @BindView(R.id.tvHasPermission)
    TextView tvHasPermission;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_file_api, container);
        ButterKnife.bind(this, getContentView());
        PermissionManager.performWithPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        .perform(getActivity(), new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                try {
                    FileManager.pathStringToFile(totalFilePath).mkdir();
                    File txtFile = FileManager.pathStringToFile(totalFilePath + File.separator + "text.txt");
                    txtFile.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(txtFile);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                    byte[] buff = new byte[1024000];
                    for (int i = 0; i < 1024000; i++) {
                        buff[i] = 1;
                    }
                    bufferedOutputStream.write(buff, 0, 1024000);
                    bufferedOutputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("请授予文件管理权限");
            }
        });
    }

    @OnClick(R.id.btnCopyFileSimple)
    public void OnCopyFileSimple() {
        FileManager.pathStringToFile(totalFilePath + File.separator + "Copy").mkdir();
        FileManager.copyFileOperate(FileManager.pathStringToFile(totalFilePath + File.separator + "text.txt"),
                totalFilePath + File.separator + "Copy").setFileSizeListener(new FileSizeListener() {
            @Override
            public void onStart() {}

            @Override
            public void onProgressUpdate(final double count, final double current) {
                tvCurrentSize.setText("currentSize:" + current + "KB");
                tvTotalSize.setText("totalSize:" + count + "KB");
            }

            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "copy success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String failMsg) {}
        }).setFileCountListener(new FileCountListener() {
            @Override
            public void onStart() {}

            @Override
            public void onProgressUpdate(final long count, final long current) {
                tvCurrentSum.setText("currentSum:" + current + "");
                tvTotalSum.setText("totalSum:"  + count + "");
            }

            @Override
            public void onSuccess() {}

            @Override
            public void onFail(String failMsg) {}
        }).execute();
    }

    @OnClick(R.id.btnCopyFolderSimple)
    public void OnCopyFolderSimple() {
        try {
            FileManager.pathStringToFile(totalFilePath + File.separator + "FolderExample").mkdir();
            FileManager.pathStringToFile(totalFilePath + File.separator + "FolderExample"+ File.separator + "1").mkdir();
            FileManager.pathStringToFile(totalFilePath + File.separator + "FolderExample"+ File.separator + "2").mkdir();
            FileManager.pathStringToFile(totalFilePath + File.separator + "CopyFolder").mkdir();
            FileManager.pathStringToFile(totalFilePath).mkdir();

            File txtFile = FileManager.pathStringToFile(totalFilePath + File.separator + "FolderExample"+
                    File.separator + "2" + File.separator + "text.txt");
            File txtFile_ = FileManager.pathStringToFile(totalFilePath + File.separator + "FolderExample"+
                    File.separator + "text.txt");
            txtFile.createNewFile();
            txtFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(txtFile);
            FileOutputStream outputStream_ = new FileOutputStream(txtFile_);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            BufferedOutputStream bufferedOutputStream_ = new BufferedOutputStream(outputStream_);
            byte[] buff = new byte[1024000];
            for (int i = 0; i < 1024000; i++) {
                buff[i] = 1;
            }
            bufferedOutputStream.write(buff, 0, 1024000);
            bufferedOutputStream_.write(buff, 0, 1024000);
            bufferedOutputStream.close();
            outputStream.close();
            bufferedOutputStream_.close();
            outputStream_.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileManager.copyFileOperate(
                FileManager.pathStringToFile(
                    totalFilePath + File.separator + "FolderExample"),
                    totalFilePath + File.separator + "CopyFolder"
        ).setFileSizeListener(new FileSizeListener() {
            @Override
            public void onStart() {}

            @Override
            public void onProgressUpdate(final double count, final double current) {
                tvCurrentSize.setText("currentSize:" + current + "KB");
                tvTotalSize.setText("totalSize:" + count + "KB");

            }

            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "copy success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String failMsg) {}
        }).setFileCountListener(new FileCountListener() {
            @Override
            public void onStart() {}

            @Override
            public void onProgressUpdate(final long count, final long current) {
                tvCurrentSum.setText("currentSum:" + current + "");
                tvTotalSum.setText("totalSum:"  + count + "");

            }

            @Override
            public void onSuccess() {}

            @Override
            public void onFail(String failMsg) {}
        }).execute();
    }

    @OnClick(R.id.btnDeleteFolderSimple)
    public void OnDeleteFolderSimple() {
        FileManager.deleteFileOperate(FileManager.pathStringToFile(totalFilePath + File.separator +
                "FolderExample")).setFileCountListener(new FileCountListener() {
            @Override
            public void onStart() {}

            @Override
            public void onProgressUpdate(final long count, final long current) {
                tvCurrentSum.setText("currentSum:" + current + "");
                tvTotalSum.setText("totalSum:"  + count + "");
            }

            @Override
            public void onSuccess() {
                
            }

            @Override
            public void onFail(String failMsg) {
                Toast.makeText(getActivity(), failMsg, Toast.LENGTH_SHORT).show();
            }
        }).execute();
        tvHasSDCard.setText(getString(R.string.is_exist_sd_card) + ": " + FileManager.existExternalStorage());
        tvHasPermission.setText(getString(R.string.has_file_permission) + ": " + FileManager.hasFileOperatePermission());
    }

}

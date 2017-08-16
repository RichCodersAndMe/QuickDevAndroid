package com.quickdevandroid.sample.sample;

import android.app.Dialog;
import android.view.View;

import com.quickdevandroid.framework.dialog.BaseBottomDialogFragment;
import com.quickdevandroid.sample.R;


/**
 *
 * Created by LinXiao on 2016-12-12.
 */
public class SampleBottomDialogFragment extends BaseBottomDialogFragment {

    @Override
    protected int configureContentViewRes() {
        return R.layout.dialog_bottom_sample;
    }

    @Override
    protected void configureDialog(final Dialog dialog, View contentView) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}

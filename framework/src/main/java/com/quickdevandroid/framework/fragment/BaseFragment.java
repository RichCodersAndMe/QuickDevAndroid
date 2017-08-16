package com.quickdevandroid.framework.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * base Fragment of entire project
 * <p>template for Fragments in the project, used to define common methods </p>
 * */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
//        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            onCreateContentView(inflater, container, savedInstanceState);
        }
        
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    /**
     * set the content view of this fragment by layout resource id
     * <p>if the content view has been set before, this method will not work again</p>
     * @param resId
     * layout resource id of content view
     * @param container
     * parent ViewGroup of content view, get it in
     * {@link #onCreateContentView(LayoutInflater, ViewGroup, Bundle)}
     * */
    protected void setContentView(@LayoutRes int resId, ViewGroup container) {
        if (rootView != null) {
            Log.w(TAG, "contentView has set already");
            return;
        }
        rootView = LayoutInflater.from(getActivity()).inflate(resId, container, false);
    }
    
    /**
     * set the content view of this fragment by layout resource id
     * <p>if the content view has been set before, this method will not work again</p>
     *
     * @param contentView content view of this fragment
     * */
    protected void setContentView(View contentView) {
        if (rootView != null) {
            Log.w(TAG, "contentView has set already");
            return;
        }
        rootView = contentView;
    }

    protected View getContentView() {
        return rootView;
    }
    
    /**
     * execute on method onCreateView(), put your code here which you want to do in onCreateView()<br>
     * <strong>execute {@link #setContentView(int, ViewGroup)} or {@link #setContentView(View)} to
     * set the root view of this fragment like activity</strong>
     * */
    protected abstract void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    
    
    /**
     * use this method instead of findViewById() to simplify view initialization <br>
     * it's not unchecked because of T extends View
     * */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(View layoutView, @IdRes int resId) {
        return (T) layoutView.findViewById(resId);
    }

}

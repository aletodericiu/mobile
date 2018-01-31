package com.fresh.rares.android_api.activity;

/**
 * Created by Rares Abrudan on 12/20/2017.
 *
 * @email - raresabr@gmail.com
 */

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.fresh.rares.android_api.R;

public class BaseActivity extends AppCompatActivity
{
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog()
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        hideProgressDialog();
    }

}

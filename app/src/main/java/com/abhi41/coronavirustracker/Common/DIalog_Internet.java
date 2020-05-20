package com.abhi41.coronavirustracker.Common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.abhi41.coronavirustracker.MainActivity;
import com.abhi41.coronavirustracker.R;
import com.wessam.library.NetworkChecker;

public class DIalog_Internet {
    public static boolean fetchEnableTrue;
    public static void dialog_internet(final Context context)
    {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_internet);
        dialog.setCancelable(false);
        dialog.setTitle("No Internet Connection!");
        dialog.show();

        Button dialog_retry = dialog.findViewById(R.id.dialog_retry);

        dialog_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkChecker.isNetworkConnected(context))
                {
                    dialog.dismiss();
                    fetchEnableTrue = true;


                    Intent intent = new Intent("custom-message");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                    intent.putExtra("fetchEnableTrue",fetchEnableTrue);

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });
    }
}

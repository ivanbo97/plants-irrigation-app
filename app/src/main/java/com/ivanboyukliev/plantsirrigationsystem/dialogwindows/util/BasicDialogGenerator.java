package com.ivanboyukliev.plantsirrigationsystem.dialogwindows.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.DeleteConfirmationListener;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELETE_TOPIC_DIALOG_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DEL_ITEM_DIALOG_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DEL_TOPIC_BTN_TXT;

public class BasicDialogGenerator {

    private DeleteConfirmationListener deleteConfirmationListener;

    public BasicDialogGenerator(DeleteConfirmationListener deleteConfirmationListener) {
        this.deleteConfirmationListener = deleteConfirmationListener;
    }

    public AlertDialog generateDeleteConfirmation(DatabaseReference itemForDeletion, Context parentContext) {
        AlertDialog quittingDialogBox = new AlertDialog.Builder(parentContext)
                .setTitle(DEL_ITEM_DIALOG_TITLE)
                .setMessage(DELETE_TOPIC_DIALOG_TITLE)
                .setIcon(R.drawable.ic_baseline_delete_forever_24)
                .setPositiveButton(DEL_TOPIC_BTN_TXT, (dialog, whichButton) -> {
                    itemForDeletion.removeValue((error, ref) -> {
                        if (error == null) {
                            deleteConfirmationListener.onDeleteConfirmationSent();
                            return;
                        }
                        Toast.makeText(parentContext, "Firebase error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    });
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create();
        return quittingDialogBox;
    }
}

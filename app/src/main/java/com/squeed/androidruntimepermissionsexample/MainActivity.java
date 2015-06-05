package com.squeed.androidruntimepermissionsexample;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE_CONTACT = 0;
    private static final int REQUEST_CODE_CALL_LOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn__contact_add).setOnClickListener(this);
        findViewById(R.id.btn__contact_read).setOnClickListener(this);
        findViewById(R.id.btn__call_add).setOnClickListener(this);
        findViewById(R.id.btn__call_read).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.btn__contact_add) {
            addContact();
        } else if (viewId == R.id.btn__contact_read) {
            readLatestContact();
        } else if (viewId == R.id.btn__call_add) {
            addCallLogEvent();
        } else if (viewId == R.id.btn__call_read) {
            readLatestCallLogEvent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // We know that the array only contains one result since we only request one permission
        // TODO: Do we? Is this a list?
        boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (REQUEST_CODE_CONTACT == requestCode) {
            Toast.makeText(MainActivity.this, granted ? R.string.contact_permission_granted : R.string.contact_permission_denied, Toast.LENGTH_SHORT).show();
        } else if (REQUEST_CODE_CALL_LOG == requestCode) {
            Toast.makeText(MainActivity.this, granted ? R.string.call_permission_granted : R.string.call_permission_denied, Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void addContact() {
        if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            String contactName = ((EditText) findViewById(R.id.et__contact_name)).getText().toString();
            ContactsUtil.addContact(this, contactName);
            Toast.makeText(MainActivity.this, getString(R.string.contact_added, contactName), Toast.LENGTH_SHORT).show();
            return;
        }

        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_CONTACT);
    }

    private void readLatestContact() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            String latestContactName = ContactsUtil.getLatestContactName(this);
            Toast.makeText(MainActivity.this, getString(R.string.contact_latest, latestContactName), Toast.LENGTH_SHORT).show();
            return;
        }

        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACT);
    }

    private void addCallLogEvent() {
        if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            String callNumber = ((EditText) findViewById(R.id.et__call_number)).getText().toString();
            CallLogUtil.addCallLogEvent(this, callNumber);
            Toast.makeText(MainActivity.this, getString(R.string.call_added, callNumber), Toast.LENGTH_SHORT).show();
            return;
        }

        requestPermissions(new String[]{Manifest.permission.WRITE_CALL_LOG}, REQUEST_CODE_CALL_LOG);
    }

    private void readLatestCallLogEvent() {
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            String latestCallLogEventNumber = CallLogUtil.getLatestCallLogEventNumber(this);
            Toast.makeText(MainActivity.this, getString(R.string.call_latest, latestCallLogEventNumber), Toast.LENGTH_SHORT).show();
            return;
        }

        requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CODE_CALL_LOG);
    }
}

package com.example.newcalendar;

import android.content.Intent;

public interface ActivityResultListener {
    void onResult(int resultCode, Intent data);
}

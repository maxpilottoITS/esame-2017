package com.maxpilotto.esame2017;

import android.content.ContentValues;

public interface Storable {
    ContentValues values(boolean includeId);

    default ContentValues values() {
        return values(false);
    }
}

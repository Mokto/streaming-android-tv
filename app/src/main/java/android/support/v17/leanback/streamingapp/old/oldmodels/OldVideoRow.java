/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package android.support.v17.leanback.streamingapp.old.oldmodels;

import android.support.v17.leanback.streamingapp.R;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class represents a row of Video cards fetched from the
 * url: {@link R.string.videos_url}
 */
public class OldVideoRow {
    @SerializedName("category") private String mCategory = "";
    @SerializedName("videos") private List<OldVideoCard> mVideos;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public List<OldVideoCard> getVideos() {
        return mVideos;
    }

    public void setVideos(List<OldVideoCard> videos) {
        mVideos = videos;
    }
}

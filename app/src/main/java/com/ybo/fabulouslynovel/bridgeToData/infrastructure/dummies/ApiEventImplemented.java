package com.ybo.fabulouslynovel.bridgeToData.infrastructure.dummies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ybo.fabdata.api.ApiModelEvent;
import com.ybo.fabdata.api.ApiModelMultiImage;
import com.ybo.fabdata.api.ApiModelPerformer;
import com.ybo.fabdata.api.ApiModelStats;
import com.ybo.fabdata.api.ApiModelVenue;

import java.util.ArrayList;
import java.util.List;

class ApiEventImplemented extends ApiModelEvent {
    @NonNull
    @Override
    public String getTitle() {
        return "Dwayne Jonshon back on FF party";
    }

    @NonNull
    @Override
    public String getUrl() {
        return "https://google.fr";
    }

    @NonNull
    @Override
    public String getDate() {
        return "2023-08-09T19:00:00";
    }

    @NonNull
    @Override
    public ApiModelStats getStats() {
        return new ApiModelStats() {
            @Override
            public Integer getListingCount() {
                return 0;
            }

            @Override
            public Integer getAveragePrice() {
                return 1000;
            }

            @Override
            public Integer getLowerPrice() {
                return 100;
            }

            @Override
            public Integer getHighestPrice() {
                return 3000;
            }
        };
    }

    @NonNull
    @Override
    public List<ApiModelPerformer> getPerformers() {
        List<ApiModelPerformer> outList = new ArrayList<>();
        outList.add(createDummyPerformer("Dwayne The Rock Johnson the real one"));
        outList.add(createDummyPerformer("Dwayne lookalike"));
        outList.add(createDummyPerformer("Other Dwayne lookalike"));
        return outList;
    }

    @NonNull
    @Override
    public String getType() {
        return "concert";
    }


    private ApiModelPerformer createDummyPerformer(String inName)
    {
        return new ApiModelPerformer() {
            @Override
            public int getId() {
                return 0;
            }

            @NonNull
            @Override
            public String getName() {
                return inName+" el maestro";
            }

            @NonNull
            @Override
            public String getShortName() {
                return inName;
            }

            @NonNull
            @Override
            public String getUrl() {
                return "https://google.fr";
            }

            @NonNull
            @Override
            public String getImage() {
                return "https://media.gqmagazine.fr/photos/637cf99e74e0a5c02d7399bc/1:1/w_4016,h_4016,c_limit/GettyImages-1434847758.jpg";
            }

            @NonNull
            @Override
            public ApiModelMultiImage getImages() {
                return new ApiModelMultiImage() {
                    @NonNull
                    @Override
                    public String getLargeImageUrl() {
                        return "https://media.gqmagazine.fr/photos/637cf99e74e0a5c02d7399bc/1:1/w_4016,h_4016,c_limit/GettyImages-1434847758.jpg";
                    }

                    @NonNull
                    @Override
                    public String getSmallImageUrl() {
                        return "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Dwayne_Johnson_2014_%28cropped%29.jpg/640px-Dwayne_Johnson_2014_%28cropped%29.jpg";
                    }

                    @NonNull
                    @Override
                    public String getMediumImageUrl() {
                        return "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Dwayne_Johnson_2014_%28cropped%29.jpg/640px-Dwayne_Johnson_2014_%28cropped%29.jpg";
                    }

                    @NonNull
                    @Override
                    public String getHugeImageUrl() {
                        return "https://media.gqmagazine.fr/photos/637cf99e74e0a5c02d7399bc/1:1/w_4016,h_4016,c_limit/GettyImages-1434847758.jpg";
                    }
                };
            }

            @Override
            public float getScore() {
                return inName.length();
            }
        };
    }

    @Override
    public int getId() {
        return 0;
    }

    @Nullable
    @Override
    public ApiModelVenue getVenue() {
        return null;
    }
}

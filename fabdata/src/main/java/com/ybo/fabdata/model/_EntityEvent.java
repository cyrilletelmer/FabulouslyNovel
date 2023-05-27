package com.ybo.fabdata.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ybo.fabdata.api.ApiModelEvent;
import com.ybo.fabdata.api.ApiModelPerformer;
import com.ybo.fabdata.api.ApiModelVenue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * implementation of EntityEvent
 * using Java for package-private modifier */
class _EntityEvent implements EntityEvent
    {

    private ApiModelEvent mEventFromAPI;
    _EntityEvent(ApiModelEvent inApiModelEvent)
        {
        mEventFromAPI = inApiModelEvent;
        }


    @NonNull
    @Override
    public String getTitle() {
        return mEventFromAPI.getTitle();
    }


    @NonNull
    @Override
    public EventType getType()
        {
        switch (mEventFromAPI.getType().toLowerCase(Locale.FRANCE))
            {
            case "concert": return EventType.CONCERT;
            case "music_festival": return EventType.FESTIVAL;
            default: return EventType.OTHER;
            }
        }


    @Override
    public LocalDate getStartingDate()
        {
        try {
            DateTimeFormatter vFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDate.parse(mEventFromAPI.getDate(), vFormatter);
            }
        catch (Exception ve)
            {
            return null;
            }
        }

    @NonNull
    @Override
    public String getImageURL(@NonNull ImageType inImageType)
        {
        // taking as image the image of the most popular performer in this event
        String outURLImage = null;
        ApiModelPerformer vBestPerformer = mEventFromAPI.getPerformers().stream().reduce(
                null,
                (inBestUpToNow, inCandidate) -> inBestUpToNow == null || inBestUpToNow.getScore()< inCandidate.getScore()? inCandidate:inBestUpToNow
                );
        if(vBestPerformer!= null) {
            outURLImage = vBestPerformer.getImage();
            if(inImageType == ImageType.SMALL && vBestPerformer.getImages().getSmallImageUrl()!=null)
                outURLImage = vBestPerformer.getImages().getSmallImageUrl();
            else if(inImageType == ImageType.MEDIUM && vBestPerformer.getImages().getMediumImageUrl()!=null)
                outURLImage = vBestPerformer.getImages().getMediumImageUrl();
            else if(inImageType == ImageType.BIG && vBestPerformer.getImages().getLargeImageUrl()!= null)
                outURLImage = vBestPerformer.getImages().getLargeImageUrl();
            else if(vBestPerformer.getImages().getHugeImageUrl()!= null)
                outURLImage = vBestPerformer.getImages().getHugeImageUrl();
        }

        return outURLImage;
        }

    @NonNull
    @Override
    public List<String> getPerformers()
        {
        return mEventFromAPI.getPerformers().stream().map(ApiModelPerformer::getName).collect(Collectors.toList());
        }


    @Override
    public String getAddress()
        {
        String outAddress = null;
        try
            {
            ApiModelVenue vVenue = mEventFromAPI.getVenue();
            if(vVenue!= null)
                {
                String vAddress = vVenue.getAddress();
                String vCity = vVenue.getCity();
                String vCountry = vVenue.getCountry();
                String vAddressComplement = vVenue.getExtendedAddress();
                if(vAddress!= null)
                    outAddress = vAddress;
                if(vAddressComplement!= null)
                    outAddress+=" "+vAddressComplement;
                if(vCity!=null)
                    outAddress+=" "+vCity;
                if(vCountry!= null)
                    outAddress+=" "+vCountry;
                }
            }
        catch (Exception ve){}
        return outAddress;
        }

    @Override
    public int getId()
        {
        return mEventFromAPI.getId();
        }

    @Override
    public Integer getMinimalPrice()
        {
        return mEventFromAPI.getStats().getLowerPrice();
        }

    @Nullable
    @Override
    public Integer getAveragePrice()
        {
        return mEventFromAPI.getStats().getAveragePrice();
        }
    }

package org.movie.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL() {
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //썸네일
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(path+"/"+"/s_"+uuid+"_"+imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
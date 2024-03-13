package org.movie.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {
    // field
    private String fileName;
    private String uuid;
    private String folderPath;

    // constructor

    // mehtod
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //썸네일
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

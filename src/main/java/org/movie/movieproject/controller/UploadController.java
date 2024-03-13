package org.movie.movieproject.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.movie.movieproject.dto.PageRequestDTO;
import org.movie.movieproject.dto.UploadResultDTO;
import org.movie.movieproject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// JSON을 전달받기 위해 RestController를 사용, RestController가 자동으로 mapping을 진행하여 Requestmapping 어노테이션이 필요가 없다
@RestController
@Log4j2
public class UploadController {
    // field
    @Value("${org.movie.upload.path}")
    private String uploadPath; // application설정에서 내가 설정한 경로

    //constructor

    // method
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(@RequestParam(value = "uploadFiles") MultipartFile[] uploadFiles) {
        List<UploadResultDTO> resultDTOLists = new ArrayList<UploadResultDTO>();

        for(MultipartFile file : uploadFiles) {
            // 이미지 파일만 업로드 가능
            if(file.getContentType().startsWith("image")==false) {
                log.warn("이미지 파일이 아닙니다.");
                // 403에러 : 클라이언트가 요청을 이해했지만, 서버가 요청을 거부, 요청 리소스에 대한 권한이 없을 때
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String originName = file.getOriginalFilename();
            log.info("Original fileName : " + originName);
            // lastIndexof로 문자를 잘라내어 파일 이름만 변수에 저장 후 값을 가져올 예정
            String fileName = originName.substring(originName.lastIndexOf("/")+1);
            log.info("fileName : " + fileName);

            // 날짜별 폴더 생성
            String folderPath = makeFolder();
            // UUID 생성
            String uuid = UUID.randomUUID().toString();
            // 내가 지정한 경로 + 생성된 날짜 + uuid값 + 파일이미지 이름.jpg
            String saveName = uploadPath+File.separator+folderPath+File.separator+uuid+"_"+fileName;
            Path savePath = Paths.get(saveName);
            try {
                //파일 저장
                file.transferTo(savePath);
                // 원본파일 저장할 때 썸네일도 생성하겠다.
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid +"_"+ fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                // 썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100,100);
                resultDTOLists.add(new UploadResultDTO(fileName, uuid, folderPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(resultDTOLists, HttpStatus.OK);
    }
    // 날짜 폴더 생성 메소드
    private String makeFolder() {
        // 현재 오늘 날짜를 yyyy/MM/dd 형태의 문자로 할당
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);
        File uploadPathFile = new File(uploadPath, folderPath);
        if(uploadPathFile.exists() == false) {
            uploadPathFile.mkdirs();
        }
        return folderPath;
    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFiles(@RequestParam(value = "fileName") String fileName) {
        ResponseEntity<byte[]> result = null;
        String srcFileName = null;
        try {
            // URLDecoder, URLEncoder은 try-catch문을 작성해야한다.
            srcFileName = URLDecoder.decode(fileName, "utf-8");
            File file = new File(uploadPath+File.separator + srcFileName);
            HttpHeaders header = new HttpHeaders();
            // probeContentType() 괄호 안의 타입을 체크해서 알려주는 메소드 / 파일의 MIME타입을 헤더에 추가한다.
            header.add("content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header,  HttpStatus.OK );
        } catch (Exception e) {
            // 서버 에러 500번때 리턴
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(@RequestParam(value = "fileName")String fileName) {
        System.out.println(fileName);
        System.out.println("여기입니다");
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName,"utf-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            System.out.println(file);
            boolean result = file.delete();
            System.out.println(result);
            // getParent()메소드는 부모경로라는 의미로 밑의 메소드로 인하여 폴더까지 전부 삭제된다.
            File thumbnail = new File(file.getParent(), "s_"+file.getName());
            System.out.println(thumbnail);
            result = thumbnail.delete();
            System.out.println("썸네일 " + result);
            // 성공할 경우 delete는 따로 body로 전송할 객체가 없기에 status만 500번대로 리턴한다.
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            // 실패 할 경우 false와 서버에러 리턴
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


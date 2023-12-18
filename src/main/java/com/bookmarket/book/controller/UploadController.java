package com.bookmarket.book.controller;

import com.bookmarket.book.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${com.bookmarket.upload.path}") // application.properties의 변수
    private  String uploadPath;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile uploadFiles) {
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        // 이미지 파일 여부 확인
        if(uploadFiles.getContentType().startsWith("image") == false) {
            log.warn("이미지 파일만 업로드 가능합니다.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 파일 경로에서 파일 이름 분리(IE or Edge는 경로가 들어옴)
        String originalName = uploadFiles.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") +1);
        log.info("fileName: " + fileName);

        // 날짜별 폴더 생성
        String folderPath = makeFolder(); // makeFolder()는 아래에 별도로 구현

        //UUID (같은 이름의 파일 업로드 시 덮어쓰기 방지)
        String uuid = UUID.randomUUID().toString();

        // 파일명에 uuid 적용 (ex. uuid_파일이름.확장자)
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);

        try {
            // original file save
            uploadFiles.transferTo(savePath);

            // Thumbnail 생성 (s_ + uuid_ + filename 형식)
            String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
            File thumbnailFile = new File(thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);

            resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            // 파일 이름 출력?
            log.info("fileName: " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")) {
                file = new File(file.getParent(), file.getName().substring(2));
            }

            //파일 출력
            log.info("file: " + file);

            HttpHeaders headers = new HttpHeaders();
            //MIME타입 처리
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        String srcFileName = null;

        try {
            // original 파일 삭제
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            // thumbnail삭제
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result &= thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            return  new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 폴더 생성 method
    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        // 폴더 생성
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}

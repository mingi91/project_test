package web.info.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import web.info.domain.IctAttachVO;

@Log4j
@RestController
@RequestMapping("/file")
public class FileController {
    private static final String UPLOAD_PATH = "D:/uploads"; // 변경된 저장 경로

    @PostMapping("/upload")
    public ResponseEntity<List<IctAttachVO>> upload(@RequestParam("uploadFile") MultipartFile[] multipartFiles) {
        List<IctAttachVO> list = new ArrayList<IctAttachVO>();
        File uploadPath = new File(UPLOAD_PATH, getFolder()); // 변경된 저장 경로 적용
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        for (MultipartFile multipartFile : multipartFiles) {
            IctAttachVO attachVO = new IctAttachVO();

            String filName = multipartFile.getOriginalFilename(); // 파일이름
            String uuid = UUID.randomUUID().toString();
            File saveFile = new File(uploadPath, uuid + "_" + filName);

			log.info("filName : "+filName);
			log.info("savFile : "+saveFile);
			
            attachVO.setFileName(filName);
            attachVO.setUuid(uuid);
            attachVO.setUploadPath(getFolder());

            try {
                if (checkImageType(saveFile)) {
                    attachVO.setFileType(true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uuid + "_" + filName));
                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 40, 40);
                }
                multipartFile.transferTo(saveFile); // 파일 저장
                list.add(attachVO);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();	
            }
        } // for end
        return new ResponseEntity<List<IctAttachVO>>(list, HttpStatus.OK);
    }

    // 이미지 파일 체크 여부
    private boolean checkImageType(File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        return contentType != null ? contentType.startsWith("image") : false;
    }

    // 정상
    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }

    // 정상
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        File file = new File(UPLOAD_PATH + "/" + fileName);
        ResponseEntity<byte[]> result = null;

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
                    headers,
                    HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 정상
    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam("uuid") String uuid,
                                             @RequestParam("fileName") String fileName,
                                             @RequestParam("uploadPath") String uploadPath) {
        File file = new File(UPLOAD_PATH + "/" + uploadPath, uuid + "_" + fileName);
        log.info(file);
        if (file.exists()) {
            file.delete();
        }
        if (fileName.startsWith("s_")) {
            String originalFileName = fileName.substring(2);
            File thumbnailFile = new File(UPLOAD_PATH + "/" + uploadPath, originalFileName);
            if (thumbnailFile.exists()) {
                thumbnailFile.delete();
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

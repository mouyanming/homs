package jp.co.hyron.ope.dto;

import jp.co.hyron.ope.annotation.UploadFileRequired;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDto {

    @UploadFileRequired
    private MultipartFile multipartFile;

    private int id;

}

package jp.co.hyron.ope.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.hyron.ope.annotation.UploadFileRequired;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequiredValidator implements ConstraintValidator<UploadFileRequired, MultipartFile> {

    @Override
    public void initialize(UploadFileRequired constraint) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        boolean result = multipartFile != null && StringUtils.hasLength(multipartFile.getOriginalFilename());
        if (result) {
            if (!(multipartFile.getOriginalFilename().indexOf(".jpg") > 0 || multipartFile.getOriginalFilename().indexOf(".JPG") > 0 || multipartFile.getOriginalFilename().indexOf(".PNG") > 0 || multipartFile
                    .getOriginalFilename().indexOf(".png") > 0)) {
                result = false;
            }
        }
        return result;
    }
}
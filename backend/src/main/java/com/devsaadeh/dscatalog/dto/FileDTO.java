package com.devsaadeh.dscatalog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class FileDTO implements Serializable {
    private MultipartFile file;
}

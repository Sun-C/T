package com.jt.service;

import com.jt.vo.ImageVO;
import com.jt.vo.SysResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ImageVO uploadFile(MultipartFile uploadFile);
}

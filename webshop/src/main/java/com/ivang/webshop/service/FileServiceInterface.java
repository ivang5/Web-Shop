package com.ivang.webshop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ivang.webshop.dto.DetailedDescriptionDTO;
import com.ivang.webshop.entity.DetailedDescription;

public interface FileServiceInterface {
    public DetailedDescription findOne(Long id);
    public List<DetailedDescriptionDTO> findAll();
    public DetailedDescription save(MultipartFile file) throws IOException;
    public void remove(Long id);
}

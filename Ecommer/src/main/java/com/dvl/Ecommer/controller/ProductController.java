package com.dvl.Ecommer.controller;

import com.dvl.Ecommer.dtos.ProductDTO;
import org.apache.catalina.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //http://localhost:8088/api/v1/products
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductDTO productDTO,
                                           // @RequestPart("file") MultipartFile file,
                                           BindingResult result) {

        try {

            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();//productDTO
            // xu ly files null
            files =files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {

                // kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // kích thuước  >10MB
                    // throw  new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,"File is Too larger");
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is Too larger");
                }
                // kiểm tra cho phải ảnh không
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                // luu file va cap nhat thumbnail trong dto
                String filename = storeFile(file);

            }


            return ResponseEntity.ok("Product create successfuly");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // luu ten file
    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // them UUID vao truoc ten de dam bao ten file la duy nhat
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // duong dan den thu muc muon luu file
        Path uploadDir = Paths.get("uploads");
        // kiem tra va tao thu muc neu no chua ton tai
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // duong dan day du toi file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // sao chep file vao thu muc
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(@RequestParam("Page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("getProducts here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductBuId(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with ID :" + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok("Product deleted successfully");
    }
}

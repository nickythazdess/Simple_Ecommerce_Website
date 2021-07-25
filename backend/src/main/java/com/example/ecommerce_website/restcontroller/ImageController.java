package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.ImageDTO;
import com.example.ecommerce_website.entity.Image;
import com.example.ecommerce_website.exception.image.ImageNotFoundException;
import com.example.ecommerce_website.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/admin")
    public ResponseEntity<?> getAll() {
        List<ImageDTO> dtoList = imageService.convertToDtoList(imageService.getImageList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{image_id}")
    public ResponseEntity<?> getImage(@PathVariable String image_id) {
        try {
            Optional<Image> img = imageService.getImageById(image_id);
            if (!img.isPresent()) throw new ImageNotFoundException();
            Image image = img.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                    .contentType(MediaType.valueOf(image.getContentType()))
                    .body(Base64.getDecoder().decode(image.getData().getBytes("UTF-8")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not get the image!").getBytes(StandardCharsets.UTF_8));
        }

    }

    @GetMapping("/product/{pid}")
    public ResponseEntity<?> getImageOfProduct(@PathVariable Long pid) {
        Optional<Image> img = imageService.getImageOfProduct(pid);
        if (!img.isPresent()) throw new ImageNotFoundException();
        Image image = img.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(image.getData());
    }

    @PostMapping("/admin")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName) {
        try {
            Image image = new Image(Base64.getEncoder().encodeToString(file.getBytes()), fileName, file.getContentType(), file.getSize());
            imageService.saveImage(image);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Image uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the image: %s!", file.getOriginalFilename()));
        }
    }

    @PutMapping("/admin/{image_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<String> updateImage(@PathVariable String image_id, @RequestParam("file") MultipartFile file, @RequestParam("name") String newName) {
        try {
            Image image = imageService.getImageById(image_id).get();
            image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
            image.setContentType(file.getContentType());
            image.setSize(file.getSize());
            if (newName.length() > 0) image.setName(newName);
            imageService.saveImage(image);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Image updated successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not update the image: %s!", file.getOriginalFilename()));
        }
    }

    @DeleteMapping("/admin/{image_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteImage(@PathVariable String image_id) {
        try {
            Image image = imageService.getImageById(image_id).get();
            imageService.deleteImage(image_id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not delete the file!"));
        }
    }
}

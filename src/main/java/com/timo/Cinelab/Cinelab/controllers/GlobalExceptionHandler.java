package com.timo.Cinelab.Cinelab.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage() {
        return "error_pages/error_page";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable throwable) {
        return "redirect:/error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Resource> handleMissingResource(NoResourceFoundException ex) throws NoResourceFoundException {

        String path = ex.getResourcePath();

        if (isImage(path) && isAvatar(path)) {
            Resource resource = new ClassPathResource("static/img/user_img_not_found.jpg");

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        }

        throw ex;
    }

    private boolean isImage(String path) {
        return path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg") ||
                path.endsWith(".webp");
    }

    private boolean isAvatar(String path) {
        return path.startsWith("uploads/avatars/")
                && (
                path.endsWith(".png") ||
                        path.endsWith(".jpg") ||
                        path.endsWith(".jpeg")
        );
    }
}

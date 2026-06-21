package com.timo.Cinelab.Cinelab.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage(HttpServletRequest request,
                               Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("exception", exception);

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

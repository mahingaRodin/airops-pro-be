package com.stack.location_service.controllers;


import com.stack.payloads.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping()
    public ApiResponse HomeController() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello This is the Location Service At Work!");
        return apiResponse;
    }
}

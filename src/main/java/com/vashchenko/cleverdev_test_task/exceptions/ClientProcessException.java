package com.vashchenko.cleverdev_test_task.exceptions;

import com.vashchenko.cleverdev_test_task.fetchers.dto.response.ClientInfoResponseDto;

public class ClientProcessException extends RuntimeException{
    public ClientProcessException(ClientInfoResponseDto client, Exception e) {
        super(String.format("Exception of processing client",client),e);
    }
}

package com.jhonatanborges.picpaydesafiobackend.authorization;

import lombok.Getter;

public record Authorization (
        String message
){
    public boolean isAuthorized(){
        return message.equals("Autorizado");
    }
}

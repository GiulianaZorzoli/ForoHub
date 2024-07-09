package com.api.foroHub.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException{
    public ValidacionDeIntegridad(String s){
        super(s);
    }
}

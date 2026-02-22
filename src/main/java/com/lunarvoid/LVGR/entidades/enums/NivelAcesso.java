package com.lunarvoid.LVGR.entidades.enums;

public enum NivelAcesso {
    BAIXO(1),
    MEDIO(2),
    ALTO(3);

    private int code;

    public Integer getCode(){
        return code;
    }

    private NivelAcesso(Integer code){
        this.code = code;
    }

    public static NivelAcesso valueOf(Integer code){
        for (NivelAcesso obj : NivelAcesso.values()) {
            if (obj.getCode().equals(code)){
                return obj;
            }
        }
        throw new IllegalArgumentException();
    }
}

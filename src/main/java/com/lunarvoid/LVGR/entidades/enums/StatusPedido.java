package com.lunarvoid.LVGR.entidades.enums;

public enum StatusPedido {
    SUBMETIDO(1),
    PREPARADO(2),
    ENTREGUE(3),
    PAGO(4);

    private int code;

    public Integer getCode(){
        return code;
    }

    private StatusPedido(Integer code){
        this.code = code;
    }

    public static StatusPedido valueOf(Integer code){
        for (StatusPedido obj : StatusPedido.values()) {
            if (code == obj.getCode()){
                return obj;
            }
        }
        throw new IllegalArgumentException();
    }
}

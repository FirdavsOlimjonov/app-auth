package uz.pdp.entity.enums;


//import org.springframework.security.core.GrantedAuthority;


import lombok.Getter;

@Getter
public enum PermissionEnum {

    ADD_ORDER(PageEnum.ORDER),
    EDIT_ORDER(PageEnum.ORDER),
    CANCEL_ORDER(PageEnum.ORDER),
    ADD_POSITION(PageEnum.POSITION),
    EDIT_POSITION(PageEnum.POSITION),
    GET_EMPLOYEES(PageEnum.EMPLOYEE),
    ADD_EMPLOYEES(PageEnum.EMPLOYEE),
    EDIT_EMPLOYEES(PageEnum.EMPLOYEE);

    private final PageEnum page;

    PermissionEnum(PageEnum page) {
        this.page = page;
    }
}

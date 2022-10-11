package uz.pdp.entity.enums;


//import org.springframework.security.core.GrantedAuthority;


import lombok.Getter;

@Getter
public enum PermissionEnum {


    ADD_POSITION(PageEnum.POSITION),
    EDIT_POSITION(PageEnum.POSITION),
    GET_EMPLOYEES(PageEnum.EMPLOYEE),
    ADD_EMPLOYEES(PageEnum.EMPLOYEE),
    EDIT_EMPLOYEES(PageEnum.EMPLOYEE),
    LIST_CATEGORY(PageEnum.CATEGORY),
    ADD_CATEGORY(PageEnum.CATEGORY),
    EDIT_CATEGORY(PageEnum.CATEGORY),
    DELETE_CATEGORY(PageEnum.CATEGORY),
    ADD_PRODUCT(PageEnum.PRODUCT),
    GET_PRICE_FOR_DELIVERY(PageEnum.ORDER),
    GET_ALL_PRICES_FOR_DELIVERIES(PageEnum.ORDER),
    ADD_PRICE_FOR_DELIVERY(PageEnum.ORDER),
    EDIT_PRICE_FOR_DELIVERY(PageEnum.ORDER),
    DELETE_PRICE_FOR_DELIVERY(PageEnum.ORDER),
    DELETE_ALL_PRICES_FOR_DELIVERIES(PageEnum.ORDER),
    EDIT_PRODUCT(PageEnum.PRODUCT),
    EDIT_ORDER(PageEnum.ORDER),
    ADD_ORDER(PageEnum.ORDER),
    GET_ORDER_FOR_COURIER(PageEnum.ORDER),
    EDIT_STATUS(PageEnum.ORDER),
    GET_ORDER(PageEnum.ORDER),
    SHOW_STATISTICS(PageEnum.ORDER),
    ADD_FEEDBACK(PageEnum.FEEDBACK),
    CHANGE_STATUS(PageEnum.FEEDBACK);

    private final PageEnum page;

    PermissionEnum(PageEnum page) {
        this.page = page;
    }
}

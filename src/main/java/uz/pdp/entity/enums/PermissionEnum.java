package uz.pdp.entity.enums;


//import org.springframework.security.core.GrantedAuthority;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionEnum {


    ADD_POSITION(PageEnum.POSITION, "can_add_position"),
    EDIT_POSITION(PageEnum.POSITION, "can_edit_position"),
    GET_EMPLOYEES(PageEnum.EMPLOYEE, "can_get_employees"),
    ADD_EMPLOYEES(PageEnum.EMPLOYEE, "can_add_employees"),
    EDIT_EMPLOYEES(PageEnum.EMPLOYEE, "can_edit_employees"),
    LIST_CATEGORY(PageEnum.CATEGORY, "can_list_category"),
    ADD_CATEGORY(PageEnum.CATEGORY, "can_add_category"),
    EDIT_CATEGORY(PageEnum.CATEGORY, "can_edit_category"),
    DELETE_CATEGORY(PageEnum.CATEGORY, "can_delete_category"),
    ADD_PRODUCT(PageEnum.PRODUCT, "can_add_product"),
    GET_PRICE_FOR_DELIVERY(PageEnum.ORDER, "can_get_price_for_delivery"),
    GET_ALL_PRICES_FOR_DELIVERIES(PageEnum.ORDER, "can_get_all_prices_for_deliveries"),
    ADD_PRICE_FOR_DELIVERY(PageEnum.ORDER, "can_add_price_for_delivery"),
    EDIT_PRICE_FOR_DELIVERY(PageEnum.ORDER, "can_edit_price_for_delivery"),
    DELETE_PRICE_FOR_DELIVERY(PageEnum.ORDER, "can_delete_price_for_delivery"),
    DELETE_ALL_PRICES_FOR_DELIVERIES(PageEnum.ORDER, "can_delete_all_prices_for_deliveries"),
    EDIT_PRODUCT(PageEnum.PRODUCT, "can_edit_product"),
    EDIT_ORDER(PageEnum.ORDER, "can_edit_order"),
    ADD_ORDER(PageEnum.ORDER, "can_add_order"),
    GET_ORDER_FOR_COURIER(PageEnum.ORDER, "can_get_order_for_courier"),
    EDIT_STATUS(PageEnum.ORDER, "can_edit_status"),
    GET_ORDER(PageEnum.ORDER, "can_get_order"),
    SHOW_STATISTICS(PageEnum.ORDER, "can_show_statistics"),
    ADD_FEEDBACK(PageEnum.FEEDBACK, "can_add_feedback"),
    CHANGE_STATUS(PageEnum.FEEDBACK, "can_change_status");

    private final PageEnum page;
    private final String fieldName;

}

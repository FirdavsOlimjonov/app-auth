package uz.pdp.entity;


//import org.springframework.security.core.GrantedAuthority;

public enum PermissionEnum {

    ORDER,
    EDIT_LANGUAGE,
    SOLVE_PROBLEM,
    EDIT_ROLE,
    ADD_ROLE,
    GET_USERS,
    DELETE_USER,
    GET_USER_PROBLEMS;

//    @Override
//    public String getAuthority() {
//        return this.name();
//    }
}

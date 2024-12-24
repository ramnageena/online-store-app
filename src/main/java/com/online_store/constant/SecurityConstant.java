package com.online_store.constant;

public class SecurityConstant {
    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    // Endpoints
    public static final String REGISTER_CLIENT = "/api/v1/clients/register";
    public static final String LOGIN_CLIENT = "/api/v1/login";
    public static final String UPDATE_PRODUCT = "/api/v1/updateProduct/**";
    public static final String DELETE_PRODUCT = "/api/v1/deleteProduct/**";
    public static final String GET_CLIENT_BY_ID = "/api/v1/getClient/**";
    public static final String GET_ALL_CLIENTS = "/api/v1/getALlClients";
    public static final String NEW_PRODUCT = "/api/v1/newProduct";
    public static final String GET_ALL_PRODUCTS = "/api/v1/getAllProduct";
    public static final String GET_PRODUCT_BY_ID = "/api/v1/getProductById/**";



    private SecurityConstant() {

    }
}

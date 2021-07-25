import { getCookie } from "./Cookie";

export function isAdmin() {
    const status = getCookie("status");
    const role = getCookie("role");
    if ((status === "true") && (role === "ROLE_ADMIN")) {
        return true;
    } else {
        console.log("isLogin FALSE");  
        return false;
    }
}

export function isUser() {
    const status = getCookie("status");
    const role = getCookie("role");
    if ((status === "true") && (role === "ROLE_USER")) {
        return true;
    } else {
        console.log("isLogin FALSE");  
        return false;
    }
}

export function logOut(){
    document.cookie = `token=; max-age=; path=/;`;
    document.cookie = `username=; max-age=; path=/;`;
    document.cookie = `email=; max-age=; path=/;`;
    document.cookie = `role=; max-age=; path=/;`;
    document.cookie = `status=false; max-age=; path=/;`;
}
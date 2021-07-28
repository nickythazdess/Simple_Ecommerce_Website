import { getCookie } from "./Cookie";

export function isAdmin() {
    const status = getCookie("status");
    const role = getCookie("role");
    if ((status === "true") && (role.includes("ROLE_ADMIN"))) {
        return true;
    } else {
        console.log("isAdmin FALSE");  
        return false;
    }
}

export function isUser() {
    const status = getCookie("status");
    const role = getCookie("role");
    if ((status === "true") && (role.includes("ROLE_USER"))) {
        return true;
    } else {
        console.log("isUser FALSE");  
        return false;
    }
}
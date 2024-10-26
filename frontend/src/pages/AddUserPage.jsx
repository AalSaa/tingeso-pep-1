import { useState } from "react";
import { useLocation } from "wouter";
import { postUser } from "../features/users/services/UserService";
import { SignupForm } from "../features/users/components/UserForm";

export function AddUserPage() {
    const [, setLocation] = useLocation();

    const [user, setUser] = useState({
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "In validation"
    })

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            await postUser(user);
            setLocation("/users");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <SignupForm user={user} setUser={setUser} submitForm={submitForm}/>
    )
}
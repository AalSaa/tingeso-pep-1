import { useState, useEffect } from "react"
import { useRoute, useLocation } from "wouter";
import { getUser, putUser } from "../features/users/services/UserService";
import { SignupForm } from "../features/users/components/UserForm";

export function EditUserPage() {
    const [user, setUser] = useState({
        first_name: "",
        last_name: "",
        rut: "",
        birth_date: "",
    })

    const [match, params] = useRoute("/edituser/:id");
    const [, setLocation] = useLocation();

    const fetchUser = async () => {
        try {
            const fetchedUser = await getUser(params.id);
            setUser(fetchedUser);
            console.log(fetchedUser);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchUser();
    }, [match, params.id])

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            await putUser(params.id, user);
            setLocation("/users");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <div className="flex justify-center items-center h-full">
            <SignupForm user={user} setUser={setUser} submitForm={submitForm} isEdit={true}/>
        </div>
    )
}
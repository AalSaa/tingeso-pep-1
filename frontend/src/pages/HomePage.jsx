import { useState, useEffect } from "react";
import { useLocation } from "wouter";
import { LoanTable } from "../features/loans/components/LoansTable";
import { getUserByRut } from '../features/users/services/UserService';
import { getLoansByUserIdAndStatusNot } from "../features/loans/services/LoanService";
import { UserSearchForm } from "../features/users/components/UserSearchForm";

export function HomePage() {
    const [user, setUser] = useState({
        id: "",
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "",
    })
    const [loans, setLoans] = useState([]);

    const [, setLocation] = useLocation();

    const fetchLoans = async (userId) => {    
        try {
            const fetchedLoans = await getLoansByUserIdAndStatusNot(userId, 'Simulación');
            setLoans(fetchedLoans);
        } catch (error) {
            console.error(error);
        }
    }

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            const fetchedUser = await getUserByRut(user.rut);
            if (fetchedUser.id !== "") {
                setUser(fetchedUser);
                await fetchLoans(fetchedUser.id);
            }
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <div className="space-y-4">
            <h1 className="text-4xl px-4 pt-4">Préstamos</h1>
            <UserSearchForm user={user} setUser={setUser} submitForm={submitForm} />
            <LoanTable loans={loans} fetchLoans={fetchLoans} isSimulationPage={false} />
        </div>
    )
}
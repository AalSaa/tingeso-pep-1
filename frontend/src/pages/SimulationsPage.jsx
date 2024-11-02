import { useState, useEffect } from "react";
import { useLocation } from "wouter";

import { LoanTable } from "../features/loans/components/LoansTable";
import { getLoansByStatus } from "../features/loans/services/LoanService";

export function SimulationsPage() {
    const [loans, setLoans] = useState([]);

    const [, setLocation] = useLocation();

    const fetchLoans = async () => {    
        try {
            const fetchedLoans = await getLoansByStatus('Simulación');
            setLoans(fetchedLoans);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchLoans();
    }
    , []);

    const handleClick = () => {
        setLocation('/simulateloan');
    }

    return (
        <div>
            <div className="flex items-center space-x-4 p-4">
                <h1 className="text-4xl">Préstamos</h1>
                <button onClick={handleClick}
                className="bg-lime-500 text-white flex rounded-lg gap-4 p-2">
                    <p>Simular préstamo</p>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                    <path fillRule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25ZM12.75 9a.75.75 0 0 0-1.5 0v2.25H9a.75.75 0 0 0 0 1.5h2.25V15a.75.75 0 0 0 1.5 0v-2.25H15a.75.75 0 0 0 0-1.5h-2.25V9Z" clipRule="evenodd" />
                    </svg>
                </button>
            </div>
            <LoanTable loans={loans} fetchLoans={fetchLoans} isSimulationPage={true} />
        </div>
    )
}
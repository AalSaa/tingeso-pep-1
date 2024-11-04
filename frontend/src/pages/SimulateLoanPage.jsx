import { useState, useEffect } from 'react';
import { useLocation } from 'wouter';

import { UserSearchForm } from '../features/users/components/UserSearchForm';
import { LoanTypeSelectorForm } from '../features/loans/components/LoanTypeSelectorForm';
import { LoanForm } from '../features/loans/components/LoanForm';
import { getUserByRut } from '../features/users/services/UserService';
import { postLoan } from '../features/loans/services/LoanService';

export function SimulateLoanPage() {
    const [user, setUser] = useState({
        id: "",
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "",
    })

    const [loanType, setLoanType] = useState({
        name: "",
        max_term: "",
        min_annual_interest_rate: "",
        max_annual_interest_rate: "",
        max_percentage_amount: "",
        type_of_documents_required: [],
    })

    const [loan, setLoan] = useState({
        id: "",
        property_value: "",
        amount: "",
        term_in_years: "",
        annual_interest_rate_percentage: "",
        monthly_life_insurance_percentage: "0",
        monthly_fire_insurance_amount_percentage: "0",
        administration_fee_percentage: "0",
        status: "Simulación",
        user_id: "",
        loan_type_id: "",
    })

    const [, setLocation] = useLocation();

    const submitUserForm = async (event) => {
        event.preventDefault();
        try {
            const fetchedUser = await getUserByRut(user.rut);
            if (fetchedUser) {
                setUser(fetchedUser);
                console.log(fetchedUser);
            }
        } catch (error) {
            console.error(error);
        }
    }

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            const updatedLoan = { ...loan, user_id: user.id, loan_type_id: loanType.id };
            const postedLoan = await postLoan(updatedLoan);
            if (postedLoan) {
                setLoan(postedLoan);
                setLocation('/simulations');
            }
        } catch (error) {
            setLoan({});
            console.error(error);
        }
    }

    return (
        <div className='flex justify-center items-center space-x-4 h-full'>
            <div className='bg-slate-50 rounded-2xl p-4'>
                <div>
                    <button
                    className="absolute">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                        <path fillRule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clipRule="evenodd" />
                        </svg>
                    </button>
                    <h1 className="text-2xl text-center">Simulación de prestamo</h1>
                </div>
                <div className="flex">
                    <div className="flex flex-col h-full">
                        <UserSearchForm user={user} setUser={setUser} submitForm={submitUserForm} />
                        <LoanTypeSelectorForm loanType={loanType} setLoanType={setLoanType} />
                        <div className='px-6'>
                            {(user.id || loanType.id) ? (
                                <h2>Datos:</h2>
                            ) : null}
                            {(user.id != "") ? (
                                <p>Cliente: {user.first_name} {user.last_name}</p>
                            ) : null}
                            {(loanType.id) ? (
                                <ul>
                                    <li>Monto maximo: {loanType.max_percentage_amount}%</li>
                                    <li>Años maximo: {loanType.max_term} años</li>
                                    <li>
                                        Tasa de interes: Entre {loanType.min_annual_interest_rate}% 
                                        y {loanType.max_annual_interest_rate}%
                                    </li>
                                </ul>
                            ) : null}
                        </div>
                    </div>
                    <LoanForm 
                        user={user} 
                        loanType={loanType}
                        loan={loan} 
                        setLoan={setLoan} 
                        submitForm={submitForm} 
                        isSimulation={true}
                    />
                </div>
            </div>
        </div>
    )
}
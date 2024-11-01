import { useState, useEffect } from "react"
import { useLocation, useRoute } from 'wouter';

import { getLoanById, putLoan } from "../features/loans/services/LoanService";
import { LoanConditionForm } from "../features/loans/components/LoanConditionForm";

export function AddLoanConditionsPage() {
    const [match, params] = useRoute("/loan/:id/conditions");
    const [, setLocation] = useLocation();

    const [loan, setLoan] = useState({
        id: "",
        property_value: "",
        amount: "",
        term_in_years: "",
        annual_interest_rate_percentage: "",
        monthly_life_insurance_percentage: "",
        monthly_fire_insurance_amount_percentage: "",
        administration_fee_percentage: "",
        status: "",
        user_id: "",
        loan_type_id: "",
    })

    const fetchedLoan = async () => {
        try {
            if (params.id) {
                const fetchedLoan = await getLoanById(params.id);
                const modifiedLoan = {
                    ...fetchedLoan,
                    monthly_life_insurance_percentage: '',
                    monthly_fire_insurance_amount_percentage: '',
                    administration_fee_percentage: '',
                };
                setLoan(modifiedLoan);
                console.log(modifiedLoan);
            }
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchedLoan();
    }, [match, params.id])

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            loan.status = "En Aprobación Final";
            await putLoan(loan.id, loan);
            setLocation(`/loans`);
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <div className="flex justify-center items-center h-full">
            <LoanConditionForm loan={loan} setLoan={setLoan} submitForm={submitForm}/>
        </div>
    )
}
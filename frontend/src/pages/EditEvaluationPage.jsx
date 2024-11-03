import { useState, useEffect } from "react";
import { useLocation, useRoute } from 'wouter';

import { EvaluationForm } from "../features/evaluations/components/EvaluationForm";
import { getEvaluationInfoByLoanId, putEvaluationInfo } from "../features/evaluations/services/EvaluationInfoService";


export function EditEvaluationPage() {
    const [match, params] = useRoute("/loan/:id/evaluation/edit");
    const [, setLocation] = useLocation();

    const [loan, setLoan] = useState({});
    const [evaluationInfo, setEvaluationInfo] = useState({
        id: "",
        monthly_income: "",
        have_positive_credit_history: false,
        employment_type: "",
        employment_seniority: "",
        monthly_debt: "",
        savings_account_balance: "",
        has_consistent_savings_history: false,
        has_periodic_deposits: false,
        sum_of_deposits: "",
        old_savings_account: "",
        maximum_withdrawal_in_six_months: "",
        loan_id: "",
    })

    const fetchedEvaluationInfo = async () => {
        try {
            if (params.id) {
                const fetchedEvaluationInfo = await getEvaluationInfoByLoanId(params.id);
                if (fetchedEvaluationInfo) {
                    setEvaluationInfo(fetchedEvaluationInfo);
                }
            }
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchedEvaluationInfo();
    }, [match, params.id])

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            const savedEvaluationInfo = await putEvaluationInfo(evaluationInfo.id, evaluationInfo);
            if (savedEvaluationInfo) {
                setLocation(`/loans`);
            }
        } catch (error) {
            console.error(error);
        }
    }



    return (
        <div className="flex justify-center items-center h-full">
            <EvaluationForm evaluationInfo={evaluationInfo} setEvaluationInfo={setEvaluationInfo} submitForm={submitForm} />
        </div>
    )
}
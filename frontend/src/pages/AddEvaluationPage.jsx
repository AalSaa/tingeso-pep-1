import { useState, useEffect } from "react"
import { useLocation, useRoute } from 'wouter';
import { EvaluationForm } from "../features/evaluations/components/EvaluationForm";
import { getLoanById, putLoan } from "../features/loans/services/LoanService";
import { postEvaluationInfo } from "../features/evaluations/services/EvaluationInfoService";

export function AddEvaluationPage() {
    const [match, params] = useRoute("/loan/:id/evaluation");
    const [, setLocation] = useLocation();

    const [loan, setLoan] = useState({});
    const [evaluationInfo, setEvaluationInfo] = useState({
        monthly_income: "",
        have_positive_credit_history: false,
        employment_type: "",
        employment_seniority: "",
        monthly_debt: "",
        savings_account_balance: "",
        has_periodic_deposits: false,
        sum_of_deposits: "",
        old_savings_account: "",
        maximum_withdrawal_in_six_months: "",
        loan_id: "",
    })

    const fetchedLoan = async () => {
        try {
            if (params.id) {
                const fetchedLoan = await getLoanById(params.id);
                setLoan(fetchedLoan);
                console.log(fetchedLoan);
            }
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchedLoan();
    }, [match, params.id])

    const changeStatusToInEvaluation = async (loan) => {
        try {
            loan.status = 'En Evaluación';
            await putLoan(loan.id, loan);
        } catch (error) {
            console.error(error);
        }
    }

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            const updatedEvaluationInfo = { ...evaluationInfo, loan_id: loan.id };
            console.log(updatedEvaluationInfo);
            const savedEvaluationInfo = await postEvaluationInfo(updatedEvaluationInfo);
            if (savedEvaluationInfo) {
                await changeStatusToInEvaluation(loan);
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
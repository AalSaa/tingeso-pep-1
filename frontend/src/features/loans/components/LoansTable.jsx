import { useState, useEffect } from "react";
import { useLocation } from "wouter";

import { getLoans, putLoan } from "../services/LoanService";
import { postEvaluationResult } from "../../evaluations/services/EvaluationResultService";

export function LoanTable() {
    const [loans, setLoans] = useState([]);

    const [, setLocation] = useLocation();

    const fetchLoans = async () => {    
        try {
            const fetchedLoans = await getLoans();
            setLoans(fetchedLoans);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchLoans();
    }
    , []);

    const handleRedirectToDocumentsClick = (loanId) => {
        setLocation(`/loan/${loanId}/documents`);
    }

    const handleRedirectToAddEvaluationClick = (loanId) => {
        setLocation(`/loan/${loanId}/evaluation`);
    }

    const handleRedirectToEditEvaluationEditClick = (loanId) => {
        setLocation(`/loan/${loanId}/evaluation/edit`);
    }

    const handleRedirectToAddLoanConditionsClick = (loanId) => {
        setLocation(`/loan/${loanId}/conditions`);
    }

    const handleGenerateEvaluationClick = async (loan) => {
        try {
            const response = await postEvaluationResult(loan.id);
            if (response) {
                if (response.evaluation_result == 'Rejected') {
                    loan.status = 'Rechazada';
                } else if (response.evaluation_result == 'Requires a additional review') {
                    loan.status = 'Requiere revisión adicional';
                } else {
                    loan.status = 'Pre-Aprobada';
                }
                await putLoan(loan.id , loan);
                fetchLoans();
            }
        } catch (error) {
            console.error(error);
        }
    }

    const handleChangeStatusToInitialRevisionClick = async (loan) => {
        try {
            loan.status = 'Revisión Inicial';
            await putLoan(loan.id, loan);
            fetchLoans();
        } catch (error) {
            console.error(error);
        }
    }

    const handleChangeStatusToPendingDocumentationClick = async (loan) => {
        try {
            loan.status = 'Pendiente de Documentación';
            await putLoan(loan.id, loan);
            fetchLoans();
        } catch (error) {
            console.error(error);
        }
    }

    const handleChangeStatusToApprovedClick = async (loan) => {
        try {
            loan.status = 'Aprobada';
            await putLoan(loan.id, loan);
            fetchLoans();
        } catch (error) {
            console.error(error);
        }
    }

    const handleChangeStatusToCanceledClick = async (loan) => {
        try {
            loan.status = 'Cancelada por el Cliente';
            await putLoan(loan.id, loan);
            fetchLoans();
        } catch (error) {
            console.error(error);
        }
    }


    const LoanRow = ({ loan }) => {
        return (
            <tr className="odd:bg-white even:bg-slate-50">
                <td className="text-start p-4">{loan.user.rut}</td>
                <td className="text-start p-4">{loan.loanType.name}</td>
                <td className="text-start p-4">{loan.amount}</td>
                <td className="text-start p-4">{loan.status}</td>
                <td className="flex items-center p-2 space-x-2">
                    <button onClick={(e) => handleRedirectToDocumentsClick(loan.id)}
                    className="bg-cyan-500 text-white flex justify-between rounded-lg p-2">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                        <path d="M5.625 1.5c-1.036 0-1.875.84-1.875 1.875v17.25c0 1.035.84 1.875 1.875 1.875h12.75c1.035 0 1.875-.84 1.875-1.875V12.75A3.75 3.75 0 0 0 16.5 9h-1.875a1.875 1.875 0 0 1-1.875-1.875V5.25A3.75 3.75 0 0 0 9 1.5H5.625Z" />
                        <path d="M12.971 1.816A5.23 5.23 0 0 1 14.25 5.25v1.875c0 .207.168.375.375.375H16.5a5.23 5.23 0 0 1 3.434 1.279 9.768 9.768 0 0 0-6.963-6.963Z" />
                        </svg>
                    </button>
                    {(loan.status == "Pendiente de Documentación") ? (
                        <button onClick={(e) => handleChangeStatusToInitialRevisionClick(loan)}
                        className="bg-lime-500 text-white flex justify-between flex-1 rounded-lg p-2">
                            <p>Confirmar documentos</p>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                            <path fillRule="evenodd" d="M9 1.5H5.625c-1.036 0-1.875.84-1.875 1.875v17.25c0 1.035.84 1.875 1.875 1.875h12.75c1.035 0 1.875-.84 1.875-1.875V12.75A3.75 3.75 0 0 0 16.5 9h-1.875a1.875 1.875 0 0 1-1.875-1.875V5.25A3.75 3.75 0 0 0 9 1.5Zm6.61 10.936a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 14.47a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z" clipRule="evenodd" />
                            <path d="M12.971 1.816A5.23 5.23 0 0 1 14.25 5.25v1.875c0 .207.168.375.375.375H16.5a5.23 5.23 0 0 1 3.434 1.279 9.768 9.768 0 0 0-6.963-6.963Z" />
                            </svg>
                        </button>
                    ) : null}
                    {(loan.status == "Revisión Inicial") ? (
                        <button onClick={(e) => handleRedirectToAddEvaluationClick(loan.id)}
                        className="bg-lime-500 text-white flex justify-center rounded-lg p-2">
                            <p>Evaluar</p>
                        </button>
                    ) : null}
                    {(loan.status == "Revisión Inicial") ? (
                        <button onClick={(e) => handleChangeStatusToPendingDocumentationClick(loan)}
                        className="bg-red-500 text-white flex justify-center flex-1 rounded-lg p-2">
                            <p>Faltan documentos</p>
                        </button>
                    ) : null}
                    {(loan.status == "En Evaluación") ? (
                        <button onClick={(e) => handleRedirectToEditEvaluationEditClick(loan.id)}
                        className="bg-yellow-500 text-white flex justify-center rounded-lg p-2">
                            <p>Editar info</p>
                        </button>
                    ) : null}
                    {(loan.status == "En Evaluación") ? (
                        <button onClick={(e) => handleGenerateEvaluationClick(loan)}
                        className="bg-lime-500 text-white flex justify-center flex-1 rounded-lg p-2">
                            <p>Generar evaluación</p>
                        </button>
                    ) : null}
                    {(loan.status == "Pre-Aprobada") ? (
                        <button onClick={(e) => handleRedirectToAddLoanConditionsClick(loan.id)}
                        className="bg-cyan-500 text-white flex justify-center flex-1 rounded-lg p-2">
                            <p>Agregar condiciones</p>
                        </button>
                    ) : null}
                    {(loan.status == "En Aprobación Final") ? (
                        <button onClick={(e) => handleChangeStatusToApprovedClick(loan)}
                        className="bg-lime-500 text-white flex justify-center rounded-lg p-2">
                            <p>Aceptar</p>
                        </button>
                    ) : null}
                    {(loan.status == "En Aprobación Final") ? (
                        <button onClick={(e) => handleChangeStatusToCanceledClick(loan)}
                        className="bg-red-500 text-white flex justify-center rounded-lg p-2">
                            <p>Cancelar</p>
                        </button>
                    ) : null}
                </td>
            </tr>
        )
    }

    return (
        <div className="overflow-y-auto">
            <table className="w-full">
                <thead>
                    <tr className="bg-slate-50">
                        <th className="text-start p-4">Rut del solicitante</th>
                        <th className="text-start p-4">Tipo</th>
                        <th className="text-start p-4">Monto</th>
                        <th className="text-start p-4">Estado</th>
                        <th className="text-start w-96 p-4">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {loans?.map((loan) => (
                        <LoanRow key={loan.id} loan={loan} />
                    ))}
                </tbody>
            </table>
        </div>
    )
}
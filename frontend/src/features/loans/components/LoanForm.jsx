import { useState, useEffect } from "react";

import { getDocumentsByLoanId } from "../../documents/services/DocumentService";

export function LoanForm({ user, loanType, loan, setLoan, submitForm }) {
    const [documents, setDocuments] = useState([]);

    const fetchedDocuments = async (loanId) => {
        try {
            if (loanId) {
                const fetchedDocuments = await getDocumentsByLoanId(loanId);
                setDocuments(fetchedDocuments);
                console.log(fetchedDocuments);
            }
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchedDocuments(loan.id);
    }, [loan.id])

    return (
        <div>
            
            <form action="" className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
                <div className="space-y-4">
                    <div>
                        <label htmlFor="" className="block ml-2">Valor de la vivienda</label>
                        <input
                        id="property_value"
                        name="property_value"
                        value={loan.property_value}
                        onChange={(e) => setLoan({ ...loan, property_value: e.target.value })}
                        placeholder="Ingrese el valor de la vivienda"
                        type="number"
                        className="border w-full rounded-lg p-2"
                        />
                    </div>
                    <div>
                        <label htmlFor="" className="block ml-2">Monto a solicitar</label>
                        <input
                        id="amount"
                        name="amount"
                        value={loan.amount}
                        onChange={(e) => setLoan({ ...loan, amount: e.target.value })}
                        placeholder="Ingrese el monto a solicitar"
                        type="number"
                        className="border w-full rounded-lg p-2"
                        />
                    </div>
                    <div>
                        <label htmlFor="" className="block ml-2">Plazo en años</label>
                        <input
                        id="term_in_years"
                        name="term_in_years"
                        value={loan.term_in_years}
                        onChange={(e) => setLoan({ ...loan, term_in_years: e.target.value })}
                        placeholder="Ingrese el plazo en años"
                        type="number"
                        className="border w-full rounded-lg p-2"
                        />
                    </div>
                    <div>
                        <label htmlFor="" className="block ml-2">Tasa de interes</label>
                        <input
                        id="annual_interest_rate"
                        name="annual_interest_rate"
                        value={loan.annual_interest_rate}
                        onChange={(e) => setLoan({ ...loan, annual_interest_rate: e.target.value })}
                        placeholder="Ingrese la tasa de interes"
                        type="number"
                        className="border w-full rounded-lg p-2"
                        />
                    </div>
                </div>
                <button onClick={submitForm} className="bg-lime-500 text-white w-full rounded-lg p-2">
                    Enviar solicitud
                </button>
            </form>
        </div>
    )
}
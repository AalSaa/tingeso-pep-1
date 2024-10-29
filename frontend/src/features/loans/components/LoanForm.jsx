import { useState, useEffect } from "react";
import { getLoanTypes } from "../services/LoanTypeService";
import { postLoan } from "../services/LoanService";
import { getDocumentsByLoanId } from "../../documents/services/DocumentService";

export function LoanForm({ user, loan, setLoan }) {
    const [loanTypes, setLoanTypes] = useState([]);

    const [loanType, setLoanType] = useState({
        name: "",
        max_term: "",
        min_annual_interest_rate: "",
        max_annual_interest_rate: "",
        max_percentage_amount: "",
        type_of_documents_required: [],
    })

    const [documents, setDocuments] = useState([]);

    const fetchLoanTypes = async () => {
        try {
            const fetchedLoanTypes = await getLoanTypes();
            setLoanTypes(fetchedLoanTypes);
            console.log(fetchedLoanTypes);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchLoanTypes();
    }, [])

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

    const handleLoanTypeChange = (value) => {
        setLoanType(loanTypes[value - 1]);
    };

    const submitLoanForm = async (event) => {
        event.preventDefault();
        try {
            const updatedLoan = { ...loan, user_id: user.id, loan_type_id: loanType.id };
            const postedLoan = await postLoan(updatedLoan);
            if (postedLoan) {
                setLoan(postedLoan);
                console.log(postedLoan);
            }
        } catch (error) {
            setLoan({});
            console.error(error);
        }
    }

    return (
        <div>
            <form action="">
                <div>
                    <label htmlFor="">Tipo de prestamo</label>
                    <select 
                    id="loanType" 
                    name="loanType" 
                    value={loanType.id} 
                    onChange={(e) => handleLoanTypeChange(e.target.value)}
                    defaultValue=""
                    >
                        <option value="" disabled>Seleccione un tipo de prestamo</option>
                        {loanTypes.map((type) => (
                            <option key={type.id} value={type.id}>
                                {type.name}
                            </option>
                        ))}
                    </select>
                </div>
            </form>
            <form action="">
                <div>
                    <label htmlFor="">Valor de la vivienda</label>
                    <input
                    id="property_value"
                    name="property_value"
                    value={loan.property_value}
                    onChange={(e) => setLoan({ ...loan, property_value: e.target.value })}
                    placeholder="Ingrese el valor de la vivienda"
                    type="number"
                    />
                </div>
                <div>
                    <label htmlFor="">Monto a solicitar</label>
                    <input
                    id="amount"
                    name="amount"
                    value={loan.amount}
                    onChange={(e) => setLoan({ ...loan, amount: e.target.value })}
                    placeholder="Ingrese el monto a solicitar"
                    type="number"
                    />
                </div>
                <div>
                    <label htmlFor="">Plazo en años</label>
                    <input
                    id="term_in_years"
                    name="term_in_years"
                    value={loan.term_in_years}
                    onChange={(e) => setLoan({ ...loan, term_in_years: e.target.value })}
                    placeholder="Ingrese el plazo en años"
                    type="number"
                    />
                </div>
                <div>
                    <label htmlFor="">Tasa de interes</label>
                    <input
                    id="annual_interest_rate"
                    name="annual_interest_rate"
                    value={loan.annual_interest_rate}
                    onChange={(e) => setLoan({ ...loan, annual_interest_rate: e.target.value })}
                    placeholder="Ingrese la tasa de interes"
                    type="number"
                    />
                </div>
                <button onClick={submitLoanForm}>Enviar solicitud</button>
            </form>
            <div>{documents?.map((document) => (
                <div key={document.id}>
                    <p>{document.file_name}</p>
                    <button onClick={() => console.log('Button clicked!')}>Nuevo Botón</button>
                </div>
                ))}
            </div>
        </div>
    )
}
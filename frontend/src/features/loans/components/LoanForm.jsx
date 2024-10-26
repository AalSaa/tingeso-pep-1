import { useState, useEffect } from "react";
import { getUserByRut } from "../../users/services/UserService";
import { getLoanTypes } from "../services/LoanTypeService";
import { postLoan } from "../services/LoanService";

export function LoanForm() {
    const [user, setUser] = useState({
        id: "",
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "",
    })

    const [loanTypes, setLoanTypes] = useState([]);

    const [loanType, setLoanType] = useState({
        name: "",
        max_term: "",
        min_annual_interest_rate: "",
        max_annual_interest_rate: "",
        max_percentage_amount: "",
        type_of_documents_required: [],
    })

    const [loan, setLoan] = useState({
        property_value: "",
        amount: "",
        term_in_years: "",
        annual_interest_rate: "",
        monthly_life_insurance: "0",
        monthly_fire_insurance: "0",
        administration_fee: "0",
        status: "In validation",
        user_id: "",
        loan_type_id: "",
    })

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

    const handleLoanTypeChange = (value) => {
        setLoanType(loanTypes[value - 1]);
    };

    const submitUserForm = async (event) => {
        event.preventDefault();
        try {
            const fetchedUser = await getUserByRut(user.rut);
            setUser(fetchedUser);
            console.log(fetchedUser);
        } catch (error) {
            console.error(error);
        }
    }

    const submitLoanForm = async (event) => {
        event.preventDefault();
        loan.user_id = user.id;
        loan.loan_type_id = loanType.id;
        await postLoan(loan);
        console.log(loan);
    }

    return (
        <div>
            <form action="">
                <div>
                    <label htmlFor="">Rut del solicitante</label>
                    <input 
                    id="rut"
                    name="rut"
                    value={user.rut}
                    onChange={(e) => setUser({ ...user, rut: e.target.value })}
                    placeholder="Ingrese el rut del solicitante"
                    type="text" 
                    />
                </div>
                <button onClick={submitUserForm}>Buscar usuario</button>
            </form>
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
        </div>
    )
}
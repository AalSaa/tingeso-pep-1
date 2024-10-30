import { useState, useEffect } from "react";
import { getLoanTypes } from "../services/LoanTypeService";

export function LoanTypeSelectorForm({ loanType, setLoanType }) {
    const [loanTypes, setLoanTypes] = useState([]);

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

    return (
        <form action="" className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
            <div>
                <label htmlFor="" className="block ml-2">Tipo de prestamo</label>
                <select 
                id="loanType" 
                name="loanType" 
                value={loanType.id} 
                onChange={(e) => setLoanType(loanTypes[e.target.value - 1])}
                defaultValue=""
                className="bg-white border w-full rounded-lg p-2"
                >
                    <option value="" disabled>Seleccione un tipo de prestamo</option>
                    {loanTypes?.map((type) => (
                        <option key={type.id} value={type.id}>
                            {type.name}
                        </option>
                    ))}
                </select>
            </div>
        </form>
    )
}
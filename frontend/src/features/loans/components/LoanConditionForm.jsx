export function LoanConditionForm({ loan, setLoan, submitForm }) {
    return (
        <form onSubmit={submitForm} className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
            <div>
                <button
                className="absolute">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                    <path fillRule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clipRule="evenodd" />
                    </svg>
                </button>
                <h2 className="text-2xl text-center">
                    Información de evaluación
                </h2>
            </div>
            <div className="space-y-4">
                <div>
                    <label htmlFor="" className="block ml-2">Porcentaje seguro de vida</label>
                    <input
                    id="monthly_life_insurance_percentage"
                    name="monthly_life_insurance_percentage"
                    value={loan.monthly_life_insurance_percentage}
                    onChange={(e) => setLoan({ ...loan, monthly_life_insurance_percentage: e.target.value })}
                    type="number" 
                    placeholder="Ingrese el % para el seguro de vida"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Porcentaje seguro de incendio</label>
                    <input
                    id="monthly_fire_insurance_amount_percentage"
                    name="monthly_fire_insurance_amount_percentage"
                    value={loan.monthly_fire_insurance_amount_percentage}
                    onChange={(e) => setLoan({ ...loan, monthly_fire_insurance_amount_percentage: e.target.value })}
                    type="number" 
                    placeholder="Ingrese el % para el seguro de incendio"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Porcentaje Gastos de administración</label>
                    <input
                    id="administration_fee_percentage"
                    name="administration_fee_percentage"
                    value={loan.administration_fee_percentage}
                    onChange={(e) => setLoan({ ...loan, administration_fee_percentage: e.target.value })}
                    type="number" 
                    placeholder="Ingrese el % para los gastos de admin."
                    className="border w-full rounded-lg p-2"
                    />
                </div>
            </div>
            <button type="submit"
            className="bg-lime-500 text-white w-full rounded-lg p-2">
                Enviar Condiciones
            </button>
        </form>
    )
}
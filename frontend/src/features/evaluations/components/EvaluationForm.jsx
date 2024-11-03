export function EvaluationForm({ evaluationInfo, setEvaluationInfo, submitForm }) {
    return (
        <form action="" className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
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
                    <label htmlFor="" className="block ml-2">Ingreso mensual</label>
                    <input 
                    id="monthly_income"
                    name="monthly_income"
                    value={evaluationInfo.monthly_income}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, monthly_income: e.target.value})}
                    type="number" 
                    placeholder="Ingrese el ingreso mensual"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div className="flex space-x-2 ml-2">
                    <input 
                    id="have_positive_credit_history"
                    name="have_positive_credit_history"
                    checked={evaluationInfo.have_positive_credit_history}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, have_positive_credit_history: e.target.checked})}
                    type="checkbox"
                    />
                    <p>¿Tiene un historial crediticio positivo?</p>
                </div>
                <div>
                    <label className="block ml-2">Tipo de empleo</label>
                    <div className="flex justify-between mx-2">
                        <div className="space-x-2">
                            <input 
                                type="radio" 
                                id="employment_type_dependent" 
                                name="employment_type" 
                                value="Dependent" 
                                checked={evaluationInfo.employment_type === 'Dependent'}
                                onChange={(e) => setEvaluationInfo({...evaluationInfo, employment_type: e.target.value})}
                            />
                            <label htmlFor="employment_type_dependent">Dependiente</label>
                        </div>
                        <div className="space-x-2">
                            <input 
                                type="radio" 
                                id="employment_type_independent" 
                                name="employment_type" 
                                value="Independent" 
                                checked={evaluationInfo.employment_type === 'Independent'}
                                onChange={(e) => setEvaluationInfo({...evaluationInfo, employment_type: e.target.value})}
                            />
                            <label htmlFor="employment_type_independent">Independiente</label>
                        </div>
                    </div>
                </div>
                <div>
                    <label htmlFor="employment_seniority" className="block ml-2">Antigüedad laboral</label>
                    <input 
                    id="employment_seniority"
                    name="employment_seniority"
                    value={evaluationInfo.employment_seniority}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, employment_seniority: e.target.value})}
                    type="number" 
                    placeholder="Ingrese la antigüedad laboral"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Deudas mensuales</label>
                    <input
                    id="monthly_debt"
                    name="monthly_debt"
                    value={evaluationInfo.monthly_debt}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, monthly_debt: e.target.value})}
                    type="number"
                    placeholder="Ingrese suma de las deudas mensuales"
                    className="border w-full rounded-lg p-2"
                    />
                </div>

                <div>
                    <label htmlFor="" className="block ml-2">Monto actual en la cuenta de ahorro</label>
                    <input
                    id="savings_account_balance"
                    name="savings_account_balance"
                    value={evaluationInfo.savings_account_balance}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, savings_account_balance: e.target.value})}
                    type="number"
                    placeholder="Ingrese el monto actual en la cuenta de ahorro"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div className="flex space-x-2 ml-2">
                    <input 
                    id="has_consistent_savings_history"
                    name="has_consistent_savings_history"
                    checked={evaluationInfo.has_consistent_savings_history}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, has_consistent_savings_history: e.target.checked})}
                    type="checkbox" />
                    <label htmlFor="" className="block ml-2">¿Tiene historial de ahorros consistente?</label>
                </div>
                <div className="flex space-x-2 ml-2">
                    <input 
                    id="has_periodic_deposits"
                    name="has_periodic_deposits"
                    checked={evaluationInfo.has_periodic_deposits}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, has_periodic_deposits: e.target.checked})}
                    type="checkbox" />
                    <p>¿Tiene depósitos periódicos?</p>
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Suma de los depositos</label>
                    <input
                    id="sum_of_deposits"
                    name="sum_of_deposits"
                    value={evaluationInfo.sum_of_deposits}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, sum_of_deposits: e.target.value})}
                    type="number"
                    placeholder="Ingrese la suma de los depósitos"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Antiguedad de la cuenta de ahorro</label>
                    <input
                    id="old_savings_account"
                    name="old_savings_account"
                    value={evaluationInfo.old_savings_account}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, old_savings_account: e.target.value})}
                    type="number"
                    placeholder="Ingrese la antiguedad de la cuenta de ahorro"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="" className="block ml-2">Maximo retiro en 6 meses</label>
                    <input
                    id="maximum_withdrawal_in_six_months"
                    name="maximum_withdrawal_in_six_months"
                    value={evaluationInfo.maximum_withdrawal_in_six_months}
                    onChange={(e) => setEvaluationInfo({...evaluationInfo, maximum_withdrawal_in_six_months: e.target.value})}
                    type="number"
                    placeholder="Ingrese el máximo retiro en 6 meses"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
            </div>
            <button onClick={submitForm} className="bg-lime-500 text-white w-full rounded-lg p-2">
                Realizar evaluación
            </button>
        </form>
    )
}
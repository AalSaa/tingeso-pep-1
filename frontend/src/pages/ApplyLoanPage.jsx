import { useState, useEffect } from 'react';

import { UserSearchForm } from '../features/users/components/UserSearchForm';
import { LoanForm } from '../features/loans/components/LoanForm';
import { DocumentFileUploadForm } from '../features/documents/components/DocumentFileUploadForm';

export function ApplyLoanPage() {
    const [user, setUser] = useState({
        id: "",
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "",
    })

    const [loan, setLoan] = useState({
        id: "",
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

    return (
        <div>
            <UserSearchForm user={user} setUser={setUser} />
            <LoanForm user={user} loan={loan} setLoan={setLoan} />
            <DocumentFileUploadForm loanId={loan.id} />
        </div>
    )
}
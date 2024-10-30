import { useState } from "react"
import { Route, Switch } from 'wouter';
import { Header } from "./layouts/Header"
import { SideBar } from "./layouts/SideBar"
import { AddUserPage } from "./pages/AddUserPage"
import { UsersPage } from "./pages/UsersPage"
import { EditUserPage } from "./pages/EditUserPage";
import { LoansPage } from "./pages/LoansPage";
import { ApplyLoanPage } from "./pages/ApplyLoanPage";


function App() {
  const [showSideBar, setShowSideBar] = useState(false)

  function handleShowSideBar() {
    setShowSideBar(!showSideBar)
  }

  return (
    <div
    className="flex flex-col h-screen">
      <Header showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
      <div className="flex-1 mt-16">
        <SideBar showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
        <Switch>
          <div className="h-[calc(100vh-4rem)] mx-64">
            <Route path="/edituser/:id" component={EditUserPage} />
            <Route path="/adduser" component={AddUserPage} />
            <Route path="/users" component={UsersPage} />
            <Route path="/loans" component={LoansPage} />
            <Route path="/applyloan" component={ApplyLoanPage} />
          </div>
        </ Switch>
      </div>
    </div>
  )
}

export default App

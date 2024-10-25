import { useState } from "react"
import { Route, Switch } from 'wouter';
import { Header } from "./layouts/Header"
import { SideBar } from "./layouts/SideBar"
import { SignupPage } from "./pages/SignupPage"
import { UsersPage } from "./pages/UsersPage"


function App() {
  const [showSideBar, setShowSideBar] = useState(false)

  function handleShowSideBar() {
    setShowSideBar(!showSideBar)
  }

  return (
    <div
    className="flex flex-col h-screen">
      <Header showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
      <div className="relative flex-1 mt-16">
        <SideBar showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
        <Switch>
          <div className="mx-64">
            <Route path="/signup" component={SignupPage} />
            <Route path="/users" component={UsersPage} />
          </div>
        </ Switch>
      </div>
    </div>
  )
}

export default App

import { useState } from "react"
import { Header } from "./layouts/Header"
import { SideBar } from "./layouts/SideBar"


function App() {
  const [showSideBar, setShowSideBar] = useState(false)

  function handleShowSideBar() {
    setShowSideBar(!showSideBar)
  }

  return (
    <div
    className="flex flex-col h-screen">
      <Header showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
      <div className="relative h-full">
        <SideBar showSideBar={showSideBar} handleShowSideBar={handleShowSideBar}/>
      </div>
    </div>
  )
}

export default App

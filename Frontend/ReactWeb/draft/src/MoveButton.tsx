import move from './lib/move.png'
import Controller from "./Controller"
import { useState } from 'react'

function MoveButton() {
    
    const [state,setState] = useState<number>()

    const clicked = () => {
        if(state === 0)
            setState(1)
        else
            setState(0)
        Controller.sendInput("clickstate" , {
            clickstate : 0
        })
    }

    let modify = ""

    if(state === 1)
        modify += "border-yellow-400 "
    else
        modify += "border-gray-400 "

    return (
        <div className={modify + "border-2 rounded-md hover:scale-105 duration-300 ease-out"} onClick={clicked}>
            <img src={move} width={40}/>
        </div>
    )

}

export default MoveButton;
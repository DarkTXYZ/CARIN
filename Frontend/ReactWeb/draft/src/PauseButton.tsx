import pause from './lib/pause.png'
import Controller from "./Controller"
import { useState } from 'react'

function PauseButton(){

    const [state,setState] = useState<number>()

    Controller.getPauseState().then(resp => {
        setState(resp)
    })

    const clicked = () => {
        if(state === 0){
            Controller.sendPauseState({
                pauseState : 1
            })
        }
        else{
            Controller.sendPauseState({
                pauseState : 0
            })
        }
        Controller.sendSpeedState({
            speedState: 0
        })
    }

    let modify = ""

    if(state === 1)
        modify += "border-yellow-400 "
    else
        modify += "border-gray-400 "

    return (
        <div className={modify + "border-2 rounded-md hover:scale-105 duration-300 ease-out"} onClick={clicked}>
            <img src={pause} width={40}/>
        </div>
    )
}

export default PauseButton
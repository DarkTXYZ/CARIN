import speed from './lib/speed.png'
import Controller from "./Controller"
import { useState } from 'react'

function SpeedButton(){

    const [state,setState] = useState<number>()

    Controller.getSpeedState().then(resp => {
        setState(resp)
    })

    const clicked = () => {
        if(state === 0){
            Controller.sendSpeedState({
                speedState : 1
            })
        }
        else{
            Controller.sendSpeedState({
                speedState : 0
            })
        }
        Controller.sendPauseState({
            pauseState: 0
        })
    }

    let modify = ""

    if(state === 1)
        modify += "border-yellow-400 "
    else
        modify += "border-gray-400 "
        
    return (
        <div className={modify + "border-2 rounded-md hover:scale-105 duration-300 ease-out"} onClick={clicked}>
            <img src={speed} width={40}/>
        </div>
    )
}

export default SpeedButton
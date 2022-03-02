import move from './lib/move.png'
import Controller from "./Controller"
import { useState } from 'react'

function MoveButton(props: any) {

    // const [moveState,setMoveState] = useState<number>()
    // const [selectedX, setSelectedX] = useState<number>(-1)
    // const [selectedY, setSelectedY] = useState<number>(-1)

    // Controller.getInput("selectedx").then(resp => setSelectedX(resp))
    // Controller.getInput("selectedy").then(resp => setSelectedY(resp))
    // Controller.getInput('movestate').then(resp => setMoveState(resp))

    let moveState = props.moveState
    let selectedX = props.x
    let selectedY = props.y

    const clicked = () => {
        if (moveState === 0) {
            Controller.sendInput('movestate', {
                moveState: 1
            })
            Controller.sendInput('selected', {
                selectedX: -1,
                selectedY: -1
            })
        }
        else
            Controller.sendInput('movestate', {
                moveState: 0
            })
        Controller.sendInput("placestate", {
            placeState: 0
        })
    }

    const selectATBDClicked = () => {
        if (moveState === 1 && selectedX !== -1 && selectedY !== -1) {
            Controller.sendInput('movestate', {
                moveState: 2
            })
            Controller.sendInput('og', {
                ogX: selectedX,
                ogY: selectedY
            })
            Controller.sendInput('selected', {
                selectedX: -1,
                selectedY: -1
            })
        }
    }

    const selectTileClicked = () => {
        if (moveState === 2 && selectedX !== -1 && selectedY !== -1) {
            Controller.sendInput('movestate', {
                moveState: 3
            })
            Controller.sendInput('posmove', {
                posX_move: selectedX,
                posY_move: selectedY
            })
            Controller.sendInput('selected', {
                selectedX: -1,
                selectedY: -1
            })
        }
    }

    const cancel = () => {
        Controller.sendInput("movestate", {
            moveState: 0
        })
        Controller.sendInput("selected", {
            selectedX: -1,
            selectedY: -1,
        })
    }


    let modify = ""

    if (moveState !== 0)
        modify += "border-yellow-400 "
    else
        modify += "border-gray-400 "

    return (
        <div className='flex flex-row space-x-2'>
            {moveState === 1 &&
                <div className='flex flex-row space-x-2 items-center'>
                    <p className="font-bold text-white">Select ATBD</p>
                    <div className="font-bold bg-gradient-to-l from-green-600 to-green-400 p-2 border-2 border-green-300 rounded-md hover:scale-105 duration-300 ease-out" onClick={selectATBDClicked}>
                        Select
                    </div>
                </div>
            }
            {moveState === 2 &&
                <div className='flex flex-row space-x-2 items-center'>
                    <p className="font-bold text-white">Select Tiles</p>
                    <div className="font-bold bg-gradient-to-l from-green-600 to-green-400 p-2 border-2 border-green-300 rounded-md hover:scale-105 duration-300 ease-out" onClick={selectTileClicked}>
                        Select
                    </div>
                </div>
            }
            {moveState !== 0 &&
                <div className="font-bold bg-gradient-to-l from-red-600 to-red-400 p-2 border-2 border-red-300 rounded-md hover:scale-105 duration-300 ease-out" onClick={cancel}>
                    Cancel
                </div>
            }

            <div className={modify + "border-2 rounded-md hover:scale-105 duration-300 ease-out"} onClick={clicked}>
                <img src={move} width={40} />
            </div>
        </div>

    )

}

export default MoveButton;
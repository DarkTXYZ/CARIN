import move from './lib/move.png'
import Controller from "./Controller"
import { useState } from 'react'

function MoveButton(props: any) {

    let moveState = props.moveState
    let selectedX = props.x
    let selectedY = props.y

    const clicked = () => {
        if (moveState === 0) {
            Controller.sendInput('selected', {
                selectedX: -1,
                selectedY: -1
            }).then(() => {
                Controller.sendInput('movestate', {
                    moveState: 1
                }).then(() => {
                    Controller.sendInput("placestate", {
                        placeState: 0
                    })
                })
            })
        }
        else{
            Controller.sendInput('movestate', {
                moveState: 0
            }).then(() => {
                Controller.sendInput("placestate", {
                    placeState: 0
                })
            })
        }
    }

    const selectATBDClicked = () => {
        if (moveState === 1 && selectedX !== -1 && selectedY !== -1) {
            Controller.sendInput('og', {
                ogX: selectedY,
                ogY: selectedX
            }).then(() => {
                Controller.sendInput('selected', {
                    selectedX: -1,
                    selectedY: -1
                }).then(() => {
                    Controller.sendInput('movestate', {
                        moveState: 2
                    })
                })
            })
        }
    }

    const selectTileClicked = () => {
        if (moveState === 2 && selectedX !== -1 && selectedY !== -1) {
            Controller.sendInput('selected', {
                selectedX: -1,
                selectedY: -1
            }).then(() => {
                Controller.sendInput('posmove', {
                    posX_move: selectedY,
                    posY_move: selectedX
                }).then(() => {
                    Controller.sendInput('movestate', {
                        moveState: 3
                    }).then(() => {
                        Controller.sendInput('pausestate', {
                            pauseState: 0
                        })
                    })
                })
            })
        }
    }

    const cancel = () => {
        Controller.sendInput("selected", {
            selectedX: -1,
            selectedY: -1,
        }).then(() => {
            Controller.sendInput("movestate", {
                moveState: 0
            })
        })
    }


    let modify = ""

    if (moveState === 1 || moveState === 2 )
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
            {(moveState === 1 || moveState === 2) &&
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
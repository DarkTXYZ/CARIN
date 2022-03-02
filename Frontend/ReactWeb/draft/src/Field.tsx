import { TransformComponent, TransformWrapper } from "react-zoom-pan-pinch"
import atbd1 from './lib/atbd1.png'
import atbd2 from './lib/atbd2.png'
import atbd3 from './lib/atbd3.png'
import virus1 from './lib/virus1.png'
import virus2 from './lib/virus2.png'
import virus3 from './lib/virus3.png'
import Controller from "./Controller"                          
import { useState } from "react"
import React from "react";
import MoveButton from "./MoveButton"
import SpeedButton from "./SpeedButton"
import PauseButton from "./PauseButton"

function Node(props: any) {
    let color = ""
    let skin: any = null
    let size = props.size
    let progheight = size * 0.1
    let progwidth = size * 0.8
    let chosen = false;

    if (((props.x + props.y) % 2) === 0)
        color = "bg-rose-300 flex justify-center"
    else
        color = "bg-rose-400 flex justify-center"

    if (props.select) {
        if (props.type === 0 && props.placeState !== 0)
            color += " border-2 border-green-400"
        else if (props.moveState === 1) {
            if (props.type <= 3 && props.type >= 1)
                color += " border-2 border-green-400"
        } else if (props.moveState === 2) {
            if (props.type === 0)
                color += " border-2 border-green-400"
        }
    } else {
        if (props.type === 0 && props.placeState !== 0)
            chosen = true
        else if (props.moveState === 1) {
            if (props.type <= 3 && props.type >= 1)
                chosen = true
        } else if (props.moveState === 2) {
            if (props.type === 0)
                chosen = true
        }
    }

    if (props.type === 1) {
        skin = atbd1
    } else if (props.type === 2) {
        skin = atbd2
    } else if (props.type === 3) {
        skin = atbd3
    } else if (props.type === 4) {
        skin = virus1
    } else if (props.type === 5) {
        skin = virus2
    } else if (props.type === 6) {
        skin = virus3
    }

    let image: any = null
    if (props.type !== 0)
        image = <img src={skin} alt="" style={{ height: size }} />
    else
        image = null

    return (
        <div>
            <div className={color + ' flex flex-col relative items-center'} style={{ height: size, width: size }}
                onClick={() => {
                    if (chosen) {
                        Controller.sendInput("selected", {
                            selectedX: props.x,
                            selectedY: props.y,
                        })
                    }
                }}>
                <div>
                    {props.type !== 0 && <progress className='fixed rotate-45' style={{ transform: "translate(-50%,-80%)", height: progheight, width: progwidth }} value={props.hp} max={props.hpMax}></progress>}
                </div>
                <div>
                    {image}
                </div>
            </div>
        </div>
    )
}

function Field(props: any) {

    const m = props.m
    const n = props.n

    let scale = 0.65
    let fullWidth = 1920 * scale
    let fullHeight = 1080 * scale
    let size = fullWidth / m < fullHeight / n ? fullWidth / m : fullHeight / n

    // let width = fullWidth / m
    // let height = fullHeight / n

    const [selectedX, setSelectedX] = useState<number>(-1)
    const [selectedY, setSelectedY] = useState<number>(-1)
    const [placeState, setPlaceState] = useState<number>(-1)
    const [moveState, setMoveState] = useState<number>(-1)

    Controller.getInput("selectedx").then(resp => setSelectedX(resp))
    Controller.getInput("selectedy").then(resp => setSelectedY(resp))
    Controller.getInput("movestate").then(resp => setMoveState(resp))
    Controller.getInput("placestate").then(resp => setPlaceState(resp))

    const createGrid = () => {
        const Grid = []

        const px = props.px
        const py = props.py
        const type = props.t

        for (let i = 0; i < m; ++i) {
            const currentRow = []
            for (let j = 0; j < n; ++j) {
                currentRow.push(<div />)
            }
            Grid.push(currentRow)
        }

        return (
            <div className="flex">
                {Grid.map((row, rowId) => {
                    return (
                        <div key={rowId}>
                            {row.map((node, nodeId) => {
                                let selected = false
                                if (selectedX === nodeId && selectedY === rowId)
                                    selected = true

                                for (let i = 0; i < px.length; ++i) {
                                    if (py[i] === nodeId && px[i] === rowId) {
                                        return (
                                            <div className=''>
                                                <Node x={nodeId} y={rowId} size={size} type={type[i]} select={selected} hp={props.hp[i]} hpMax={props.hpMax[i]} moveState={moveState} placeState={placeState} />
                                            </div>
                                        )
                                    }
                                }
                                return (
                                    <Node x={nodeId} y={rowId} size={size} type={0} select={selected} moveState={moveState} placeState={placeState} />
                                )
                            })}
                        </div>
                    )
                })}
            </div>
        )
    }

    let screenWidth = 1232
    let screenHeight = 686

    return (

        <TransformWrapper centerOnInit={true} initialScale={0.9} minScale={0.5} maxScale={100} limitToBounds={false} doubleClick={{ disabled: true }}>

            {({ zoomIn, zoomOut, centerView, ...rest }) => (

                <React.Fragment>
                    <div className="flex flex-col space-y-2">
                        <div className="flex items-center justify-center border-8 border-yellow-400 rounded-xl" style={{ width: 1248, height: 702 }}>
                            <div className="flex flex-col space-y-2">
                                <div className="">
                                    <TransformComponent>
                                        <div className="flex items-center justify-center" style={{ width: screenWidth, height: screenHeight }}>
                                            {createGrid()}
                                        </div>
                                    </TransformComponent>
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-row justify-between space-x-2">
                            <div className="tools">
                                <button className='text-xl font-bold rounded-xl p-2 hover:scale-105 duration-300 ease-out hover:drop-shadow-xl bg-gradient-to-r from-pink-300 via-purple-300 to-indigo-400' onClick={() => {
                                    centerView(0.9)
                                }}>Reset View</button>
                            </div>
                            <div className="flex flex-row space-x-2">
                                <MoveButton moveState={moveState} x={selectedX} y={selectedY} />
                                <PauseButton />
                                <SpeedButton />
                            </div>

                        </div>
                    </div>

                </React.Fragment>

            )}
        </TransformWrapper >


    )

}

export default Field
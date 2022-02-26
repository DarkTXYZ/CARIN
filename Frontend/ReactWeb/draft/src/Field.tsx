import { TransformComponent, TransformWrapper } from "react-zoom-pan-pinch"
import atbd1 from './lib/atbd1.png'
import atbd2 from './lib/atbd2.png'
import atbd3 from './lib/atbd3.png'
import Controller from "./Controller"
import { useState } from "react"
import React, { Component } from "react";
import MoveButton from "./MoveButton"
import SpeedButton from "./SpeedButton"
import PauseButton from "./PauseButton"

function Node(props: any) {
    let color = ""
    let ATBDchosen: any = null
    let size = props.size
    let progheight = size * 0.1
    let progwidth = size * 0.8

    if (((props.x + props.y) % 2) === 0)
        color = "bg-rose-300 flex justify-center"
    else
        color = "bg-rose-400 flex justify-center"

    if (props.select)
        color += " border-2 border-green-400"

    if (props.type === 'atbd1') {
        ATBDchosen = atbd1
    } else if (props.type === 'atbd2') {
        ATBDchosen = atbd2
    } else if (props.type === 'atbd3') {
        ATBDchosen = atbd3
    }

    let image: any = null
    if (props.type !== 0)
        image = <img src={ATBDchosen} alt="" style={{ height: size }} />
    else
        image = null

    return (
        <div>
            <div className={color + ' flex flex-col relative items-center'} style={{ height: size, width: size }}
                onClick={() => {
                    if (image === null) {
                        Controller.sendInput("posplace" , {
                            posX_place: props.x,
                            posY_place: props.y,
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

    const [selectX, setSelectX] = useState<number>(-1)
    const [selectY, setSelectY] = useState<number>(-1)
    const [clickState, setClickState] = useState<number>(-1)

    Controller.getInput("posplacex").then(resp => setSelectX(resp))
    Controller.getInput("posplacey").then(resp => setSelectY(resp))
    Controller.getInput("clickstate").then(resp => setClickState(resp))

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
                                if (selectX === nodeId && selectY === rowId && clickState !== 0)
                                    selected = true

                                for (let i = 0; i < px.length; ++i) {
                                    if (px[i] === nodeId && py[i] === rowId) {
                                        return (
                                            <div className=''>
                                                <Node x={nodeId} y={rowId} size={size} type={type[i]} select={false} hp={props.hp[i]} hpMax={props.hpMax[i]} />
                                            </div>
                                        )
                                    }
                                }
                                return (
                                    <Node x={nodeId} y={rowId} size={size} type={0} select={selected} />
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
                                <MoveButton />
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
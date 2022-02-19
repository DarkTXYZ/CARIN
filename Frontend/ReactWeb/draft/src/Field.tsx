import React from "react"
import { TransformComponent,TransformWrapper } from "react-zoom-pan-pinch"
import field1 from './lib/field1.png'
import field2 from './lib/field2.png'

function Node(props: any) {

    let field = null
    if(((props.x + props.y) % 2) === 0) {
        field = <img src = {field1} style={{height:props.h }}/>
    } else {
        field = <img src = {field2} style={{height:props.h }}/>
    }
    return (
        <div className="">
            {field}
        </div>
    )
}

function Field(props: any) {

    let scale = 0.8
    let fullWidth = 1920*scale
    let fullHeight = 1080*scale

    let width = fullWidth / props.m
    let height = fullHeight / props.n

    const createGrid = () => {
        const Grid = []
        const m = props.m
        const n = props.n
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
                                return (
                                    <Node x={nodeId} y={rowId} w = {width} h = {height} />
                                )
                            })}
                        </div>
                    )
                })}
            </div>
        )
    }

    return (

        <div className = "flex justify-center">
            <TransformWrapper>
                <TransformComponent>
                    <div className = "">
                        {createGrid()}
                    </div>
                </TransformComponent>
            </TransformWrapper>
        </div>

    )

}

export default Field
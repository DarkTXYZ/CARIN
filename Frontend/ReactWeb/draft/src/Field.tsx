import { TransformComponent, TransformWrapper } from "react-zoom-pan-pinch"
import field1 from './lib/field1.png'
import field2 from './lib/field2.png'
import atbd1 from './lib/atbd1.png'
import atbd2 from './lib/atbd2.png'
import atbd3 from './lib/atbd3.png'
import Controller from "./Controller"

function Node(props: any) {
    let node = null
    let color = ""
    let ATBDchosen : any = null
    let size = props.w < props.h ? props.w : props.h

    if (((props.x + props.y) % 2) === 0) 
        color = "bg-rose-300 flex justify-center"
    else 
        color = "bg-rose-400 flex justify-center"

    if(props.type === 1) {
        ATBDchosen = atbd1
    } else if(props.type === 2) {
        ATBDchosen = atbd2
    } else if(props.type === 3){
        ATBDchosen = atbd3
    } 

    let image = null
    if(props.type !== 0)
        image = <img src={ATBDchosen} alt="" style={{ height: size}} />
    else
        image = <div></div>

    node = (
        <div className={color} style={{ height: size, width: size }}
            onMouseDown={() => {
                Controller.sendPos({
                    posX_placement: props.x,
                    posY_placement: props.y,
                })
                Controller.sendState({
                    state: 0
                })
            }}>
            {image}
        </div>
    )
    return (
        <div>
            {node}
        </div>
    )
}

function Field(props: any) {

    const m = props.m
    const n = props.n

    let scale = 0.6
    let fullWidth = 1920 * scale
    let fullHeight = 1080 * scale

    let width = fullWidth / m
    let height = fullHeight / n

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
                                let t = ""
                                for(let i = 0 ; i < px.length ; ++i) {
                                    if(px[i] === nodeId && py[i] === rowId) {
                                        return (
                                            <Node x={nodeId} y={rowId} w={width} h={height} type={type[i]}/>
                                        )
                                    }
                                }
                                return (
                                    <Node x={nodeId} y={rowId} w={width} h={height} type={0}/>
                                )
                            })}
                        </div>
                    )
                })}
            </div>
        )
    }

    return (

        <div className="flex justify-center">
            <TransformWrapper doubleClick={{disabled:true}}>
                <TransformComponent>
                    <div className="">
                        {createGrid()}
                    </div>
                </TransformComponent>
            </TransformWrapper>
        </div>

    )

}

export default Field
import { useState } from "react"
import tile1 from './lib/atbd1.png'
import tile2 from './lib/atbd2.png'
import tile3 from './lib/atbd3.png'
import Controller from "./Controller"

function ShopTile(props: any) {
    const [hover, setHover] = useState<boolean>(false)
    const [clickState , setClickState] = useState<number>()

    Controller.getInput('clickstate').then(resp => {
        setClickState(resp)
    })


    const toggleHover = () => {
        setHover(!hover)
    }
    let canBuy: boolean = props.canBuy
    let cost = props.cost
    let tileImg : any = null
    let selected = props.selected // props.selected
    let type = props.type

    if (type === 1) tileImg = tile1
    else if (type === 2) tileImg = tile2
    else if (type === 3) tileImg = tile3

    let modify = ""

    if (!canBuy) {
        modify = "opacity-70 border-4 border-gray-400"
    } else {
        if (selected === 'atbd' + type && clickState !== 0){
            if(type === 1) {
                modify = 'opacity-100 border-4 border-yellow-300'
            } else if(type === 2) {
                modify = 'opacity-100 border-4 border-blue-400'
            } else {
                modify = 'opacity-100 border-4 border-green-400'
            }
        }   
        else
            modify += 'opacity-100 border-4 border-gray-400'
    }

    if(type === 1) {
        modify += ' bg-gradient-to-b from-orange-500 to-yellow-300'
    } else if(type === 2) {
        modify += ' bg-gradient-to-r from-purple-800 via-violet-900 to-purple-800'
    } else {
        modify += ' bg-gradient-to-r from-green-500 to-green-700'
    }

    let color = ""
    if(canBuy)
        color = 'text-green-400'
    else 
        color = 'text-red-400'

    return (
        <div className="flex flex-row items-center space-x-10" onClick={props.onClick}>
            <div className={modify + " w-40 flex flex-col rounded-2xl bg-white hover:drop-shadow-xl duration-300 ease-out hover:scale-105 "} onMouseEnter={toggleHover} onMouseLeave={toggleHover}>
                <div className="self-center">
                    <img src={tileImg} className="rounded-t-xl h-44 opacity-100" />
                </div>
                <div className={color + ' font-bold h-30 bg-gray-600 flex justify-center rounded-b-xl select-none'}>
                    Cost: {cost}
                </div>
            </div>
            <div>{hover && <p className='select-none fixed text-4xl text-gray-600 font-semibold'>{props.name}</p>}</div>
        </div>

    )

}

export default ShopTile
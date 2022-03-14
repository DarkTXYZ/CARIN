import { useState } from "react"
import tile1 from './lib/atbd1.png'
import tile2 from './lib/atbd2.png'
import tile3 from './lib/atbd3.png'

function ShopTile(props: any) {
    const [hover, setHover] = useState<boolean>(false)

    const toggleHover = () => {
        setHover(!hover)
    }
    let canBuy: boolean = props.canBuy
    let cost = props.cost
    let tileImg: any = null
    let selected = props.selected // props.selected
    let type = props.type

    if (type === 1) tileImg = tile1
    else if (type === 2) tileImg = tile2
    else if (type === 3) tileImg = tile3

    let modify = ""
    let modifyDesc = ""

    if (!canBuy) {
        modify = "opacity-70 border-4 border-gray-400"
    } else {
        if (selected === type && props.placeState === 1) {
            if (type === 1) {
                modify = 'opacity-100 border-4 border-yellow-300'
            } else if (type === 2) {
                modify = 'opacity-100 border-4 border-blue-400'
            } else {
                modify = 'opacity-100 border-4 border-green-400'
            }
        }
        else
            modify += 'opacity-100 border-4 border-gray-400'
    }

    if (type === 1) {
        modify += ' bg-gradient-to-b from-orange-500 to-yellow-300'
        modifyDesc += ' bg-gradient-to-b from-orange-500 to-yellow-300'
    } else if (type === 2) {
        modify += ' bg-gradient-to-l from-blue-100 via-blue-300 to-blue-500'
        modifyDesc += ' bg-gradient-to-l from-blue-100 via-blue-300 to-blue-500'
    } else {
        modify += ' bg-gradient-to-r from-green-500 to-green-700'
        modifyDesc += ' bg-gradient-to-r from-green-500 to-green-700'
    }

    let color = ""
    if (canBuy)
        color = 'text-green-400'
    else
        color = 'text-red-400'

    let h = ['■■','■','■■■■■']
    let a = ['■■■','■■■■','■■']
    let ls = ['■■','■■■','■']
    let r = ['■','■■■■■■','■']
    let m = ['■','■■','■■']

    return (
        <div className="flex flex-row items-center space-x-4" onClick={props.onClick}>
            <div className={modify + " w-40 flex flex-col rounded-2xl bg-white hover:drop-shadow-xl duration-300 ease-out hover:scale-105 "} onMouseEnter={toggleHover} onMouseLeave={toggleHover}>
                <div className="self-center">
                    <img src={tileImg} className="rounded-t-xl h-44 opacity-100" />
                </div>
                <div className={color + ' font-bold h-30 bg-gray-600 flex justify-center rounded-b-xl select-none'}>
                    Cost: {cost}
                </div>
            </div>
            {
                hover &&
                <div className="fixed translate-x-40">
                    <div className={'select-none text-transparent bg-clip-text'+modifyDesc}>
                        <div className="text-4xl font-bold mb-5">{props.name}</div>
                        <div className="text-lg font-semibold">HP : {h[type-1]}</div>
                        <div className="text-lg font-semibold">ATK : {a[type-1]}</div>
                        <div className="text-lg font-semibold">Lifesteal : {ls[type-1]}</div>
                        <div className="text-lg font-semibold">Range : {r[type-1]}</div>
                        <div className="text-lg font-semibold">MoveCost : {m[type-1]}</div>
                    </div>
                </div>
            }
        </div>

    )

}

export default ShopTile
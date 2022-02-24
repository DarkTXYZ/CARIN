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
    let tileImg = <img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover}
        className="rounded-t-xl h-44 opacity-100" />
    let selected = props.selected // props.selected
    let type = props.type

    if (type === 1) tileImg = <img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover}
        className="rounded-t-xl h-44 opacity-100" />
    else if (type === 2) tileImg = <img src={tile2} onMouseEnter={toggleHover} onMouseLeave={toggleHover}
        className="rounded-t-xl h-44 opacity-100" />
    else if (type === 3) tileImg = <img src={tile3} onMouseEnter={toggleHover} onMouseLeave={toggleHover}
        className="rounded-t-xl h-44 opacity-100" />

    let modify = ""

    if (!canBuy) {
        modify = "opacity-70 border-4 border-gray-400"
    } else {
        if (selected === 'atbd' + type)
            modify = 'opacity-100 border-4 border-yellow-400'
        else
            modify += 'opacity-100 border-4 border-gray-400'
    }

    let color = ""
    if(canBuy)
        color = 'text-green-400'
    else 
        color = 'text-red-400'

    return (
        <div className="flex flex-row items-center space-x-10" onClick={props.onClick}>
            <div className={modify + " w-40 flex flex-col rounded-2xl bg-white hover:drop-shadow-xl duration-300 ease-out hover:scale-105"}>
                <div className="self-center">
                    {tileImg}
                </div>
                <div className={color + ' font-bold h-30 bg-gray-600 flex justify-center rounded-b-xl'}>
                    Cost: {cost}
                </div>
            </div>
            <div>{hover && <p className='fixed text-4xl text-gray-600 font-semibold'>{props.name}</p>}</div>
        </div>

    )

}

export default ShopTile